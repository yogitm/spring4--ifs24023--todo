package org.delcom.starter.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // ============================================
    // 1️⃣ Informasi NIM
    // ============================================
    @GetMapping("/informasiNim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        // Validasi panjang NIM
        if (nim.length() < 8) {
            return "NIM harus terdiri dari minimal 8 karakter!";
        }

        // Ambil bagian NIM
        String prefix = nim.substring(0, 3);
        String angkatan = "20" + nim.substring(3, 5);
        String nomorUrut = nim.substring(5, 8);

        // Tentukan program studi
        String prodi;
        switch (prefix) {
            case "11S":
                prodi = "Sarjana Informatika";
                break;
            case "12S":
                prodi = "Sarjana Sistem Informasi";
                break;
            case "14S":
                prodi = "Sarjana Teknik Elektro";
                break;
            case "21S":
                prodi = "Sarjana Manajemen Rekayasa";
                break;
            case "22S":
                prodi = "Sarjana Teknik Metalurgi";
                break;
            case "31S":
                prodi = "Sarjana Teknik Bioproses";
                break;
            case "114":
                prodi = "Diploma 4 Teknologi Rekayasa Perangkat Lunak";
                break;
            case "113":
                prodi = "Diploma 3 Teknologi Informasi";
                break;
            case "133":
                prodi = "Diploma 3 Teknologi Komputer";
                break;
            default:
                prodi = "Tidak dikenal";
                break;
        }

        // Buat hasil dalam format HTML agar rapi di browser
        return "<h3>Informasi NIM: " + nim + "</h3>"
                + "<ul>"
                + "<li>Program Studi: " + prodi + "</li>"
                + "<li>Angkatan: " + angkatan + "</li>"
                + "<li>Nomor Urut: " + Integer.parseInt(nomorUrut) + "</li>"
                + "</ul>";
    }

    // ============================================
    // 2️⃣ Perolehan Nilai
    // ============================================
    @GetMapping("/perolehanNilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        // Decode dari Base64 ke teks biasa
        String decodedInput = new String(Base64.getDecoder().decode(strBase64));

        Scanner sc = new Scanner(decodedInput);
        Locale.setDefault(Locale.US);

        // Bobot komponen
        int bobotPA = sc.nextInt();
        int bobotT = sc.nextInt();
        int bobotK = sc.nextInt();
        int bobotP = sc.nextInt();
        int bobotUTS = sc.nextInt();
        int bobotUAS = sc.nextInt();
        sc.nextLine();

        int totalPA = 0, maxPA = 0;
        int totalT = 0, maxT = 0;
        int totalK = 0, maxK = 0;
        int totalP = 0, maxP = 0;
        int totalUTS = 0, maxUTS = 0;
        int totalUAS = 0, maxUAS = 0;

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equals("---"))
                break;

            String[] parts = line.split("\\|");
            if (parts.length < 3)
                continue;

            String simbol = parts[0];
            int maks = Integer.parseInt(parts[1]);
            int nilai = Integer.parseInt(parts[2]);

            switch (simbol) {
                case "PA":
                    maxPA += maks;
                    totalPA += nilai;
                    break;
                case "T":
                    maxT += maks;
                    totalT += nilai;
                    break;
                case "K":
                    maxK += maks;
                    totalK += nilai;
                    break;
                case "P":
                    maxP += maks;
                    totalP += nilai;
                    break;
                case "UTS":
                    maxUTS += maks;
                    totalUTS += nilai;
                    break;
                case "UAS":
                    maxUAS += maks;
                    totalUAS += nilai;
                    break;
            }
        }
        sc.close();

        // Hitung rata-rata
        double rataPA = (maxPA == 0) ? 0 : (totalPA * 100.0 / maxPA);
        double rataT = (maxT == 0) ? 0 : (totalT * 100.0 / maxT);
        double rataK = (maxK == 0) ? 0 : (totalK * 100.0 / maxK);
        double rataP = (maxP == 0) ? 0 : (totalP * 100.0 / maxP);
        double rataUTS = (maxUTS == 0) ? 0 : (totalUTS * 100.0 / maxUTS);
        double rataUAS = (maxUAS == 0) ? 0 : (totalUAS * 100.0 / maxUAS);

        // Pembulatan
        int bulatPA = (int) Math.floor(rataPA);
        int bulatT = (int) Math.floor(rataT);
        int bulatK = (int) Math.floor(rataK);
        int bulatP = (int) Math.floor(rataP);
        int bulatUTS = (int) Math.floor(rataUTS);
        int bulatUAS = (int) Math.floor(rataUAS);

        // Kontribusi nilai akhir
        double nilaiPA = (bulatPA / 100.0) * bobotPA;
        double nilaiT = (bulatT / 100.0) * bobotT;
        double nilaiK = (bulatK / 100.0) * bobotK;
        double nilaiP = (bulatP / 100.0) * bobotP;
        double nilaiUTS = (bulatUTS / 100.0) * bobotUTS;
        double nilaiUAS = (bulatUAS / 100.0) * bobotUAS;

        double totalNilai = nilaiPA + nilaiT + nilaiK + nilaiP + nilaiUTS + nilaiUAS;

        // Konversi ke grade
        String grade;
        if (totalNilai >= 79.5)
            grade = "A";
        else if (totalNilai >= 72)
            grade = "AB";
        else if (totalNilai >= 64.5)
            grade = "B";
        else if (totalNilai >= 57)
            grade = "BC";
        else if (totalNilai >= 49.5)
            grade = "C";
        else if (totalNilai >= 34)
            grade = "D";
        else
            grade = "E";

        return String.format("""
                <h3>Perolehan Nilai:</h3>
                <ul>
                  <li>Partisipatif: %d/100 (%.2f/%d)</li>
                  <li>Tugas: %d/100 (%.2f/%d)</li>
                  <li>Kuis: %d/100 (%.2f/%d)</li>
                  <li>Proyek: %d/100 (%.2f/%d)</li>
                  <li>UTS: %d/100 (%.2f/%d)</li>
                  <li>UAS: %d/100 (%.2f/%d)</li>
                </ul>
                <b>Nilai Akhir:</b> %.2f<br>
                <b>Grade:</b> %s
                """,
                bulatPA, nilaiPA, bobotPA,
                bulatT, nilaiT, bobotT,
                bulatK, nilaiK, bobotK,
                bulatP, nilaiP, bobotP,
                bulatUTS, nilaiUTS, bobotUTS,
                bulatUAS, nilaiUAS, bobotUAS,
                totalNilai, grade);
    }

    // ============================================
    // Perbedaan L dan Kebalikannya

    @GetMapping("/perbedaanL/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        try {
            // Decode data Base64 → ambil isi teks
            byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
            String decodedText = new String(decodedBytes);

            Scanner scanner = new Scanner(decodedText.trim());
            int n = scanner.nextInt();

            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = scanner.nextInt();
                }
            }

            int nilaiL = 0;
            int nilaiKebalikanL = 0;
            int nilaiTengah = 0;

            // Kasus 1x1
            if (n == 1) {
                nilaiTengah = matrix[0][0];
                return formatHTML(nilaiL, nilaiKebalikanL, nilaiTengah, "Tidak Ada", nilaiTengah);
            }

            // Kasus 2x2
            if (n == 2) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        nilaiTengah += matrix[i][j];
                    }
                }
                return formatHTML(nilaiL, nilaiKebalikanL, nilaiTengah, "Tidak Ada", nilaiTengah);
            }

            // Hitung nilai L
            for (int i = 0; i < n; i++)
                nilaiL += matrix[i][0];
            for (int j = 1; j < n - 1; j++)
                nilaiL += matrix[n - 1][j];

            // Hitung nilai kebalikan L
            for (int j = 1; j < n; j++)
                nilaiKebalikanL += matrix[0][j];
            for (int i = 1; i < n; i++)
                nilaiKebalikanL += matrix[i][n - 1];

            // Hitung nilai tengah
            if (n % 2 == 1) {
                nilaiTengah = matrix[n / 2][n / 2];
            } else {
                int a = n / 2 - 1;
                int b = n / 2;
                nilaiTengah = matrix[a][a] + matrix[a][b] + matrix[b][a] + matrix[b][b];
            }

            // Perbedaan dan dominan
            int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
            int dominan = (nilaiL > nilaiKebalikanL) ? nilaiL
                    : (nilaiKebalikanL > nilaiL ? nilaiKebalikanL : nilaiTengah);

            scanner.close();

            return "<h3>Perbedaan L dan Kebalikannya</h3>"
                    + "<ul>"
                    + "<li>Nilai L: " + nilaiL + "</li>"
                    + "<li>Nilai Kebalikan L: " + nilaiKebalikanL + "</li>"
                    + "<li>Nilai Tengah: " + nilaiTengah + "</li>"
                    + "<li>Perbedaan: " + perbedaan + "</li>"
                    + "<li>Dominan: " + dominan + "</li>"
                    + "</ul>";

        } catch (Exception e) {
            return "Terjadi kesalahan: " + e.getMessage();
        }
    }

    // helper untuk output HTML
    private String formatHTML(int nilaiL, int nilaiKebalikanL, int nilaiTengah, String perbedaan, int dominan) {
        return "<h3>Perbedaan L dan Kebalikannya</h3>"
                + "<ul>"
                + "<li>Nilai L: " + nilaiL + "</li>"
                + "<li>Nilai Kebalikan L: " + nilaiKebalikanL + "</li>"
                + "<li>Nilai Tengah: " + nilaiTengah + "</li>"
                + "<li>Perbedaan: " + perbedaan + "</li>"
                + "<li>Dominan: " + dominan + "</li>"
                + "</ul>";
    }

    // ============================================
    // Paling Ter
    @GetMapping("/palingTer/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {

        // Decode Base64
        String decoded = new String(Base64.getDecoder().decode(strBase64));
        // Misalnya decoded = "80,90,75,75,100,90"
        String[] parts = decoded.split(",");
        List<Integer> daftarNilai = new ArrayList<>();
        for (String p : parts) {
            daftarNilai.add(Integer.parseInt(p.trim()));
        }

        // Logika utama (dari StudiKasus4)
        int[] arrayNilai = daftarNilai.stream().mapToInt(Integer::intValue).toArray();
        HashMap<Integer, Integer> hashMapNilai = new HashMap<>();

        for (int nilai : arrayNilai) {
            hashMapNilai.put(nilai, hashMapNilai.getOrDefault(nilai, 0) + 1);
        }

        int nilaiTertinggi = Arrays.stream(arrayNilai).max().orElse(0);
        int nilaiTerendah = Arrays.stream(arrayNilai).min().orElse(0);

        // Cari nilai terbanyak dan tersedikit
        int nilaiTerbanyak = arrayNilai[0];
        int frekuensiTerbanyak = 0;
        int nilaiTersedikit = arrayNilai[0];
        int frekuensiTersedikit = Integer.MAX_VALUE;

        for (var e : hashMapNilai.entrySet()) {
            if (e.getValue() > frekuensiTerbanyak) {
                frekuensiTerbanyak = e.getValue();
                nilaiTerbanyak = e.getKey();
            }
            if (e.getValue() < frekuensiTersedikit) {
                frekuensiTersedikit = e.getValue();
                nilaiTersedikit = e.getKey();
            }
        }

        // Jumlah tertinggi & terendah
        int jumlahTertinggi = 0;
        int nilaiJumlahTertinggi = 0;
        int jumlahTerendah = Integer.MAX_VALUE;
        int nilaiJumlahTerendah = 0;

        for (var e : hashMapNilai.entrySet()) {
            int jumlah = e.getKey() * e.getValue();
            if (jumlah > jumlahTertinggi) {
                jumlahTertinggi = jumlah;
                nilaiJumlahTertinggi = e.getKey();
            }
            if (jumlah < jumlahTerendah) {
                jumlahTerendah = jumlah;
                nilaiJumlahTerendah = e.getKey();
            }
        }

        // Kembalikan hasil
        return """
                Tertinggi: %d
                Terendah: %d
                Terbanyak: %d (%dx)
                Tersedikit: %d (%dx)
                Jumlah Tertinggi: %d * %d = %d
                Jumlah Terendah: %d * %d = %d
                """.formatted(
                nilaiTertinggi,
                nilaiTerendah,
                nilaiTerbanyak, frekuensiTerbanyak,
                nilaiTersedikit, frekuensiTersedikit,
                nilaiJumlahTertinggi, hashMapNilai.get(nilaiJumlahTertinggi), jumlahTertinggi,
                nilaiJumlahTerendah, hashMapNilai.get(nilaiJumlahTerendah), jumlahTerendah);
    }

}
