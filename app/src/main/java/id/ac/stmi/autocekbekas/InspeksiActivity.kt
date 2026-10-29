package id.ac.stmi.autocekbekas

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.android.material.chip.Chip
import id.ac.stmi.autocekbekas.databinding.ActivityInspeksiBinding
import id.ac.stmi.autocekbekas.databinding.ItemPeriksaBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InspeksiActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EDIT_ID = "extra_edit_id"
    }

    private lateinit var b: ActivityInspeksiBinding
    private val rows = mutableMapOf<String, ItemPeriksaBinding>()
    private var editItem: Inspeksi? = null

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private fun formatInputAngka(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var sedangMengubah = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (sedangMengubah) return
                sedangMengubah = true
                val angka = s?.toString().orEmpty().filter { it.isDigit() }
                val hasil = if (angka.isEmpty()) "" else {
                    angka.toLongOrNull()?.let {
                        NumberFormat.getNumberInstance(Locale("id", "ID")).format(it)
                    } ?: angka
                }
                editText.setText(hasil)
                editText.setSelection(hasil.length)
                sedangMengubah = false
            }
        })
    }

    private fun angkaDariInput(teks: String): Long? {
        return teks.filter { it.isDigit() }.toLongOrNull()
    }

    private fun statusDari(r: ItemPeriksaBinding): String {
        return when (r.chipGroup.checkedChipId) {
            R.id.chipBaik -> Status.BAIK
            R.id.chipPerbaikan -> Status.PERBAIKAN
            R.id.chipMasalah -> Status.BERMASALAH
            else -> Status.BELUM
        }
    }

    private fun semuaStatus(): Map<String, String> {
        return rows.mapValues { statusDari(it.value) }
    }

    private fun setStatus(r: ItemPeriksaBinding, s: String?) {
        when (s) {
            Status.BAIK -> r.chipBaik.isChecked = true
            Status.PERBAIKAN -> r.chipPerbaikan.isChecked = true
            Status.BERMASALAH -> r.chipMasalah.isChecked = true
            else -> r.chipGroup.clearCheck()
        }
    }

    private fun updateCatatanVisibility(r: ItemPeriksaBinding) {
        val status = statusDari(r)
        r.tilCatatan.visibility = if (status == Status.PERBAIKAN || status == Status.BERMASALAH) View.VISIBLE else View.GONE
    }

    private fun styleChip(chip: Chip, warnaAktif: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_checked), intArrayOf())
        chip.chipBackgroundColor = ColorStateList(states, intArrayOf(warnaAktif, getColor(R.color.card_bg)))
        chip.setTextColor(ColorStateList(states, intArrayOf(Color.WHITE, getColor(R.color.text_primary))))
        chip.isCheckedIconVisible = false
    }

    private fun updateSkorLive() {
        val status = semuaStatus()
        val skor = Skor.hitung(status)
        val rekomendasi = Skor.rekomendasi(status)
        val diperiksa = Skor.jumlahDiperiksa(status)

        b.tvSkorLive.text = "Skor $skor / 100 · $diperiksa dari ${Skor.DAFTAR.size} diperiksa"
        b.tvLabelLive.text = rekomendasi.judul
        b.tvLabelLive.setTextColor(rekomendasi.warna)
        b.progressLive.setIndicatorColor(rekomendasi.warna)
        b.progressLive.setProgressCompat(skor, true)
    }

    // LOGIKA CASCADING: MEREK -> MODEL (Gabungan Tipe)
    private fun setupDropdowns() {
        val adapterMerek = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, DataMobil.getMerekList())
        b.etMerek.setAdapter(adapterMerek)

        b.etMerek.setOnItemClickListener { _, _, _, _ ->
            val merek = b.etMerek.text.toString()
            val modelList = DataMobil.getModelList(merek)

            // Gabungkan Model + Tipe jadi 1 string, misal "Innova V"
            val gabunganList = mutableListOf<String>()
            modelList.forEach { model ->
                val tipeList = DataMobil.getTipeList(merek, model)
                tipeList.forEach { tipe ->
                    gabunganList.add("$model $tipe")
                }
            }

            b.etModel.setText("")
            b.etModel.isEnabled = gabunganList.isNotEmpty()
            val adapterGabungan = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, gabunganList)
            b.etModel.setAdapter(adapterGabungan)
            if (gabunganList.isNotEmpty()) b.etModel.showDropDown()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityInspeksiBinding.inflate(layoutInflater)
        setContentView(b.root)

        formatInputAngka(b.etKm)
        formatInputAngka(b.etHarga)
        formatInputAngka(b.etHargaPasar)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(b.root) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val keyboard = insets.getInsets(WindowInsetsCompat.Type.ime())
            b.appbar.updatePadding(top = bars.top)
            b.bottomBar.updatePadding(bottom = maxOf(bars.bottom, keyboard.bottom) + dp(16))
            insets
        }

        val editId = intent.getLongExtra(EXTRA_EDIT_ID, -1L)
        editItem = if (editId != -1L) InspeksiStore.getById(this, editId) else null

        supportActionBar?.title = if (editItem != null) "Ubah Inspeksi" else "Inspeksi Baru"
        if (editItem != null) b.btnSimpan.text = "Simpan Perubahan"

        setupDropdowns()

        // Isi form saat edit
        editItem?.let { item ->
            if (savedInstanceState == null) {
                b.etMerek.setText(item.merek, false)

                val modelList = DataMobil.getModelList(item.merek)
                val gabunganList = mutableListOf<String>()
                modelList.forEach { m ->
                    DataMobil.getTipeList(item.merek, m).forEach { t ->
                        gabunganList.add("$m $t")
                    }
                }
                val adapterGabungan = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, gabunganList)
                b.etModel.setAdapter(adapterGabungan)
                b.etModel.isEnabled = gabunganList.isNotEmpty()
                b.etModel.setText(item.model, false)

                if (item.tahun > 0) b.etTahun.setText(item.tahun.toString())
                if (item.km > 0) b.etKm.setText(formatAngka(item.km))
                b.etHarga.setText(formatAngka(item.harga))
                if (item.hargaPembanding > 0) b.etHargaPasar.setText(formatAngka(item.hargaPembanding))
                b.etPenjual.setText(item.noPenjual)

                val jenisMobil = resources.getStringArray(R.array.jenis_kendaraan)
                val posisi = jenisMobil.indexOf(item.jenis)
                b.spJenis.setSelection(if (posisi >= 0) posisi else 0)
            }
        }

        // Checklist
        Skor.DAFTAR.forEach { item ->
            val row = ItemPeriksaBinding.inflate(layoutInflater, b.listPeriksa, false)
            row.root.isSaveFromParentEnabled = false
            row.tvJudul.text = item.judul
            row.tvPetunjuk.text = item.petunjuk
            row.chipBaik.text = item.opsi[0]
            row.chipPerbaikan.text = item.opsi[1]
            row.chipMasalah.text = item.opsi[2]

            styleChip(row.chipBaik, Skor.HIJAU)
            styleChip(row.chipPerbaikan, Skor.AMBER)
            styleChip(row.chipMasalah, Skor.MERAH)

            val statusAwal = savedInstanceState?.getString("st_${item.key}") ?: editItem?.status?.get(item.key)
            setStatus(row, statusAwal)

            val catatanAwal = savedInstanceState?.getString("ct_${item.key}") ?: editItem?.catatan?.get(item.key)
            row.etCatatan.setText(catatanAwal ?: "")
            updateCatatanVisibility(row)

            row.chipGroup.setOnCheckedStateChangeListener { _, _ ->
                updateCatatanVisibility(row)
                updateSkorLive()
            }

            b.listPeriksa.addView(row.root)
            rows[item.key] = row
        }

        updateSkorLive()
        b.btnBatal.setOnClickListener { finish() }

        b.btnSimpan.setOnClickListener {
            val merek = b.etMerek.text.toString().trim()
            val modelGabungan = b.etModel.text.toString().trim()
            val tahunStr = b.etTahun.text.toString().trim()
            val tahun = tahunStr.toIntOrNull()
            val km = angkaDariInput(b.etKm.text.toString())
            val harga = angkaDariInput(b.etHarga.text.toString())
            val hargaPasar = angkaDariInput(b.etHargaPasar.text.toString())
            val hp = b.etPenjual.text.toString().trim()

            val tahunSekarang = Calendar.getInstance().get(Calendar.YEAR)
            var valid = true

            b.tilTahun.error = null
            b.tilKm.error = null
            b.tilHarga.error = null
            b.tilPenjual.error = null

            if (merek.isEmpty()) { Toast.makeText(this, "Pilih merek", Toast.LENGTH_SHORT).show(); valid = false }
            else if (modelGabungan.isEmpty()) { Toast.makeText(this, "Pilih model/tipe", Toast.LENGTH_SHORT).show(); valid = false }
            else if (tahun == null || tahun < 1980 || tahun > tahunSekarang + 1) {
                b.tilTahun.error = "Tahun harus antara 1980 dan ${tahunSekarang + 1}"
                valid = false
            }
            else if (km == null || km < 1L || km > 1_500_000L) { b.tilKm.error = "Isi kilometer 1–1.500.000"; valid = false }
            else if (harga == null || harga < 1_000_000L || harga > 10_000_000_000L) { b.tilHarga.error = "Harga tidak masuk akal"; valid = false }
            else if (hp.isNotEmpty() && hp.count { it.isDigit() } < 8) { b.tilPenjual.error = "Nomor HP tidak valid"; valid = false }

            if (!valid || tahun == null) return@setOnClickListener

            val status = semuaStatus()
            val catatan = rows.mapNotNull { (key, row) ->
                val s = status[key]
                val teks = row.etCatatan.text.toString().trim()
                if ((s == Status.PERBAIKAN || s == Status.BERMASALAH) && teks.isNotEmpty()) key to teks else null
            }.toMap()

            val tanggal = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID")).format(Date())
            val lama = editItem

            val item = Inspeksi(
                id = lama?.id ?: System.currentTimeMillis(),
                merek = merek,
                model = modelGabungan,
                jenis = b.spJenis.selectedItem.toString(),
                tahun = tahun,
                km = km!!,
                harga = harga!!,
                hargaPembanding = hargaPasar ?: 0L,
                status = status,
                catatan = catatan,
                tanggal = tanggal,
                noPenjual = hp
            )

            if (lama != null) {
                InspeksiStore.update(this, item)
                finish()
            } else {
                InspeksiStore.add(this, item)
                val intent = Intent(this, HasilActivity::class.java)
                intent.putExtra(HasilActivity.EXTRA_ID, item.id)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rows.forEach { (key, row) ->
            outState.putString("st_$key", statusDari(row))
            outState.putString("ct_$key", row.etCatatan.text.toString())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}