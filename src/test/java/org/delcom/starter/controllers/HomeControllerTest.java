package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

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
    @DisplayName("Menolak NIM yang kurang dari 8 karakter")
    void informasiNim_ShouldRejectShortNim() {
        HomeController controller = new HomeController();
        String result = controller.informasiNim("123");
        assertTrue(result.contains("NIM harus"));
    }

    @Test
    @DisplayName("Mengembalikan hasil informasi NIM yang valid")
    void informasiNim_ShouldReturnValidInfo() {
        HomeController controller = new HomeController();
        String result = controller.informasiNim("11S23001");
        assertTrue(result.contains("Sarjana Informatika"));
    }

    @Test
    @DisplayName("Mengembalikan hasil informasi NIM tidak dikenal (default case)")
    void informasiNim_ShouldHandleUnknownPrefix() {
        HomeController controller = new HomeController();
        String result = controller.informasiNim("99X23001");
        assertTrue(result.contains("Tidak dikenal"));
    }

    @Test
    @DisplayName("Perolehan nilai menghasilkan Grade A")
    void perolehanNilai_ShouldReturnGradeA() {
        HomeController controller = new HomeController();
        String raw = String.join("\n",
                "10 10 10 20 25 25",
                "PA|100|100",
                "T|100|100",
                "K|100|100",
                "P|100|100",
                "UTS|100|100",
                "UAS|100|100",
                "---"
        );
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.perolehanNilai(encoded);
        assertTrue(result.contains("Grade"));
        assertTrue(result.contains("A"));
    }

    @Test
    @DisplayName("Perolehan nilai menghasilkan Grade E (nilai rendah)")
    void perolehanNilai_ShouldReturnGradeE() {
        HomeController controller = new HomeController();
        String raw = String.join("\n",
                "10 10 10 20 25 25",
                "PA|100|10",
                "T|100|10",
                "K|100|10",
                "P|100|10",
                "UTS|100|10",
                "UAS|100|10",
                "---"
        );
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.perolehanNilai(encoded);
        assertTrue(result.contains("Grade"));
        assertTrue(result.contains("E"));
    }

    @Test
    @DisplayName("PerbedaanL - Kasus matriks 1x1")
    void perbedaanL_ShouldHandle1x1() {
        HomeController controller = new HomeController();
        String raw = "1\n5";
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Nilai Tengah"));
    }

    @Test
    @DisplayName("PerbedaanL - Kasus matriks 2x2")
    void perbedaanL_ShouldHandle2x2() {
        HomeController controller = new HomeController();
        String raw = String.join("\n",
                "2",
                "1 2",
                "3 4"
        );
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Nilai Tengah"));
    }

    @Test
    @DisplayName("PerbedaanL - Kasus error decoding")
    void perbedaanL_ShouldHandleError() {
        HomeController controller = new HomeController();
        String result = controller.perbedaanL("ini_bukan_base64_valid");
        assertTrue(result.toLowerCase().contains("kesalahan"));
    }

    @Test
    @DisplayName("PerbedaanL - Matriks 3x3 normal")
    void perbedaanL_ShouldReturnDifference() {
        HomeController controller = new HomeController();
        String raw = String.join("\n",
                "3",
                "1 2 3",
                "4 5 6",
                "7 8 9"
        );
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.perbedaanL(encoded);
        assertTrue(result.contains("Perbedaan"));
    }

    @Test
    @DisplayName("palingTer - Data CSV base64")
    void palingTer_ShouldReturnSummary() {
        HomeController controller = new HomeController();
        String raw = "80,90,75,75,100,90";
        String encoded = java.util.Base64.getEncoder().encodeToString(raw.getBytes());
        String result = controller.palingTer(encoded);
        assertNotNull(result);
        assertTrue(result.contains("Tertinggi") && result.contains("Terendah"));
    }
}