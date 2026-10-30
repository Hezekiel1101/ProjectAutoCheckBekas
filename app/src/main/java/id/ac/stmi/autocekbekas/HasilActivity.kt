package id.ac.stmi.autocekbekas

import android.animation.ValueAnimator
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import id.ac.stmi.autocekbekas.databinding.ActivityHasilBinding
import id.ac.stmi.autocekbekas.databinding.ItemHasilRowBinding

class HasilActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var b: ActivityHasilBinding
    private var itemId = -1L
    private var animSudah = false

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        b = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.title = "Hasil Inspeksi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(b.root) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            b.appbar.updatePadding(top = bars.top)
            b.bottomBar.updatePadding(bottom = bars.bottom + dp(16))
            insets
        }

        itemId = intent.getLongExtra(EXTRA_ID, -1L)
    }

    override fun onResume() {
        super.onResume()
        tampilkan()
    }

    private fun teks(t: String): TextView = TextView(this).apply {
        text = t
        textSize = 15f
        setTextColor(getColor(R.color.text_primary))
        setPadding(0, dp(4), 0, dp(4))
    }

    private fun tampilkan() {
        val item = InspeksiStore.getById(this, itemId)

        if (item == null) {
            finish()
            return
        }

        val rek = item.rekomendasi
        val warna = rek.warna

        b.tvNamaKendaraan.text = item.nama
        b.tvInfoKendaraan.text = item.infoSingkat()
        b.tvLabel.text = rek.judul
        b.tvLabel.backgroundTintList = ColorStateList.valueOf(warna)
        b.progressSkor.setIndicatorColor(warna)

        if (!animSudah) {
            animSudah = true

            ValueAnimator.ofInt(0, item.skor).apply {
                duration = 900
                interpolator = DecelerateInterpolator()

                addUpdateListener {
                    val nilai = it.animatedValue as Int
                    b.tvSkorBesar.text = nilai.toString()
                    b.progressSkor.setProgressCompat(nilai, false)
                }

                start()
            }
        } else {
            b.tvSkorBesar.text = item.skor.toString()
            b.progressSkor.setProgressCompat(item.skor, false)
        }

        b.listAlasan.removeAllViews()
        rek.alasan.forEach {
            b.listAlasan.addView(teks("•  $it"))
        }

        b.tvJudulHasil.text =
            "Diperiksa ${item.tanggal} · " +
                    "${Skor.jumlahDiperiksa(item.status)} dari ${Skor.DAFTAR.size} item"

        b.listHasil.removeAllViews()

        Skor.DAFTAR.forEach { p ->
            val row = ItemHasilRowBinding.inflate(
                layoutInflater,
                b.listHasil,
                false
            )

            val status = item.status[p.key] ?: Status.BELUM

            row.tvNamaItem.text = p.judul
            row.tvStatusItem.text = "●  " + p.labelDari(status)
            row.tvStatusItem.setTextColor(Skor.warnaStatus(status))

            val catatan = item.catatan[p.key]

            if (!catatan.isNullOrBlank()) {
                row.tvCatatanItem.text = "📝  $catatan"
                row.tvCatatanItem.visibility = View.VISIBLE
            } else {
                row.tvCatatanItem.visibility = View.GONE
            }

            b.listHasil.addView(row.root)
        }

        // 1. Tampilkan Harga Penjual
        b.tvHarga.text = rupiah(item.harga)

        // 2. Tampilkan Harga Pasar & Penilaian (LOGIKA BARU)
        if (item.hargaPembanding > 0) {
            b.tvPembanding.visibility = View.VISIBLE
            b.tvPenilaianHarga.visibility = View.VISIBLE

            b.tvPembanding.text = "Pasar: ${rupiah(item.hargaPembanding)}"
            b.tvPenilaianHarga.text = item.penilaianHarga
        } else {
            b.tvPembanding.visibility = View.GONE
            b.tvPenilaianHarga.visibility = View.GONE
        }

        b.btnShare.setOnClickListener {
            bagikanInspeksi(item)
        }

        b.btnUbah.setOnClickListener {
            val intent = Intent(this, InspeksiActivity::class.java)
            intent.putExtra(InspeksiActivity.EXTRA_EDIT_ID, item.id)
            startActivity(intent)
        }

        // 3. LOGIKA TOMBOL CEK HARGA (Buka Google Search)
        b.btnCari.setOnClickListener {
            val kataKunci = "${item.merek} ${item.model} ${item.tahun}"
            val url = "https://www.google.com/search?q=harga+mobil+bekas+$kataKunci"

            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "Tidak dapat membuka browser", Toast.LENGTH_SHORT).show()
            }
        }

        b.btnTelepon.setOnClickListener {
            if (item.noPenjual.isBlank()) {
                Toast.makeText(
                    this,
                    "No. HP penjual belum diisi",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(
                    Intent(
                        Intent.ACTION_DIAL,
                        Uri.parse("tel:${item.noPenjual}")
                    )
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun bagikanInspeksi(item: Inspeksi) {
        val teksBagikan = """
            HASIL INSPEKSI MOBIL BEKAS

            Kendaraan: ${item.nama}
            Jenis: ${item.jenis}
            Tahun: ${item.tahun}
            Kilometer: ${formatAngka(item.km)}
            Harga tawaran penjual: ${rupiah(item.harga)}

            Skor: ${item.skor}/100
            Rekomendasi: ${item.rekomendasi.judul}
            Tanggal inspeksi: ${item.tanggal}

            Hasil ini berdasarkan checklist pemeriksaan kendaraan.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, teksBagikan)
        }

        startActivity(Intent.createChooser(intent, "Bagikan hasil inspeksi"))
    }
}