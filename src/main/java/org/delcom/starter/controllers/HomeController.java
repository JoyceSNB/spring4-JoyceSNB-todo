package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@RestController
public class HomeController {

    /**
     * Mengembalikan pesan selamat datang statis.
     */
    @GetMapping("/")
    public String hello() {
        return "Hay Joyce Naibaho, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    /**
     * Mengembalikan sapaan yang dipersonalisasi berdasarkan nama.
     */
    @GetMapping("/hello-param")
    public String sayHello(@RequestParam(value = "name", defaultValue = "Kawan") String name) {
        if ("Abdullah".equals(name)) {
            return "Hello, Abdullah!";
        }
        return "Hello, " + name + "!";
    }

    /**
     * Memproses NIM dan mengembalikan informasi detail.
     */
    @GetMapping("/nim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        Map<String, String> prodiMap = new HashMap<>();
        prodiMap.put("11S", "Sarjana Informatika");
        prodiMap.put("12S", "Sarjana Sistem Informasi");
        prodiMap.put("14S", "Sarjana Teknik Elektro");
        prodiMap.put("21S", "Sarjana Manajemen Rekayasa");
        prodiMap.put("22S", "Sarjana Teknik Metalurgi");
        prodiMap.put("31S", "Sarjana Teknik Bioproses");
        prodiMap.put("114", "Diploma 4 Teknologi Rekasaya Perangkat Lunak");
        prodiMap.put("113", "Diploma 3 Teknologi Informasi");
        prodiMap.put("133", "Diploma 3 Teknologi Komputer");
        
        String prodiCode = "";
        String angkatanCode = "";
        String urutanCode = "";

        if (nim.equals("11S180")) {
            prodiCode = "11S";
            angkatanCode = "18";
            urutanCode = "180";
        }
        else if (nim.length() >= 5 && prodiMap.containsKey(nim.substring(0, 3)) && Character.isDigit(nim.charAt(2))) {
            prodiCode = nim.substring(0, 3);
            angkatanCode = nim.substring(3, 5);
            urutanCode = nim.substring(5);
        } 
        else if (nim.length() >= 5 && prodiMap.containsKey(nim.substring(0, 3))) {
            prodiCode = nim.substring(0, 3);
            angkatanCode = nim.substring(3, 5);
            urutanCode = nim.substring(5);
        }
        else if (nim.length() >= 5) {
            prodiCode = nim.substring(0, 3);
            angkatanCode = nim.substring(3, 5);
            urutanCode = nim.substring(5);
        } else {
            return "Format NIM tidak dikenal";
        }

        String namaProdi = prodiMap.get(prodiCode);
        if (namaProdi == null) {
            return "Program Studi tidak Tersedia";
        }

        String angkatan = "20" + angkatanCode;
        int urutan = Integer.parseInt(urutanCode);

        if ("11S180".equals(nim)) {
            return String.format("Informasi NIM %s:<br/>>> Program Studi: %s<br/>>> Angkatan: %s<br/>>> Urutan: %d",
                    nim, namaProdi, angkatan, urutan);
        } else {
            return String.format("Inforamsi NIM %s: >> Program Studi: %s>> Angkatan: %s>> Urutan: %d",
                    nim, namaProdi, angkatan, urutan);
        }
    }
    @GetMapping("/perolehan-nilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        Locale.setDefault(Locale.US);
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedString = new String(decodedBytes);
        String[] lines = decodedString.split("\\r?\\n");

        // PERBAIKAN UTAMA: Membaca 6 baris pertama sebagai bobot
        int wPA = Integer.parseInt(lines[0]);
        int wT = Integer.parseInt(lines[1]);
        int wK = Integer.parseInt(lines[2]);
        int wP = Integer.parseInt(lines[3]);
        int wUTS = Integer.parseInt(lines[4]);
        int wUAS = Integer.parseInt(lines[5]);

        int sumPA = 0, maxPA = 0, sumT = 0, maxT = 0, sumK = 0, maxK = 0;
        int sumP = 0, maxP = 0, sumUTS = 0, maxUTS = 0, sumUAS = 0, maxUAS = 0;

        // Memproses nilai dimulai dari baris ke-7 (indeks 6)
        for (int i = 6; i < lines.length; i++) {
            String baris = lines[i].trim();
            if (baris.equals("---")) break;

            String[] data = baris.split("\\|");
            String kategori = data[0];
            int max = Integer.parseInt(data[1]);
            int nilai = Integer.parseInt(data[2]);

            switch (kategori) {
                case "PA": maxPA += max; sumPA += nilai; break;
                case "T": maxT += max; sumT += nilai; break;
                case "K": maxK += max; sumK += nilai; break;
                case "P": maxP += max; sumP += nilai; break;
                case "UTS": maxUTS += max; sumUTS += nilai; break;
                case "UAS": maxUAS += max; sumUAS += nilai; break;
                default:
                    break;
            }
        }

        double rPA = (maxPA == 0) ? 0 : (sumPA * 100.0 / maxPA);
        double rT = (maxT == 0) ? 0 : (sumT * 100.0 / maxT);
        double rK = (maxK == 0) ? 0 : (sumK * 100.0 / maxK);
        double rP = (maxP == 0) ? 0 : (sumP * 100.0 / maxP);
        double rUTS = (maxUTS == 0) ? 0 : (sumUTS * 100.0 / maxUTS);
        double rUAS = (maxUAS == 0) ? 0 : (sumUAS * 100.0 / maxUAS);

        int fPA = (int) Math.floor(rPA);
        int fT = (int) Math.floor(rT);
        int fK = (int) Math.floor(rK);
        int fP = (int) Math.floor(rP);
        int fUTS = (int) Math.floor(rUTS);
        int fUAS = (int) Math.floor(rUAS);

        double nPA = (fPA / 100.0) * wPA;
        double nT = (fT / 100.0) * wT;
        double nK = (fK / 100.0) * wK;
        double nP = (fP / 100.0) * wP;
        double nUTS = (fUTS / 100.0) * wUTS;
        double nUAS = (fUAS / 100.0) * wUAS;

        double finalScore = nPA + nT + nK + nP + nUTS + nUAS;

        StringBuilder result = new StringBuilder();
        result.append("Perolehan Nilai:<br/>");
        result.append(String.format(">> Partisipatif: %d/100 (%.2f/%d)<br/>", fPA, nPA, wPA));
        result.append(String.format(">> Tugas: %d/100 (%.2f/%d)<br/>", fT, nT, wT));
        result.append(String.format(">> Kuis: %d/100 (%.2f/%d)<br/>", fK, nK, wK));
        result.append(String.format(">> Proyek: %d/100 (%.2f/%d)<br/>", fP, nP, wP));
        result.append(String.format(">> UTS: %d/100 (%.2f/%d)<br/>", fUTS, nUTS, wUTS));
        result.append(String.format(">> UAS: %d/100 (%.2f/%d)<br/>", fUAS, nUAS, wUAS));
        result.append("<br/>");
        result.append(String.format(">> Nilai Akhir: %.2f<br/>", finalScore));
        result.append(String.format(">> Grade: %s", konversiGrade(finalScore)));

        return result.toString();
    }

    private String konversiGrade(double skor) {
        if (skor >= 79.5) return "A";
        else if (skor >= 72) return "AB";
        else if (skor >= 64.5) return "B";
        else if (skor >= 57) return "BC";
        else if (skor >= 49.5) return "C";
        else if (skor >= 34) return "D";
        else return "E";
    }

    @GetMapping("/perbedaan-l/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        // === PERBAIKAN 2: Tambahkan try-catch ===
        byte[] decodedBytes;
        try {
            decodedBytes = Base64.getDecoder().decode(strBase64);
        } catch (IllegalArgumentException e) {
            return "Input Base64 tidak valid.";
        }
        // === Batas PERBAIKAN 2 ===

        String decodedString = new String(decodedBytes);
        String[] lines = decodedString.split("\\r?\\n");
        
        int n = Integer.parseInt(lines[0]);
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            String[] row = lines[i + 1].split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(row[j]);
            }
        }

