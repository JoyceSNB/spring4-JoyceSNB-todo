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
        // Arrange
        HomeController controller = new HomeController();
        // Act
        String result = controller.hello();
        // Assert
        assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    // Tambahan test untuk metode sayHello dengan parameter nama
    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() {
        // Arrange
        HomeController controller = new HomeController();
        // Act
        String result = controller.sayHello("Abdullah");
        // Assert
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
    @DisplayName("Test Perolehan Nilai - Should cover all grades and default switch case")
    void perolehanNilai_ShouldCoverAllGradesAndCases() {
        HomeController controller = new HomeController();
        String weights = "10 10 10 20 25 25\n";

        String inputA = weights + "PA|100|80\nT|100|80\nK|100|80\nP|100|80\nUTS|100|80\nUAS|100|80\n---";
        String inputAB = weights + "PA|100|75\nT|100|75\nK|100|75\nP|100|75\nUTS|100|75\nUAS|100|75\n---";
        String inputB = weights + "PA|100|70\nT|100|70\nK|100|70\nP|100|70\nUTS|100|70\nUAS|100|70\n---";
        String inputBC = weights + "PA|100|60\nT|100|60\nK|100|60\nP|100|60\nUTS|100|60\nUAS|100|60\n---";
        String inputC = weights + "PA|100|50\nT|100|50\nK|100|50\nP|100|50\nUTS|100|50\nUAS|100|50\n---";
        String inputD = weights + "PA|100|40\nT|100|40\nK|100|40\nP|100|40\nUTS|100|40\nUAS|100|40\n---";
        String inputE = weights + "PA|100|20\nT|100|20\nK|100|20\nP|100|20\nUTS|100|20\nUAS|100|20\n---";
        // TES BARU: Menguji default case pada switch dengan kategori yang tidak valid
        String inputInvalidCategory = weights + "INVALID|100|100\n---"; 

        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputA.getBytes())).contains(">> Grade: A"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputAB.getBytes())).contains(">> Grade: AB"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputB.getBytes())).contains(">> Grade: B"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputBC.getBytes())).contains(">> Grade: BC"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputC.getBytes())).contains(">> Grade: C"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputD.getBytes())).contains(">> Grade: D"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputE.getBytes())).contains(">> Grade: E"));
        // Memastikan kode berjalan normal meskipun ada kategori invalid (default case ter-cover)
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputInvalidCategory.getBytes())).contains(">> Grade: E"));
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
        String input5 = "3\n1 10 10\n1 1 10\n1 1 10"; // NilaiL=4, NilaiKebalikanL=40
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

    // TES BARU: Mencakup kondisi else if (jumlah == ...)
    @Test
    @DisplayName("Test Paling Ter - Should handle same total sum conditions")
    void palingTer_ShouldHandleSameTotalSum() {
        HomeController controller = new HomeController();
        // Kasus: 6*2=12 dan 4*3=12. Tertinggi harus 6 (karena 6 > 4).
        // Kasus: 2*3=6 dan 3*2=6. Terendah harus 2 (karena 2 < 3).
        String plainInput = "6\n6\n4\n4\n4\n2\n2\n2\n3\n3\n---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String result = controller.palingTer(base64Input);
        
        assertTrue(result.contains("Jumlah Tertinggi: 6 * 2 = 12"));
        assertTrue(result.contains("Jumlah Terendah: 2 * 3 = 6"));
    }
}