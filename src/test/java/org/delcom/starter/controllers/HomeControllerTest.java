package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerUnitTest {
    // Test untuk metode hello()
    @Test
    @DisplayName("Mengembalikan pesan selamat datang yang benar")
    void hello_ShouldReturnWelcomeMessage() {
        HomeController controller = new HomeController();
        String result = controller.hello();
        assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    // Tambahan test untuk metode sayHello dengan parameter nama
    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() {
        HomeController controller = new HomeController();
        String result = controller.sayHello("Abdullah");
        assertEquals("Hello, Abdullah!", result);
    }

    // ====================================================================
    // UNIT TEST UNTUK METODE-METODE MIGRASI
    // ====================================================================

    @Test
    @DisplayName("Test Informasi NIM - Should return correct NIM information")
    void informasiNim_ShouldReturnCorrectNimInfo() {
        HomeController controller = new HomeController();
        String nim = "11S21001";
        String expected = "Informasi NIM 11S21001:<br/>" +
                          ">> Program Studi: Sarjana Informatika<br/>" +
                          ">> Angkatan: 2021<br/>" +
                          ">> Urutan: 1";
        assertEquals(expected, controller.informasiNim(nim));
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
        // N=1
        String input1 = "1\n5";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 5<br/>Perbedaan: Tidak Ada<br/>Dominan: 5", controller.perbedaanL(Base64.getEncoder().encodeToString(input1.getBytes())));
        // N=2
        String input2 = "2\n1 2\n3 4";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 10<br/>Perbedaan: Tidak Ada<br/>Dominan: 10", controller.perbedaanL(Base64.getEncoder().encodeToString(input2.getBytes())));
        // N=3 (Dominan: Tengah)
        String input3 = "3\n1 2 3\n4 5 6\n7 8 9";
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input3.getBytes())).contains("Dominan: 5"));
        // N=4 (Dominan: L)
        String input4 = "4\n10 1 1 1\n10 1 1 1\n10 1 1 1\n10 1 1 1";
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input4.getBytes())).contains("Dominan: 42"));
        // N=3 (Dominan: Kebalikan L)
        String input5 = "3\n1 10 10\n1 1 10\n1 1 10";
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input5.getBytes())).contains("Dominan: 40"));
    }

    @Test
    @DisplayName("Test Paling Ter - Should find all 'Ter' values")
    void palingTer_ShouldFindAllTerValues() {
        HomeController controller = new HomeController();
        String plainInput = "7\n8\n7\n9\n8\n10\n---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Tertinggi: 10<br/>Terendah: 7<br/>Terbanyak: 7 (2x)<br/>Tersedikit: 9 (1x)<br/>Jumlah Tertinggi: 8 * 2 = 16<br/>Jumlah Terendah: 9 * 1 = 9";
        assertEquals(expected, controller.palingTer(base64Input));
    }

    @Test
    @DisplayName("Test Paling Ter - Should handle empty input")
    void palingTer_ShouldHandleEmptyInput() {
        HomeController controller = new HomeController();
        String plainInput = "---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        assertEquals("Tidak ada data untuk diproses.", controller.palingTer(base64Input));
    }

    // TES BARU: Mencakup kondisi else if (jumlah == ...) yang berwarna kuning dan merah
    @Test
    @DisplayName("Test Paling Ter - Should handle same total sum conditions")
    void palingTer_ShouldHandleSameTotalSum() {
        HomeController controller = new HomeController();
        // Kasus "seri" untuk jumlah tertinggi dan terendah
        String plainInput = "6\n6\n4\n4\n4\n3\n3\n2\n2\n2\n---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String result = controller.palingTer(base64Input);
        
        assertTrue(result.contains("Jumlah Tertinggi: 6 * 2 = 12"));
        assertTrue(result.contains("Jumlah Terendah: 2 * 3 = 6"));
    }
}