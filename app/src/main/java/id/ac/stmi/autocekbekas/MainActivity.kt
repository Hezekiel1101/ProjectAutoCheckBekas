package id.ac.stmi.autocekbekas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import id.ac.stmi.autocekbekas.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var adapter: InspeksiAdapter

    // 0 semua, 1 layak, 2 nego, 3 hindari
    private var filter = 0

    private fun log(s: String) {
        Log.d("LIFECYCLE", "MainActivity: $s")
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).roundToInt()
    }

    // Format angka menjadi mata uang rupiah
    private fun rupiah(nilai: Long): String {
        return NumberFormat
            .getCurrencyInstance(Locale("id", "ID"))
            .format(nilai)
            .replace(",00", "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Paksa mode terang
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.title = "AutoCek Bekas"

        log("onCreate")

        WindowCompat.getInsetsController(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = false

        ViewCompat.setOnApplyWindowInsetsListener(b.root) { _, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            b.header.updatePadding(top = bars.top)

            b.rvInspeksi.updatePadding(
                bottom = bars.bottom + dp(96)
            )

            b.fabBaru.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = bars.bottom + dp(16)
            }

            insets
        }

        b.tvSapaan.text = sapaan()

        adapter = InspeksiAdapter(
            onClick = { item ->
                bukaHasil(item)
            },
            onMore = { item, anchor ->
                tampilPopup(item, anchor)
            },
            onDelete = { item ->
                konfirmasiHapus(item)
            }
        )

        b.rvInspeksi.layoutManager = LinearLayoutManager(this)
        b.rvInspeksi.adapter = adapter

        // Filter kategori inspeksi (Cuma 3 kategori + Semua)
        b.chipGroup.setOnCheckedStateChangeListener { _, ids ->
            filter = when (ids.firstOrNull()) {
                R.id.chipLayak -> 1
                R.id.chipNego -> 2
                R.id.chipHindari -> 3
                else -> 0 // Semua
            }

            muatData()
        }

        // Tombol inspeksi baru
        b.fabBaru.setOnClickListener {
            startActivity(
                Intent(this, InspeksiActivity::class.java)
            )
        }

        // Onboarding pertama kali
        if (!InspeksiStore.sudahOnboarding(this)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Selamat datang di AutoCek Bekas 🚗")
                .setMessage(
                    "Cek kondisi mobil/motor bekas sebelum membeli, " +
                            "lalu dapatkan skor kelayakan dan alasan rekomendasinya."
                )
                .setPositiveButton("Mulai") { _, _ ->
                    InspeksiStore.setOnboarding(this)
                }
                .setCancelable(false)
                .show()
        }
    }

    override fun onStart() {
        super.onStart()
        log("onStart")
    }

    override fun onResume() {
        super.onResume()
        log("onResume")
        muatData() // Refresh data tiap balik ke halaman ini
    }

    override fun onPause() {
        super.onPause()
        log("onPause")
    }

    override fun onStop() {
        super.onStop()
        log("onStop")
    }

    override fun onRestart() {
        super.onRestart()
        log("onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun sapaan(): String {
        val jam = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return when (jam) {
            in 4..10 -> "Selamat pagi ☀️"
            in 11..14 -> "Selamat siang 🌤️"
            in 15..17 -> "Selamat sore 🌇"
            else -> "Selamat malam 🌙"
        }
    }

    private fun muatData() {
        val semua = InspeksiStore.getAll(this)

        val tampil = when (filter) {
            1 -> semua.filter {
                it.rekomendasi.judul == Kategori.LAYAK
            }

            2 -> semua.filter {
                it.rekomendasi.judul == Kategori.NEGO
            }

            3 -> semua.filter {
                it.rekomendasi.judul == Kategori.HINDARI
            }

            else -> semua
        }

        adapter.submit(tampil)

        // Statistik inspeksi
        val lengkap = semua.filter {
            it.rekomendasi.judul != Kategori.BELUM_LENGKAP
        }

        b.tvStatTotal.text = semua.size.toString()
        b.tvStatRata.text =
            if (lengkap.isEmpty()) {
                "-"
            } else {
                lengkap.map { it.skor }
                    .average()
                    .roundToInt()
                    .toString()
            }

        b.tvStatLayak.text = semua.count {
            it.rekomendasi.judul == Kategori.LAYAK
        }.toString()

        // Tampilan ketika tidak ada data
        if (semua.isEmpty()) {
            b.tvEmptyJudul.text = "Belum ada inspeksi"
            b.tvEmptyDesc.text =
                "Tekan tombol \"Inspeksi Baru\"\n" +
                        "untuk mengecek kendaraan pertamamu."
        } else {
            b.tvEmptyJudul.text = "Tidak ada data"
            b.tvEmptyDesc.text =
                "Belum ada kendaraan di kategori ini."
        }

        b.emptyState.visibility =
            if (tampil.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun bukaHasil(item: Inspeksi) {
        val i = Intent(this, HasilActivity::class.java)
        i.putExtra(HasilActivity.EXTRA_ID, item.id)
        startActivity(i)
    }

    private fun konfirmasiHapus(item: Inspeksi) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Hapus inspeksi?")
            .setMessage(item.nama)
            .setPositiveButton("Hapus") { _, _ ->
                InspeksiStore.delete(this, item.id)
                muatData()

                Snackbar.make(
                    b.root,
                    "Inspeksi dihapus",
                    Snackbar.LENGTH_SHORT
                )
                    .setAnchorView(b.fabBaru)
                    .show()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun tampilPopup(item: Inspeksi, anchor: View) {
        val popup = PopupMenu(this, anchor)

        popup.menuInflater.inflate(
            R.menu.menu_popup_item,
            popup.menu
        )

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pop_detail -> bukaHasil(item)
                R.id.pop_share -> bagikanInspeksi(item)
                R.id.pop_hapus -> konfirmasiHapus(item)
            }

            true
        }

        popup.show()
    }

    // Membagikan ringkasan inspeksi
    private fun bagikanInspeksi(item: Inspeksi) {
        val teks = """
            HASIL INSPEKSI - AutoCek Bekas

            Kendaraan: ${item.nama}
            Tahun: ${item.tahun}
            Kilometer: ${item.km}
            Harga penawaran: ${rupiah(item.harga)}
            Skor: ${item.skor}/100
            Rekomendasi: ${item.rekomendasi.judul}

            Alasan:
            ${item.rekomendasi.alasan.joinToString("\n") { "- $it" }}

            Catatan: Hasil ini merupakan evaluasi awal,
            bukan pengganti pemeriksaan mekanik profesional.
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_SUBJECT,
                "Hasil Inspeksi AutoCek Bekas"
            )
            putExtra(Intent.EXTRA_TEXT, teks)
        }

        startActivity(
            Intent.createChooser(
                intent,
                "Bagikan hasil inspeksi"
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        // Sembunyikan tombol Dark Mode
        val darkModeItem = menu.findItem(R.id.action_mode)
        darkModeItem?.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_hapus_semua -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Hapus semua data?")
                    .setPositiveButton("Hapus") { _, _ ->
                        InspeksiStore.clearAll(this)
                        muatData()
                    }
                    .setNegativeButton("Batal", null)
                    .show()

                true
            }

            R.id.action_tentang -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Instant Car Valuation")
                    .setMessage("Dibuat oleh Kelompok 6 (Ahmad Muzaki, Hezekiel Berkat, Atila Ahmad, Muhamad Azwardi)")
                    .setPositiveButton("OK", null)
                    .show()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}