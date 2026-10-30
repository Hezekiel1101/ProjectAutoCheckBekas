package id.ac.stmi.autocekbekas

import android.graphics.Color
import java.text.NumberFormat
import java.util.Locale

object Status {
    const val BAIK = "Baik"
    const val PERBAIKAN = "Perlu Perbaikan"
    const val BERMASALAH = "Bermasalah"
    const val BELUM = "Belum Diperiksa"
}

object Kategori {
    const val LAYAK = "Layak Beli"
    const val NEGO = "Nego Keras"
    const val HINDARI = "Hindari"
    const val BELUM_LENGKAP = "Belum Lengkap"
}

class ItemPeriksa(
    val key: String,
    val nama: String,
    val judul: String,
    val pertanyaan: String,
    val petunjuk: String,
    val opsi: List<String>,
    val bobot: Int,
    val kritis: Boolean
) {
    fun labelDari(status: String?): String = when (status) {
        Status.BAIK -> opsi[0]
        Status.PERBAIKAN -> opsi[1]
        Status.BERMASALAH -> opsi[2]
        else -> Status.BELUM
    }
}

data class Rekomendasi(
    val judul: String,
    val warna: Int,
    val alasan: List<String>
)

data class Inspeksi(
    val id: Long,
    val merek: String,
    val model: String,
    val jenis: String,
    val tahun: Int,
    val km: Long,
    val harga: Long,
    val hargaPembanding: Long,
    val status: Map<String, String>,
    val catatan: Map<String, String>,
    val tanggal: String,
    val noPenjual: String
) {
    val nama: String
        get() = listOf(merek, model)
            .filter { it.isNotBlank() }
            .joinToString(" ")

    val skor: Int
        get() = Skor.hitung(status)

    val rekomendasi: Rekomendasi
        get() = Skor.rekomendasi(status)

    val jumlahDiperiksa: Int
        get() = Skor.jumlahDiperiksa(status)

    fun infoSingkat(): String {
        val tahunText = if (tahun > 0) tahun.toString() else "-"
        val kmText = if (km > 0) "${formatAngka(km)} km" else "Km tidak diketahui"
        return "$tahunText · $kmText"
    }

    val penilaianHarga: String
        get() = penilaianHarga(harga, hargaPembanding)
}

object Skor {

    val HIJAU = Color.parseColor("#22C55E")
    val AMBER = Color.parseColor("#F08C00")
    val MERAH = Color.parseColor("#EF4444")
    val ABU = Color.parseColor("#64748B")

    fun warnaStatus(s: String?): Int = when (s) {
        Status.BAIK -> HIJAU
        Status.PERBAIKAN -> AMBER
        Status.BERMASALAH -> MERAH
        else -> ABU
    }

