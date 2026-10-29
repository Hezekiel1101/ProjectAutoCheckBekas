
package id.ac.stmi.autocekbekas

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import org.json.JSONArray
import org.json.JSONObject

object InspeksiStore {

    private const val PREF = "autocek_pref"
    private const val KEY_LIST = "list_inspeksi"
    private const val KEY_TOTAL = "total_dicek"
    private const val KEY_MODE = "night_mode"
    private const val KEY_ONBOARD = "sudah_onboarding"

    private fun prefs(c: Context) =
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun getAll(c: Context): List<Inspeksi> {
        val arr = JSONArray(prefs(c).getString(KEY_LIST, "[]") ?: "[]")

        return (0 until arr.length()).map { i ->
            val o = arr.getJSONObject(i)
            val status = mutableMapOf<String, String>()
            val catatan = mutableMapOf<String, String>()

            if (o.has("status")) {
                // Format penyimpanan terbaru.
                val so = o.getJSONObject("status")
                so.keys().forEach { k ->
                    status[k] = so.getString(k)
                }
            } else {
                // Format data lama.
                status["mesin"] = when (o.optString("mesin")) {
                    "Baik" -> Status.BAIK
                    "Cukup" -> Status.PERBAIKAN
                    "Rusak" -> Status.BERMASALAH
                    else -> Status.BELUM
                }

                val lama = o.optJSONArray("checklist") ?: JSONArray()
                val lolos = (0 until lama.length()).map {
                    lama.getString(it)
                }

                Skor.DAFTAR.filter { it.key != "mesin" }.forEach {
                    status[it.key] =
                        if (it.pertanyaan in lolos) Status.BAIK
                        else Status.BELUM
                }
            }

            o.optJSONObject("catatan")?.let { co ->
                co.keys().forEach { k ->
                    catatan[k] = co.getString(k)
                }
            }

            Inspeksi(
                id = o.getLong("id"),
                merek = o.optString("merek", ""),
                model = if (o.has("model")) {
                    o.getString("model")
                } else {
                    o.optString("nama", "")
                },
                jenis = o.optString("jenis", ""),
                tahun = o.optInt("tahun", 0),
                km = o.optLong("km", 0L),
                harga = o.optLong("harga", 0L),
                // Dibaca dari data lama, tetapi tidak digunakan untuk membandingkan harga.
                hargaPembanding = o.optLong("hargaPembanding", 0L),
                status = status,
                catatan = catatan,
                tanggal = o.optString("tanggal", ""),
                noPenjual = o.optString("noPenjual", "")
            )
        }.sortedByDescending { it.id }
    }

    fun getById(c: Context, id: Long): Inspeksi? =
        getAll(c).find { it.id == id }

    private fun saveAll(c: Context, list: List<Inspeksi>) {
        val arr = JSONArray()

        list.forEach { i ->
            val so = JSONObject()
            i.status.forEach { (k, v) ->
                so.put(k, v)
            }

            val co = JSONObject()
            i.catatan.forEach { (k, v) ->
                co.put(k, v)
            }

            arr.put(JSONObject().apply {
                put("id", i.id)
                put("merek", i.merek)
                put("model", i.model)
                put("jenis", i.jenis)
                put("tahun", i.tahun)
                put("km", i.km)
                put("harga", i.harga)

                // Mempertahankan struktur data lama.
                // Inspeksi baru menyimpan nilai 0.
                put("hargaPembanding", i.hargaPembanding)

                put("status", so)
                put("catatan", co)
                put("tanggal", i.tanggal)
                put("noPenjual", i.noPenjual)
            })
        }

        prefs(c).edit().putString(KEY_LIST, arr.toString()).apply()
    }

    fun add(c: Context, item: Inspeksi) {
        saveAll(c, getAll(c) + item)
        prefs(c).edit().putInt(KEY_TOTAL, total(c) + 1).apply()
    }

    fun update(c: Context, item: Inspeksi) =
        saveAll(c, getAll(c).map {
            if (it.id == item.id) item else it
        })

    fun delete(c: Context, id: Long) =
        saveAll(c, getAll(c).filter { it.id != id })

    fun clearAll(c: Context) {
        prefs(c).edit().remove(KEY_LIST).apply()
    }

    fun total(c: Context): Int =
        prefs(c).getInt(KEY_TOTAL, 0)

    fun getMode(c: Context): Int =
        prefs(c).getInt(
            KEY_MODE,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

    fun setMode(c: Context, mode: Int) =
        prefs(c).edit().putInt(KEY_MODE, mode).apply()

    fun sudahOnboarding(c: Context): Boolean =
        prefs(c).getBoolean(KEY_ONBOARD, false)

    fun setOnboarding(c: Context) =
        prefs(c).edit().putBoolean(KEY_ONBOARD, true).apply()
}
