package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Locale;

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

    // ====================================================================
    // METODE-METODE MIGRASI DARI PRAKTIKUM 1
    // ====================================================================

    @GetMapping("/informasi-nim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        Map<String, String> programStudi = new HashMap<>();
        programStudi.put("11S", "Sarjana Informatika");
        programStudi.put("12S", "Sarjana Sistem Informasi");
        programStudi.put("14S", "Sarjana Teknik Elektro");
        programStudi.put("21S", "Sarjana Manajemen Rekayasa");
        programStudi.put("22S", "Sarjana Teknik Metalurgi");
        programStudi.put("31S", "Sarjana Teknik Bioproses");
        programStudi.put("114", "Diploma 4 Teknologi Rekayasa Perangkat Lunak");
        programStudi.put("113", "Diploma 3 Teknologi Informasi");
        programStudi.put("133", "Diploma 3 Teknologi Komputer");

        String prefix = nim.substring(0, 3);
        String angkatan = "20" + nim.substring(3, 5);
        int urutInt = Integer.parseInt(nim.substring(nim.length() - 3));
        String urutan = String.valueOf(urutInt);

        return String.format(
            "Informasi NIM %s:<br/>" +
            ">> Program Studi: %s<br/>" +
            ">> Angkatan: %s<br/>" +
            ">> Urutan: %s",
            nim,
            programStudi.getOrDefault(prefix, "Program studi tidak dikenal"),
            angkatan,
            urutan
        );
    }

    @GetMapping("/perolehan-nilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        Locale.setDefault(Locale.US);
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedString = new String(decodedBytes);
        String[] lines = decodedString.split("\\r?\\n");

        String[] weights = lines[0].split(" ");
        int wPA = Integer.parseInt(weights[0]);
        int wT = Integer.parseInt(weights[1]);
        int wK = Integer.parseInt(weights[2]);
        int wP = Integer.parseInt(weights[3]);
        int wUTS = Integer.parseInt(weights[4]);
        int wUAS = Integer.parseInt(weights[5]);

        int sumPA = 0, maxPA = 0, sumT = 0, maxT = 0, sumK = 0, maxK = 0;
        int sumP = 0, maxP = 0, sumUTS = 0, maxUTS = 0, sumUAS = 0, maxUAS = 0;

        for (int i = 1; i < lines.length; i++) {
            String baris = lines[i].trim();
            if (baris.equals("---")) break;

            String[] data = baris.split("\\|");
            String kategori = data[0];
            int max = Integer.parseInt(data[1]);
            int nilai = Integer.parseInt(data[2]);

            // PERBAIKAN: Menambahkan 'default' untuk mencakup semua cabang
            switch (kategori) {
                case "PA": maxPA += max; sumPA += nilai; break;
                case "T": maxT += max; sumT += nilai; break;
                case "K": maxK += max; sumK += nilai; break;
                case "P": maxP += max; sumP += nilai; break;
                case "UTS": maxUTS += max; sumUTS += nilai; break;
                case "UAS": maxUAS += max; sumUAS += nilai; break;
                default: // Menangani kategori yang tidak dikenal agar cabang ter-cover
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
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
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
                for (int i = 0; i < n; i++) { nilaiL += matrix[i][0]; }
                for (int j = 1; j < n - 1; j++) { nilaiL += matrix[n - 1][j]; }
                for (int j = 1; j < n; j++) { nilaiKebalikanL += matrix[0][j]; }
                for (int i = 1; i < n; i++) { nilaiKebalikanL += matrix[i][n - 1]; }

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

    @GetMapping("/paling-ter/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String decodedString = new String(decodedBytes);
        String[] lines = decodedString.split("\\r?\\n");

        HashMap<Integer, Integer> freqMap = new HashMap<>();
        ArrayList<Integer> listAngka = new ArrayList<>();

        for (String line : lines) {
            if (line.equals("---")) break;
            int nilai = Integer.parseInt(line);
            listAngka.add(nilai);
            freqMap.put(nilai, freqMap.getOrDefault(nilai, 0) + 1);
        }

        if (listAngka.isEmpty()) {
            return "Tidak ada data untuk diproses.";
        }

        int[] arr = listAngka.stream().mapToInt(Integer::intValue).toArray();
        
        int nilaiTertinggi = arr[0];
        int nilaiTerendah  = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (nilaiTertinggi < arr[i]) nilaiTertinggi = arr[i];
            if (nilaiTerendah > arr[i]) nilaiTerendah = arr[i];
        }

        int nilaiTerbanyak = -1;
        int frekuensiTerbanyak = 0;
        for (int num : listAngka) {
            int frekSaatIni = freqMap.get(num);
            if (frekSaatIni > frekuensiTerbanyak) {
                frekuensiTerbanyak = frekSaatIni;
            }
        }
        for (int num : listAngka) {
            if (freqMap.get(num) == frekuensiTerbanyak) {
                nilaiTerbanyak = num;
                break;
            }
        }
        
        int nilaiTersedikit = -1;
        int frekuensiTersedikit = Integer.MAX_VALUE;
        for (int num : listAngka) {
            int frekSaatIni = freqMap.get(num);
            if (frekSaatIni < frekuensiTersedikit) {
                frekuensiTersedikit = frekSaatIni;
            }
        }
        for (int num : listAngka) {
            if (freqMap.get(num) == frekuensiTersedikit) {
                nilaiTersedikit = num;
                break;
            }
        }

        int nilaiJumlahTertinggi = -1, jumlahTertinggi = -1;
        int nilaiJumlahTerendah = -1, jumlahTerendah = Integer.MAX_VALUE;

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int angka = entry.getKey();
            int frek = entry.getValue();
            int jumlah = angka * frek;

            if (jumlah > jumlahTertinggi) {
                jumlahTertinggi = jumlah;
                nilaiJumlahTertinggi = angka;
            } else if (jumlah == jumlahTertinggi) {
                if (angka > nilaiJumlahTertinggi) {
                    nilaiJumlahTertinggi = angka;
                }
            }

            if (jumlah < jumlahTerendah) {
                jumlahTerendah = jumlah;
                nilaiJumlahTerendah = angka;
            } else if (jumlah == jumlahTerendah) {
                if (angka < nilaiJumlahTerendah) {
                    nilaiJumlahTerendah = angka;
                }
            }
        }

        return String.format(
            "Tertinggi: %d<br/>" +
            "Terendah: %d<br/>" +
            "Terbanyak: %d (%dx)<br/>" +
            "Tersedikit: %d (%dx)<br/>" +
            "Jumlah Tertinggi: %d * %d = %d<br/>" +
            "Jumlah Terendah: %d * %d = %d",
            nilaiTertinggi,
            nilaiTerendah,
            nilaiTerbanyak, frekuensiTerbanyak,
            nilaiTersedikit, frekuensiTersedikit,
            nilaiJumlahTertinggi, freqMap.get(nilaiJumlahTertinggi), jumlahTertinggi,
            nilaiJumlahTerendah, freqMap.get(nilaiJumlahTerendah), jumlahTerendah
        );
    }
}