    // Total bobot seluruh pemeriksaan adalah 100.
    val DAFTAR = listOf(
        ItemPeriksa(
            key = "mesin",
            nama = "Mesin & Transmisi",
            judul = "Kondisi mesin & transmisi",
            pertanyaan = "Bagaimana kondisi mesin dan perpindahan gigi?",
            petunjuk = "Perhatikan suara, getaran, asap knalpot, dan kelancaran perpindahan transmisi (matic/manual).",
            opsi = listOf(
                "Halus & normal",
                "Keluhan ringan",
                "Masalah serius"
            ),
            bobot = 25,
            kritis = true
        ),
        ItemPeriksa(
            key = "bodi",
            nama = "Bodi & rangka",
            judul = "Bodi dan rangka",
            pertanyaan = "Bagaimana kondisi bodi dan rangka?",
            petunjuk = "Periksa karat, bekas tabrakan, dan tanda banjir.",
            opsi = listOf(
                "Normal",
                "Kerusakan ringan",
                "Kerusakan berat"
            ),
            bobot = 15,
            kritis = true
        ),
        ItemPeriksa(
            key = "dokumen",
            nama = "Dokumen",
            judul = "Kelengkapan dokumen",
            pertanyaan = "Apakah dokumen kendaraan lengkap?",
            petunjuk = "Cocokkan BPKB, STNK, faktur jika tersedia, dan nomor rangka/mesin.",
            opsi = listOf(
                "Lengkap & cocok",
                "Perlu verifikasi",
                "Tidak cocok / bermasalah"
            ),
            bobot = 15,
            kritis = true
        ),
        ItemPeriksa(
            key = "ban_rem",
            nama = "Ban & Rem",
            judul = "Kondisi ban dan sistem pengereman",
            pertanyaan = "Bagaimana kondisi ban dan rem?",
            petunjuk = "Periksa ketebalan alur ban, usia ban, serta respons dan bunyi saat pengereman.",
            opsi = listOf(
                "Normal & pakem",
                "Aus / perlu perbaikan",
                "Gundul / blong (bahaya)"
            ),
            bobot = 10,
            kritis = true
        ),
        ItemPeriksa(
            key = "pajak",
            nama = "Pajak",
            judul = "Status pajak kendaraan",
            pertanyaan = "Apakah pajak kendaraan aktif?",
            petunjuk = "Periksa tanggal berlaku pajak dan kewajiban yang tertunggak.",
            opsi = listOf(
                "Aktif",
                "Segera jatuh tempo",
                "Terlambat / bermasalah"
            ),
            bobot = 10,
            kritis = false
        ),
        ItemPeriksa(
            key = "kaki_kaki",
            nama = "Kaki-kaki",
            judul = "Kaki-kaki dan suspensi",
            pertanyaan = "Bagaimana kondisi kaki-kaki?",
            petunjuk = "Perhatikan bunyi, getaran, kestabilan, dan kebocoran suspensi.",
            opsi = listOf(
                "Normal",
                "Perlu perbaikan ringan",
                "Masalah serius"
            ),
            bobot = 10,
            kritis = false
        ),
        ItemPeriksa(
            key = "ac_listrik",
            nama = "AC & kelistrikan",
            judul = "AC dan kelistrikan",
            pertanyaan = "Apakah AC dan kelistrikan berfungsi?",
            petunjuk = "Uji AC, lampu, indikator, dan perangkat listrik. Cek juga kondisi interior dasar.",
            opsi = listOf(
                "Berfungsi normal",
                "Ada gangguan ringan",
                "Tidak berfungsi / gangguan serius"
            ),
            bobot = 10,
            kritis = false
        ),
        ItemPeriksa(
            key = "servis_odometer",
            nama = "Servis & odometer",
            judul = "Riwayat servis dan odometer",
            pertanyaan = "Apakah riwayat servis dan kilometer wajar?",
            petunjuk = "Cocokkan catatan servis dan indikasi keaslian kilometer jika tersedia.",
            opsi = listOf(
                "Wajar & terverifikasi",
                "Belum sepenuhnya terverifikasi",
                "Ada indikasi ketidaksesuaian"
            ),
            bobot = 5,
            kritis = false
        )
    )

    fun jumlahDiperiksa(status: Map<String, String>): Int {
        return DAFTAR.count { item ->
            status[item.key] in listOf(
                Status.BAIK,
                Status.PERBAIKAN,
                Status.BERMASALAH
            )
        }
    }

    // Skor rata-rata berbobot dari item yang sudah diperiksa.
    // Item yang belum diperiksa tidak dianggap sebagai kerusakan.
    fun hitung(status: Map<String, String>): Int {
        val diperiksa = DAFTAR.filter { item ->
            status[item.key] in listOf(
                Status.BAIK,
                Status.PERBAIKAN,
                Status.BERMASALAH
            )
        }

        if (diperiksa.isEmpty()) return 0

        val totalBobot = diperiksa.sumOf { it.bobot }
        if (totalBobot == 0) return 0

        val totalNilai = diperiksa.sumOf { item ->
            val nilai = when (status[item.key]) {
                Status.BAIK -> 100
                Status.PERBAIKAN -> 50
                Status.BERMASALAH -> 0
                else -> 0
            }

            item.bobot * nilai
        }

        return (totalNilai.toDouble() / totalBobot)
            .toInt()
            .coerceIn(0, 100)
    }


