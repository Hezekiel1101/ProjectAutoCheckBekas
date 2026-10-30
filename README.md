# AutoCekBekas

> Sistem Informasi Inspeksi Kendaraan dan Penilaian Kelayakan Harga Mobil Bekas  
> Dikembangkan sebagai aplikasi Android Native menggunakan bahasa pemrograman Kotlin.

---

## 1. Deskripsi Sistem

**AutoCekBekas** merupakan aplikasi berbasis Android yang dirancang untuk membantu pengguna dalam melakukan pemeriksaan kondisi kendaraan serta memperoleh gambaran mengenai kelayakan harga mobil bekas.

Aplikasi ini berfokus pada proses inspeksi kendaraan melalui beberapa komponen utama:

1. **Pemeriksaan Kendaraan:** Menyediakan fitur untuk melakukan pemeriksaan terhadap kondisi mobil bekas melalui tahapan inspeksi yang tersedia di dalam aplikasi.
2. **Pengelolaan Data Inspeksi:** Mengelola informasi dan data hasil pemeriksaan kendaraan agar dapat tersusun secara sistematis.
3. **Hasil Pemeriksaan:** Menampilkan rangkuman hasil inspeksi sebagai informasi pendukung dalam proses penilaian kondisi kendaraan.

Aplikasi ini dikembangkan menggunakan bahasa pemrograman Kotlin dengan pendekatan Android Native serta memanfaatkan komponen antarmuka XML untuk membangun tampilan aplikasi.

---

## 2. Arsitektur dan Komponen Aplikasi

Pengembangan AutoCekBekas disusun menggunakan beberapa komponen utama yang memiliki tanggung jawab berbeda dalam menjalankan fungsi aplikasi.

| Komponen | Berkas Implementasi | Tanggung Jawab |
|---|---|---|
| Halaman Utama | `MainActivity.kt` | Menangani halaman utama aplikasi dan interaksi pengguna. |
| Model Inspeksi | `Inpeksi.kt` | Merepresentasikan struktur data inspeksi kendaraan. |
| Halaman Inspeksi | `InspeksiActivity.kt` | Menangani proses pemeriksaan dan pengisian data inspeksi. |
| Adapter Inspeksi | `InspeksiAdapter.kt` | Mengatur penyajian data inspeksi pada komponen antarmuka. |
| Pengelolaan Data | `InspeksiStore.kt` | Menangani pengelolaan dan penyimpanan data inspeksi. |
| Halaman Hasil | `HasilActivity.kt` | Menampilkan hasil pemeriksaan kendaraan. |

---

## 3. Alur Kerja Aplikasi (Workflow)

Alur penggunaan aplikasi AutoCekBekas secara umum terdiri dari tahapan berikut:

```text
              ┌──────────────────────┐
              │     AUTOCEKBEKAS     │
              │     Aplikasi Android │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │     Halaman Utama    │
              │     MainActivity     │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   Proses Inspeksi    │
              │   InspeksiActivity   │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │   Pengelolaan Data   │
              │    InspeksiStore     │
              └──────────┬───────────┘
                         │
                         ▼
              ┌──────────────────────┐
              │    Hasil Inspeksi    │
              │    HasilActivity     │
              └──────────────────────┘
```

---

## 4. Fitur Utama Aplikasi

### A. Halaman Utama

- Menjadi halaman awal untuk mengakses fungsi utama aplikasi.
- Menyediakan navigasi menuju proses inspeksi kendaraan.
- Menjadi pusat interaksi pengguna dengan aplikasi.

### B. Pemeriksaan Kendaraan

- Menyediakan halaman khusus untuk melakukan inspeksi kendaraan.
- Mendukung pengisian informasi pemeriksaan melalui antarmuka aplikasi.
- Menggunakan komponen antarmuka yang disusun untuk mendukung proses input data.

### C. Pengelolaan Data Inspeksi

- Menggunakan model data inspeksi sebagai representasi informasi kendaraan.
- Mengelola data melalui komponen `InspeksiStore`.
- Menggunakan `InspeksiAdapter` untuk membantu menampilkan data pada antarmuka aplikasi.

### D. Hasil Inspeksi

- Menyediakan halaman untuk menampilkan hasil pemeriksaan.
- Menyajikan informasi inspeksi dalam bentuk tampilan yang terstruktur.
- Menjadi bagian akhir dari alur pemeriksaan kendaraan.

---

## 5. Struktur Berkas Proyek

```text
AutoCekBekas/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/id/ac/stmi/autocekbekas/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── Inpeksi.kt
│   │   │   │   ├── InspeksiActivity.kt
│   │   │   │   ├── InspeksiAdapter.kt
│   │   │   │   ├── InspeksiStore.kt
│   │   │   │   └── HasilActivity.kt
│   │   │   │
│   │   │   ├── res/
│   │   │   │   ├── drawable/
│   │   │   │   ├── layout/
│   │   │   │   ├── menu/
│   │   │   │   ├── mipmap/
│   │   │   │   └── values/
│   │   │   │
│   │   │   └── AndroidManifest.xml
│   │   │
│   │   ├── androidTest/
│   │   └── test/
│   │
│   └── build.gradle.kts
│
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
```

---

## 6. Spesifikasi Lingkungan Pengembangan

- **Platform:** Android
- **Bahasa Pemrograman:** Kotlin
- **Lingkungan Pengembangan:** Android Studio
- **Perancangan Antarmuka:** XML Layout
- **Sistem Build:** Gradle
- **Version Control:** Git
- **Repository:** GitHub

---

## 7. Tim Pengembang

Aplikasi AutoCekBekas dikembangkan secara kolaboratif oleh Kelompok 6 sebagai bagian dari proyek akademik mata kuliah Pemrograman Aplikasi Bergerak.

| No. | Nama Anggota |
|---|---|
| 01 | Ahmad Muzaki |
| 02 | Hezekiel Berkat Lamtama |
| 03 | Atila Ahmad Jilan |
| 04 | Muhamad Azwardi Azis |

---

## 8. Informasi Proyek

| Kategori | Keterangan |
|---|---|
| Nama Aplikasi | AutoCekBekas |
| Jenis Aplikasi | Aplikasi Android Native |
| Bidang | Inspeksi Kendaraan dan Mobil Bekas |
| Bahasa Pemrograman | Kotlin |
| Mata Kuliah | Pemrograman Aplikasi Bergerak |
| Tahun Pengembangan | 2026 |
| Repository | GitHub |

---

**AutoCekBekas**  
*Dikembangkan oleh Kelompok 6 — Pemrograman Aplikasi Bergerak.*
