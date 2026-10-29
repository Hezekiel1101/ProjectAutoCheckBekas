package id.ac.stmi.autocekbekas

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.stmi.autocekbekas.databinding.ItemInspeksiBinding

class InspeksiAdapter(
    private val onClick: (Inspeksi) -> Unit,
    private val onMore: (Inspeksi, View) -> Unit,
    private val onDelete: (Inspeksi) -> Unit
) : RecyclerView.Adapter<InspeksiAdapter.VH>() {

    private var data = listOf<Inspeksi>()

    fun submit(list: List<Inspeksi>) {
        data = list
        notifyDataSetChanged()
    }

    class VH(val b: ItemInspeksiBinding) :
        RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemInspeksiBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(h: VH, position: Int) {
        val item = data[position]
        val warna = item.rekomendasi.warna

        with(h.b) {
            tvEmoji.text = emoji(item.jenis)
            tvNama.text = item.nama

            // Logika Tampilan Info & Harga Pasar
            if (item.hargaPembanding > 0) {
                val hasilPenilaian = penilaianHarga(item.harga, item.hargaPembanding)
                tvInfo.text = "${item.infoSingkat()} · ${item.tanggal}\n" +
                        "Pasar: Rp ${rupiah(item.hargaPembanding)} ($hasilPenilaian)"
            } else {
                tvInfo.text = "${item.infoSingkat()} · ${item.tanggal}"
            }

            tvHarga.text = "Rp ${rupiah(item.harga)}"

            progressSkor.setIndicatorColor(warna)
            progressSkor.setProgressCompat(item.skor, false)

            tvBadge.text = "${item.skor} · ${item.rekomendasi.judul}"
            tvBadge.backgroundTintList = ColorStateList.valueOf(warna)

            root.setOnClickListener {
                onClick(item)
            }

            ivMenu.setOnClickListener {
                onMore(item, ivMenu)
            }

            // Menu hapus saat item ditekan lama
            root.setOnCreateContextMenuListener { menu, _, _ ->
                menu.setHeaderTitle(item.nama)

                menu.add("Hapus inspeksi").setOnMenuItemClickListener {
                    onDelete(item)
                    true
                }
            }
        }
    }

    private fun emoji(jenis: String): String {
        return when (jenis.trim().lowercase()) {
            "mobil" -> "🚗"
            "motor" -> "🏍️"
            "truk" -> "🚚"
            "bus" -> "🚌"
            else -> "🚘"
        }
    }
}