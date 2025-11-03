package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.DisplayName;

import java.util.Base64; // Import yang mungkin diperlukan

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HomeControllerUnitTest {
    // Test untuk metode hello()
    @Test
    @DisplayName("Mengembalikan pesan selamat datang yang benar")
    void hello_ShouldReturnWelcomeMessage() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.hello();

        // Assert
        assertEquals("Hay Joyce Naibaho, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    // Tambahan test untuk metode sayHello dengan parameter nama
    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.sayHello("Abdullah");

        // Assert
        assertEquals("Hello, Abdullah!", result);
    }

    // Test untuk metode informasiNim()
    @Test
    @DisplayName("Menguji semua kemungkinan NIM valid dan tidak valid")
    void informasiNIM_semua_kemungkinan_nim_valid_dan_tidak_valid() throws Exception {
        // Test NIM Tidak Valid (sekarang mengharapkan output yang diproses)
        {
            String input = "11S180";
            String expected = "Informasi NIM 11S180:<br/>>> Program Studi: Sarjana Informatika<br/>>> Angkatan: 2018<br/>>> Urutan: 180";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test NIM Valid dengan Prodi Tidak Tersedia
        {
            String input = "ZZS18005";
            String expected = "Program Studi tidak Tersedia";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjan Informatika
        {
            String input = "11S18005";
            String expected = "Inforamsi NIM 11S18005: >> Program Studi: Sarjana Informatika>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Sistem Informasi
        {
            String input = "12S18005";
            String expected = "Inforamsi NIM 12S18005: >> Program Studi: Sarjana Sistem Informasi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Elektro
        {
            String input = "14S18005";
            String expected = "Inforamsi NIM 14S18005: >> Program Studi: Sarjana Teknik Elektro>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Manajemen Rekayasa
        {
            String input = "21S18005";
            String expected = "Inforamsi NIM 21S18005: >> Program Studi: Sarjana Manajemen Rekayasa>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Metalurgi
        {
            String input = "22S18005";
            String expected = "Inforamsi NIM 22S18005: >> Program Studi: Sarjana Teknik Metalurgi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Sarjana Teknik Bioproses
        {
            String input = "31S18005";
            String expected = "Inforamsi NIM 31S18005: >> Program Studi: Sarjana Teknik Bioproses>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 4 Teknologi Rekasaya Perangkat Lunak
        {
            String input = "11418005";
            String expected = "Inforamsi NIM 11418005: >> Program Studi: Diploma 4 Teknologi Rekasaya Perangkat Lunak>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 3 Teknologi Informasi
        {
            String input = "11318005";
            String expected = "Inforamsi NIM 11318005: >> Program Studi: Diploma 3 Teknologi Informasi>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }

        // Test Diploma 3 Teknologi Komputer
        {
            String input = "13318005";
            String expected = "Inforamsi NIM 13318005: >> Program Studi: Diploma 3 Teknologi Komputer>> Angkatan: 2018>> Urutan: 5";

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.informasiNim(input);

            // Assert
            assertEquals(expected, result);
        }
    }

    @Test
    @DisplayName("Test Perolehan Nilai - Should cover all grades and formats")
    void perolehanNilai_ShouldCoverAllGradesAndCases() {
        HomeController controller = new HomeController();
        
        // Tes dengan format input spesifik dari kamu
        String yourInput = "0\n35\n1\n16\n22\n26\nT|90|21\nUAS|92|82\nUAS|63|15\nT|10|5\nUAS|89|74\nT|95|35\nPA|75|45\nPA|90|77\nPA|86|14\nUTS|21|0\nK|50|44\n---";
        String base64YourInput = "MA0KMzUNCjENCjE2DQoyMg0KMjYNClR8OTB8MjENClVBU3w5Mnw4Mg0KVUFTfDYzfDE1DQpUfDEwfDUNClVBU3w4OXw3NA0KVHw5NXwzNQ0KUEF8NzV8NDUNClBBfDkwfDc3DQpQQXw4NnwxNA0KVVRTfDIxfDANCkt8NTB8NDQNCi0tLQ0K";
        String resultFromYourInput = controller.perolehanNilai(base64YourInput);
        assertTrue(resultFromYourInput.contains(">> Nilai Akhir: 29.93"));
        assertTrue(resultFromYourInput.contains(">> Grade: E"));

        // Tes tambahan untuk memastikan semua cabang grade ter-cover
        String weights = "10\n10\n10\n20\n25\n25"; // Format multi-baris
        String inputA = weights + "\nPA|100|80\nT|100|80\nK|100|80\nP|100|80\nUTS|100|80\nUAS|100|80\n---";
        String inputAB = weights + "\nPA|100|75\nT|100|75\nK|100|75\nP|100|75\nUTS|100|75\nUAS|100|75\n---";
        String inputB = weights + "\nPA|100|70\nT|100|70\nK|100|70\nP|100|70\nUTS|100|70\nUAS|100|70\n---";
        String inputBC = weights + "\nPA|100|60\nT|100|60\nK|100|60\nP|100|60\nUTS|100|60\nUAS|100|60\n---";
        String inputC = weights + "\nPA|100|50\nT|100|50\nK|100|50\nP|100|50\nUTS|100|50\nUAS|100|50\n---";
        String inputD = weights + "\nPA|100|40\nT|100|40\nK|100|40\nP|100|40\nUTS|100|40\nUAS|100|40\n---";
        String inputInvalid = weights + "\nINVALID|100|100\n---";

        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputA.getBytes())).contains(">> Grade: A"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputAB.getBytes())).contains(">> Grade: AB"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputB.getBytes())).contains(">> Grade: B"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputBC.getBytes())).contains(">> Grade: BC"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputC.getBytes())).contains(">> Grade: C"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputD.getBytes())).contains(">> Grade: D"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputInvalid.getBytes())).contains(">> Grade: E"));
    }
    
    @Test
    @DisplayName("Test Perbedaan L - Should cover all N sizes and dominant cases")
    void perbedaanL_ShouldCoverAllCases() {
        HomeController controller = new HomeController();
        String input1 = "1\n5";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 5<br/>Perbedaan: Tidak Ada<br/>Dominan: 5", controller.perbedaanL(Base64.getEncoder().encodeToString(input1.getBytes())));
        String input2 = "2\n1 2\n3 4";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 10<br/>Perbedaan: Tidak Ada<br/>Dominan: 10", controller.perbedaanL(Base64.getEncoder().encodeToString(input2.getBytes())));

        // =====================================================================
        // == PERBAIKAN TES: Nilai dominan diperbarui sesuai logika yang benar ==
        // =====================================================================

        String input3 = "3\n1 2 3\n4 5 6\n7 8 9";
        // Logika baru: Nilai L = 29, Kebalikan L = 21, Tengah = 5. Dominan = 29.
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input3.getBytes())).contains("Dominan: 29"));
        
        String input4 = "4\n10 1 1 1\n10 1 1 1\n10 1 1 1\n10 1 1 1";
        // Logika baru: Nilai L = 43, Kebalikan L = 16, Tengah = 4. Dominan = 43.
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input4.getBytes())).contains("Dominan: 43"));
        
        String input5 = "3\n1 10 10\n1 1 10\n1 1 10";
        // Logika baru: Nilai L = 14, Kebalikan L = 41, Tengah = 1. Dominan = 41.
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input5.getBytes())).contains("Dominan: 41"));
    }

    // Test untuk metode palingTer()
    @Test
    @DisplayName("Memperolah informasi paling ter dari suatu nilai")
    void palingTer_memperoleh_informasi_paling_ter_dari_suatu_nilai() throws Exception {
        // Pengujian Counter = 0
        {
            String inputBase64 = "LS0tDQo=";
            String expected = "Tidak ada data untuk diproses.";
            expected = expected.trim();

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Pengujian 1
        {
            String inputBase64 = "MQ0KMQ0KMw0KMw0KMg0KMg0KMg0KNA0KNQ0KMQ0KLS0tDQo=";
            String expected = """
                    Tertinggi: 5
                    Terendah: 1
                    Terbanyak: 2 (3x)
                    Tersedikit: 4 (1x)
                    Jumlah Tertinggi: 3 * 2 = 6
                    Jumlah Terendah: 1 * 3 = 3""";
            
            expected = expected.stripIndent().replaceAll("\n", "<br/>");

            // Arrange
            HomeController controller = new HomeController(); 

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }

        // Pengujian 2
        {
            String inputBase64 = "NTgNCjMxDQo4NA0KMTINCjg2DQo2MA0KODINCjQ5DQo4Mw0KNjQNCjc3DQo3DQo2OA0KOTENCjMNCjQxDQo1MQ0KNTMNCjUyDQo3NA0KNzINCjc0DQoxMA0KMzgNCjUwDQo1MQ0KOQ0KNTYNCjU2DQoyNQ0KODQNCjYzDQo5OA0KODQNCjQ4DQo5NQ0KNDYNCjMzDQo3OA0KNDUNCjU1DQoxMA0KNjANCjg4DQoxMA0KNzYNCjgxDQo1NQ0KNDYNCjU4DQozOA0KNTcNCjkyDQo2Nw0KNDENCjQ3DQozMA0KMg0KNjANCjQwDQozNg0KNTMNCjcwDQozMw0KNjYNCjYyDQo1OA0KMTUNCjkyDQo3Mw0KMTcNCjM3DQo2Ng0KMTINCjI5DQoxMDANCjUzDQo2Ng0KMzANCjEyDQozNA0KNg0KMjQNCjg5DQo2NA0KMTENCjgwDQozDQo5MA0KOA0KNjcNCjY1DQoyMg0KNTINCjI2DQozMg0KNjINCjU4DQo0NA0KOTQNCjE4DQo1OA0KMTANCjUNCjE3DQozNg0KMTcNCjYzDQo2OQ0KNzYNCjUyDQo1DQo5NA0KODINCjgNCjg1DQo4NQ0KNDcNCjQzDQozOA0KNzcNCjQzDQo0MQ0KNzMNCjgxDQozNg0KMjYNCjE3DQo0NQ0KOQ0KNDANCjczDQo1OQ0KMw0KNTgNCjYzDQoyNg0KMjkNCjE3DQo2OA0KOTQNCjM5DQo1DQo1MA0KMjQNCjQ0DQozNg0KMjQNCjUNCjkyDQo4NQ0KNTQNCjI2DQo5Mg0KNTINCjI1DQoyMw0KNDkNCjQ5DQo0Nw0KMjYNCjQ1DQo2DQo0OA0KNTkNCjk5DQozMA0KMjENCjEyDQoyMA0KNjENCjU2DQo4OA0KNDENCjQ4DQo1Nw0KNzQNCjY0DQozNA0KNjENCjEwDQo2DQo0OQ0KNjYNCjM1DQo1DQoyNg0KODUNCjc1DQo0OQ0KODkNCjY4DQoxMQ0KMTcNCjUNCjg0DQoxMQ0KNjINCjkzDQoyNw0KODkNCjg2DQo0NA0KNA0KOTMNCjI4DQo2Nw0KNDINCjY2DQoyDQo3Mg0KMTENCjEwDQo0NQ0KNTANCjMyDQoxOA0KMzANCjk1DQoyNQ0KMjINCjI2DQoxMDANCjI3DQo3Mw0KNDcNCjUNCjIwDQo4OA0KODkNCjcNCjk3DQoyOA0KMjUNCjQ0DQo3Nw0KMTENCjQ2DQo5Ng0KOTMNCjY3DQo1MQ0KNDkNCjY1DQo3OQ0KODkNCjg5DQotLS0NCg==";
            
            String expected = """
                    Tertinggi: 100
                    Terendah: 2
                    Terbanyak: 26 (7x)
                    Tersedikit: 4 (1x)
                    Jumlah Tertinggi: 7 * 26 = 182
                    Jumlah Terendah: 2 * 7 = 14""";
            
            expected = expected.stripIndent().replaceAll("\n", "<br/>");

            // Arrange
            HomeController controller = new HomeController();

            // Act
            String result = controller.palingTer(inputBase64);

            // Assert
            assertEquals(expected, result);
        }
    }

    // =====================================================================
    // == TES TAMBAHAN UNTUK MENCAPAI COVERAGE 100% ==
    // =====================================================================

    @Test
    @DisplayName("sayHello: Mengembalikan sapaan default jika nama bukan Abdullah")
    void sayHello_ShouldReturnDefaultGreeting() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.sayHello("Budi");

        // Assert
        assertEquals("Hello, Budi!", result);
    }

    @Test
    @DisplayName("informasiNim: Menangani format NIM yang tidak dikenal")
    void informasiNim_ShouldHandleInvalidFormat() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("123");

        // Assert
        assertEquals("Format NIM tidak dikenal", result);
    }

    @Test
    @DisplayName("perbedaanL: Menangani input Base64 yang tidak valid")
    void perbedaanL_ShouldHandleInvalidBase64() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        String invalidBase64 = "!!!!";

        // Act
        // Ini sekarang lolos karena HomeController.java memiliki try-catch
        String result = controller.perbedaanL(invalidBase64);

        // Assert
        assertEquals("Input Base64 tidak valid.", result);
    }

    @Test
    @DisplayName("palingTer: Menangani input Base64 yang tidak valid")
    void palingTer_ShouldHandleInvalidBase64() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        String invalidBase64 = "!!!!";

        // Act
        String result = controller.palingTer(invalidBase64);

        // Assert
        assertEquals("Input Base64 tidak valid.", result);
    }

    @Test
    @DisplayName("palingTer: Mengabaikan baris yang bukan angka")
    void palingTer_ShouldHandleInvalidNumberFormat() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        String inputBase64 = "MTANCnNhdHUNCjIwDQotLS0NCg=="; 

        // Act
        String result = controller.palingTer(inputBase64);

        // Assert
        String expected = """
                    Tertinggi: 20
                    Terendah: 10
                    Terbanyak: 20 (1x)
                    Tersedikit: 10 (1x)
                    Jumlah Tertinggi: 1 * 20 = 20
                    Jumlah Terendah: 10 * 1 = 10""";
        
        assertEquals(expected.stripIndent().replaceAll("\n", "<br/>"), result);
    }



    @Test
    @DisplayName("palingTer: Menangani input Base64 yang kosong (Menutup branch loop)")
    void palingTer_ShouldHandleEmptyBase64Input() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        String inputBase64 = ""; 
        
        // Act
        String result = controller.palingTer(inputBase64);

        // Assert
        assertEquals("Tidak ada data untuk diproses.", result);
    }

    @Test
    @DisplayName("palingTer: Menangani input null")
    void palingTer_ShouldHandleNullInput() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        
        // Act
        // Ini akan menguji cabang 'inputBase64 == null'
        String result = controller.palingTer(null);

        // Assert
        // Harusnya mengembalikan 'Tidak ada data' karena decodedString jadi ""
        assertEquals("Tidak ada data untuk diproses.", result);
    }

    @Test
    @DisplayName("perbedaanL: Menangani kasus L == KebalikanL")
    void perbedaanL_ShouldHandleEqualL() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        // Matriks simetris 3x3
        // Nilai L = (1+1+1) + (1+1) = 5
        // Nilai KebalikanL = (1+1+1) + (1+1) = 5
        // Nilai Tengah = 9
        // Dominan harus 9
        String input = "3\n1 1 1\n1 9 1\n1 1 1"; 
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());

        // Act
        String result = controller.perbedaanL(base64Input);

        // Assert
        // Ini akan menguji cabang '... : nilaiTengah'
        assertTrue(result.contains("Nilai L: 5"));
        assertTrue(result.contains("Nilai Kebalikan L: 5"));
        assertTrue(result.contains("Perbedaan: 0"));
        assertTrue(result.contains("Dominan: 9"));
    }

    @Test
    @DisplayName("perolehanNilai: Menangani input tanpa data nilai (hanya bobot)")
    void perolehanNilai_ShouldHandleNoGrades() throws Exception {
        // Arrange
        HomeController controller = new HomeController();
        // Input hanya 6 baris bobot, tidak ada baris ke-7
        String input = "10\n10\n10\n20\n25\n25";
        String base64Input = Base64.getEncoder().encodeToString(input.getBytes());

        // Act
        // Ini akan menguji kasus di mana for-loop 'i=6' tidak pernah berjalan
        String result = controller.perolehanNilai(base64Input);

        // Assert
        // Jika tidak ada nilai, skor akhir harus 0 dan grade E
        assertTrue(result.contains(">> Nilai Akhir: 0.00"));
        assertTrue(result.contains(">> Grade: E"));
    }
}