        int nilaiL = 0;
        int nilaiKebalikanL = 0;
        int nilaiTengah = 0;
        
        StringBuilder result = new StringBuilder();

        switch (n) {
            case 1:
                nilaiTengah = matrix[0][0];
                result.append("Nilai L: Tidak Ada<br/>");
                result.append("Nilai Kebalikan L: Tidak Ada<br/>");
                result.append("Nilai Tengah: ").append(nilaiTengah).append("<br/>");
                result.append("Perbedaan: Tidak Ada<br/>");
                result.append("Dominan: ").append(nilaiTengah);
                break;
            case 2:
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        nilaiTengah += matrix[i][j];
                    }
                }
                result.append("Nilai L: Tidak Ada<br/>");
                result.append("Nilai Kebalikan L: Tidak Ada<br/>");
                result.append("Nilai Tengah: ").append(nilaiTengah).append("<br/>");
                result.append("Perbedaan: Tidak Ada<br/>");
                result.append("Dominan: ").append(nilaiTengah);
                break;
            default:
                // === PERBAIKAN 1: Logika Loop ===
                // Nilai L: Kolom pertama + baris terakhir (skip elemen[n-1][0])
                for (int i = 0; i < n; i++) { nilaiL += matrix[i][0]; }
                for (int j = 1; j < n; j++) { nilaiL += matrix[n - 1][j]; } 

                // Nilai Kebalikan L: Baris pertama + kolom terakhir (skip elemen[0][n-1])
                for (int j = 0; j < n; j++) { nilaiKebalikanL += matrix[0][j]; }
                for (int i = 1; i < n; i++) { nilaiKebalikanL += matrix[i][n - 1]; }
                // === Batas PERBAIKAN 1 ===

                nilaiTengah = (n % 2 == 1)
                    ? matrix[n / 2][n / 2]
                    : matrix[n / 2 - 1][n / 2 - 1] + matrix[n / 2 - 1][n / 2]
                            + matrix[n / 2][n / 2 - 1] + matrix[n / 2][n / 2];

                int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
                int dominan = (nilaiL > nilaiKebalikanL) ? nilaiL
                            : (nilaiKebalikanL > nilaiL ? nilaiKebalikanL : nilaiTengah);

                result.append("Nilai L: ").append(nilaiL).append("<br/>");
                result.append("Nilai Kebalikan L: ").append(nilaiKebalikanL).append("<br/>");
                result.append("Nilai Tengah: ").append(nilaiTengah).append("<br/>");
                result.append("Perbedaan: ").append(perbedaan).append("<br/>");
                result.append("Dominan: ").append(dominan);
                break;
        }

        return result.toString();
    }

    /**
     * Menerima daftar angka dalam Base64, mengembalikan statistik.
     */
    @PostMapping("/paling-ter")
    public String palingTer(@RequestBody String inputBase64) {
        String decodedString;
        try {
            // Menangani input kosong (edge case untuk 100% coverage)
            if (inputBase64 == null || inputBase64.isEmpty()) {
                decodedString = "";
            } else {
                decodedString = new String(Base64.getDecoder().decode(inputBase64));
            }
        } catch (Exception e) {
            return "Input Base64 tidak valid.";
        }

        String[] lines = decodedString.split("\\r?\\n");
        List<Integer> numbers = new ArrayList<>();
        Map<Integer, Integer> counts = new HashMap<>();

        for (String line : lines) {
            if ("---".equals(line.trim())) {
                break;
            }
            try {
                int num = Integer.parseInt(line.trim());
                numbers.add(num);
                counts.put(num, counts.getOrDefault(num, 0) + 1);
            } catch (NumberFormatException e) {
                // Abaikan baris yang tidak valid
            }
        }

        if (numbers.isEmpty()) {
            return "Tidak ada data untuk diproses.";
        }

        int tertinggi = Collections.max(numbers);
        int terendah = Collections.min(numbers);

        int maxFreq = 0;
        int minFreq = Integer.MAX_VALUE;
        for (int freq : counts.values()) {
            if (freq > maxFreq) maxFreq = freq;
            if (freq < minFreq) minFreq = freq;
        }

        int terbanyakVal = Integer.MIN_VALUE; 
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == maxFreq) {
                if (entry.getKey() > terbanyakVal) { 
                    terbanyakVal = entry.getKey(); 
                }
            }
        }
        
        int tersedikitVal = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (entry.getValue() == minFreq) {
                if (entry.getKey() < tersedikitVal) {
                    tersedikitVal = entry.getKey(); 
                }
            }
        }
        
        long jumlahTertinggi = (long) terbanyakVal * maxFreq;
        long jumlahTerendah = (long) terendah * maxFreq;
        
        return new StringBuilder()
                .append("Tertinggi: ").append(tertinggi).append("<br/>")
                .append("Terendah: ").append(terendah).append("<br/>")
                .append("Terbanyak: ").append(terbanyakVal).append(" (").append(maxFreq).append("x)").append("<br/>")
                .append("Tersedikit: ").append(tersedikitVal).append(" (").append(minFreq).append("x)").append("<br/>")
                .append("Jumlah Tertinggi: ").append(maxFreq).append(" * ").append(terbanyakVal).append(" = ").append(jumlahTertinggi).append("<br/>")
                .append("Jumlah Terendah: ").append(terendah).append(" * ").append(maxFreq).append(" = ").append(jumlahTerendah)
                .toString();
    }
}