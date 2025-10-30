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
        // Arrange
        HomeController controller = new HomeController();
        String nim = "11S21001";
        String expected = "Informasi NIM 11S21001:<br/>" +
                          ">> Program Studi: Sarjana Informatika<br/>" +
                          ">> Angkatan: 2021<br/>" +
                          ">> Urutan: 1";
        // Act
        String result = controller.informasiNim(nim);
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
    @DisplayName("Test Perolehan Nilai - Should calculate final score and grade")
    void perolehanNilai_ShouldCalculateFinalScore() {
        // Arrange
        HomeController controller = new HomeController();
        String plainInput = "10 10 10 20 25 25\n" +
                            "T|100|80\n" +
                            "UTS|100|75\n" +
                            "UAS|100|90\n" +
                            "---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Perolehan Nilai:<br/>" +
                          ">> Partisipatif: 0/100 (0.00/10)<br/>" +
                          ">> Tugas: 80/100 (8.00/10)<br/>" +
                          ">> Kuis: 0/100 (0.00/10)<br/>" +
                          ">> Proyek: 0/100 (0.00/20)<br/>" +
                          ">> UTS: 75/100 (18.75/25)<br/>" +
                          ">> UAS: 90/100 (22.50/25)<br/>" +
                          "<br/>" +
                          ">> Nilai Akhir: 49.25<br/>" +
                          ">> Grade: D";

        // Act
        String result = controller.perolehanNilai(base64Input);
        
        // Assert
        assertEquals(expected, result);
    }
    
    @Test
    @DisplayName("Test Perbedaan L - Should calculate L differences for N=3")
    void perbedaanL_ShouldCalculateDifferences() {
        // Arrange
        HomeController controller = new HomeController();
        String plainInput = "3\n" +
                            "1 2 3\n" +
                            "4 5 6\n" +
                            "7 8 9";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Nilai L: 20<br/>" +
                          "Nilai Kebalikan L: 20<br/>" +
                          "Nilai Tengah: 5<br/>" +
                          "Perbedaan: 0<br/>" +
                          "Dominan: 5";

        // Act
        String result = controller.perbedaanL(base64Input);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test Perbedaan L - Should handle N=1 case")
    void perbedaanL_ShouldHandleN1() {
        HomeController controller = new HomeController();
        String plainInput = "1\n5";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Nilai L: Tidak Ada<br/>" +
                          "Nilai Kebalikan L: Tidak Ada<br/>" +
                          "Nilai Tengah: 5<br/>" +
                          "Perbedaan: Tidak Ada<br/>" +
                          "Dominan: 5";
        assertEquals(expected, controller.perbedaanL(base64Input));
    }

    @Test
    @DisplayName("Test Perbedaan L - Should handle N=2 case")
    void perbedaanL_ShouldHandleN2() {
        HomeController controller = new HomeController();
        String plainInput = "2\n1 2\n3 4";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Nilai L: Tidak Ada<br/>" +
                          "Nilai Kebalikan L: Tidak Ada<br/>" +
                          "Nilai Tengah: 10<br/>" +
                          "Perbedaan: Tidak Ada<br/>" +
                          "Dominan: 10";
        assertEquals(expected, controller.perbedaanL(base64Input));
    }

    @Test
    @DisplayName("Test Paling Ter - Should find all 'Ter' values")
    void palingTer_ShouldFindAllTerValues() {
        // Arrange
        HomeController controller = new HomeController(); // <-- INI YANG DIPERBAIKI
        String plainInput = "7\n" +
                            "8\n" +
                            "7\n" +
                            "9\n" +
                            "8\n" +
                            "10\n" +
                            "---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Tertinggi: 10<br/>" +
                          "Terendah: 7<br/>" +
                          "Terbanyak: 7 (2x)<br/>" +
                          "Tersedikit: 9 (1x)<br/>" +
                          "Jumlah Tertinggi: 8 * 2 = 16<br/>" +
                          "Jumlah Terendah: 9 * 1 = 9";

        // Act
        String result = controller.palingTer(base64Input);
        
        // Assert
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Test Paling Ter - Should handle empty input")
    void palingTer_ShouldHandleEmptyInput() {
        HomeController controller = new HomeController();
        String plainInput = "---";
        String base64Input = Base64.getEncoder().encodeToString(plainInput.getBytes());
        String expected = "Tidak ada data untuk diproses.";
        assertEquals(expected, controller.palingTer(base64Input));
    }
}