    fun rekomendasi(status: Map<String, String>): Rekomendasi {
        val jumlah = jumlahDiperiksa(status)
        val lengkap = jumlah == DAFTAR.size
        val skor = hitung(status)

        val masalahKritis = DAFTAR.filter { item ->
            item.kritis && status[item.key] == Status.BERMASALAH
        }

        val masalahSerius = DAFTAR.filter { item ->
            status[item.key] == Status.BERMASALAH
        }

        val perluPerbaikan = DAFTAR.filter { item ->
            status[item.key] == Status.PERBAIKAN
        }

        val perbaikanKritis = perluPerbaikan.filter { item ->
            item.kritis
        }

        val alasan = mutableListOf<String>()

        val judul: String
        val warna: Int

        when {
            !lengkap -> {
                judul = Kategori.BELUM_LENGKAP
                warna = ABU

                alasan.add(
                    "Baru $jumlah dari ${DAFTAR.size} komponen yang diperiksa. " +
                            "Lengkapi semua pemeriksaan sebelum menentukan rekomendasi."
                )
            }

            masalahKritis.isNotEmpty() -> {
                judul = Kategori.HINDARI
                warna = MERAH

                masalahKritis.forEach { item ->
                    alasan.add(
                        "${item.nama}: ditemukan masalah kritis. " +
                                "Jangan lanjutkan pembelian sebelum pemeriksaan profesional."
                    )
                }
            }

            skor < 70 -> {
                judul = Kategori.HINDARI
                warna = MERAH

                alasan.add(
                    "Skor inspeksi di bawah 70. " +
                            "Risiko kondisi kendaraan perlu diperhatikan dengan serius."
                )
            }

            skor < 85 || masalahSerius.isNotEmpty() || perbaikanKritis.isNotEmpty() -> {
                judul = Kategori.NEGO
                warna = AMBER

                alasan.add(
                    "Terdapat kondisi yang perlu diperhatikan. " +
                            "Periksa penyebab dan estimasi biaya perbaikan sebelum bernegosiasi. " +
                            "Saran: Nego 5-10% dari harga penawaran atau minta penjual memperbaiki item yang bermasalah."
                )
            }

            else -> {
                judul = Kategori.LAYAK
                warna = HIJAU

                alasan.add(
                    "Hasil checklist menunjukkan kondisi kendaraan relatif baik. " +
                            "Tetap lakukan pemeriksaan profesional sebelum membeli."
                )
            }
        }

        // LOGIKA YANG DIUBAH: Nampilin teks asli dari checklist (misal "Segera jatuh tempo")
        perluPerbaikan.forEach { item ->
            val label = item.labelDari(status[item.key])
            alasan.add("${item.nama}: $label.")
        }

        masalahSerius.forEach { item ->
            if (masalahKritis.none { it.key == item.key }) {
                val label = item.labelDari(status[item.key])
                alasan.add("${item.nama}: $label.")
            }
        }

        return Rekomendasi(
            judul = judul,
            warna = warna,
            alasan = alasan.distinct()
        )
    }
}

// Format angka untuk kilometer dan nilai numerik biasa.
fun formatAngka(nilai: Long): String {
    return NumberFormat.getNumberInstance(
        Locale("id", "ID")
    ).format(nilai)
}

// Fungsi ini tetap tersedia untuk kode lain yang memakainya.
fun rupiah(nilai: Long): String = formatAngka(nilai)

// Penilaian harga berdasarkan pembanding yang dimasukkan pengguna.
fun penilaianHarga(
    harga: Long,
    hargaPembanding: Long
): String {
    if (hargaPembanding <= 0L || harga <= 0L) {
        return "Belum dapat dinilai"
    }

    val persen = (harga - hargaPembanding).toDouble() /
            hargaPembanding.toDouble() * 100.0

    val persenBulat = persen.toInt()

    return when {
        persen <= -10.0 ->
            "Di bawah harga pembanding (${persenBulat}%). Periksa kondisi dan dokumen."

        persen < -3.0 ->
            "Sedikit di bawah harga pembanding (${persenBulat}%)."

        persen <= 3.0 ->
            "Mendekati harga pembanding (${persenBulat}%)."

        persen <= 10.0 ->
            "Sedikit di atas harga pembanding (+${persenBulat}%)."

        else ->
            "Di atas harga pembanding (+${persenBulat}%). Pertimbangkan negosiasi."
    }
}