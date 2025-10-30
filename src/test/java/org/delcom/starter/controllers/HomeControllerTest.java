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
    @DisplayName("Test Perolehan Nilai - Should cover all grades")
    void perolehanNilai_ShouldCoverAllGrades() {
        HomeController controller = new HomeController();
        String weights = "10 10 10 20 25 25\n";

        String inputA = weights + "PA|100|80\nT|100|80\nK|100|80\nP|100|80\nUTS|100|80\nUAS|100|80\n---"; // Skor: 80.0 -> A
        String inputAB = weights + "PA|100|75\nT|100|75\nK|100|75\nP|100|75\nUTS|100|75\nUAS|100|75\n---"; // Skor: 75.0 -> AB
        String inputB = weights + "PA|100|70\nT|100|70\nK|100|70\nP|100|70\nUTS|100|70\nUAS|100|70\n---"; // Skor: 70.0 -> B
        String inputBC = weights + "PA|100|60\nT|100|60\nK|100|60\nP|100|60\nUTS|100|60\nUAS|100|60\n---"; // Skor: 60.0 -> BC
        String inputC = weights + "PA|100|50\nT|100|50\nK|100|50\nP|100|50\nUTS|100|50\nUAS|100|50\n---"; // Skor: 50.0 -> C
        String inputD = weights + "PA|100|40\nT|100|40\nK|100|40\nP|100|40\nUTS|100|40\nUAS|100|40\n---"; // Skor: 40.0 -> D
        String inputE = weights + "PA|100|20\nT|100|20\nK|100|20\nP|100|20\nUTS|100|20\nUAS|100|20\n---"; // Skor: 20.0 -> E

        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputA.getBytes())).contains(">> Grade: A"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputAB.getBytes())).contains(">> Grade: AB"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputB.getBytes())).contains(">> Grade: B"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputBC.getBytes())).contains(">> Grade: BC"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputC.getBytes())).contains(">> Grade: C"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputD.getBytes())).contains(">> Grade: D"));
        assertTrue(controller.perolehanNilai(Base64.getEncoder().encodeToString(inputE.getBytes())).contains(">> Grade: E"));
    }
    
    @Test
    @DisplayName("Test Perbedaan L - Should cover all N sizes")
    void perbedaanL_ShouldCoverAllNSizes() {
        HomeController controller = new HomeController();
        String input1 = "1\n5";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 5<br/>Perbedaan: Tidak Ada<br/>Dominan: 5", controller.perbedaanL(Base64.getEncoder().encodeToString(input1.getBytes())));
        String input2 = "2\n1 2\n3 4";
        assertEquals("Nilai L: Tidak Ada<br/>Nilai Kebalikan L: Tidak Ada<br/>Nilai Tengah: 10<br/>Perbedaan: Tidak Ada<br/>Dominan: 10", controller.perbedaanL(Base64.getEncoder().encodeToString(input2.getBytes())));
        String input3 = "3\n1 2 3\n4 5 6\n7 8 9";
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input3.getBytes())).contains("Dominan: 5"));
        String input4 = "4\n10 1 1 1\n10 1 1 1\n10 1 1 1\n10 1 1 1";
        assertTrue(controller.perbedaanL(Base64.getEncoder().encodeToString(input4.getBytes())).contains("Dominan: 42"));
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

    @Test
    @DisplayName("Test Paling Ter - Should handle same total sum conditions")
    void palingTer_ShouldHandleSameTotalSum() {
        HomeController controller = new HomeController();
        String plainInput = "6\n6\n4\n4\n4\n2\n2\n2\n3\n3\n---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String result = controller.palingTer(base64Input);
        
        assertTrue(result.contains("Jumlah Tertinggi: 6 * 2 = 12"));
        assertTrue(result.contains("Jumlah Terendah: 2 * 3 = 6"));
    }
}