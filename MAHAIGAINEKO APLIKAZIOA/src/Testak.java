import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class Testak {

    // --- PRODUKTUA TESTAK ---
    @Test
    void testProduktuaSortu() {
        Produktua p = new Produktua("1234567890123", "Test Kamiseta", "Arropa");
        assertEquals("1234567890123", p.getEan13());
        assertEquals("Test Kamiseta", p.getIzena());
        assertEquals("Arropa", p.getKategoria());
    }

    // --- GELAXKA STOCK TESTAK ---
    @Test
    void testGelaxkaStockSortu() {
        GelaxkaStock g = new GelaxkaStock("A1-1", "1234567890123", 50);
        assertEquals("A1-1", g.getGelaxkaID());
        assertEquals(50, g.getKantitatea());

        g.setKantitatea(20);
        assertEquals(20, g.getKantitatea());
    }

    // --- BILTEGIA TESTAK ---
    @Test
    void testBiltegiaProzesuOsoa() {
        Biltegia b = new Biltegia();

        // 1. SARTU
        // A1-1 gelaxkan (hasieran 15 ditu) 10 gehiago sartu
        boolean sartuDa = b.sartuStocka("5449000000100", "A1-1", 10);
        assertTrue(sartuDa, "Stocka sartuak true eman behar du");

        // Egiaztatu kantitatea (15 + 10 = 25)
        int kantitatea = b.kontsultatuGelaxka("A1-1").get(0).getKantitatea();
        assertEquals(25, kantitatea);

        // 2. ATERA
        // 5 unitate atera
        boolean ateraDa = b.ateraStocka("5449000000100", "A1-1", 5);
        assertTrue(ateraDa, "Stocka ateratzeak true eman behar du");

        // Egiaztatu kantitatea (25 - 5 = 20)
        kantitatea = b.kontsultatuGelaxka("A1-1").get(0).getKantitatea();
        assertEquals(20, kantitatea);
    }

    @Test
    void testBiltegiaErroreak() {
        Biltegia b = new Biltegia();

        // EAN okerra
        assertFalse(b.sartuStocka("EAN_FALTSUA", "A1-1", 10));

        // Kantitate negatiboa
        assertFalse(b.sartuStocka("5449000000100", "A1-1", -5));

        // Kapazitatea gaindituta (Max 100)
        // A1-1ek 15 ditu -> 90 gehitzen saiatu -> 105 (Errorea)
        assertFalse(b.sartuStocka("5449000000100", "A1-1", 90));
    }

    @Test
    void testMugituProduktua() {
        Biltegia b = new Biltegia();

        // A1-1etik (15 unitate) -> A1-2ra 5 unitate mugitu
        boolean mugituDa = b.mugituProduktua("5449000000100", "A1-1", "A1-2", 5);
        assertTrue(mugituDa);

        // A1-1en 10 geratu behar dira
        assertEquals(10, b.kontsultatuGelaxka("A1-1").get(0).getKantitatea());
    }

    // --- BESTE TEST BATZUK ---

    @Test
    void testHustuGelaxka() {
        Biltegia b = new Biltegia();
        // A1-1ean 15 daude. Dena atera.
        boolean ateraDa = b.ateraStocka("5449000000100", "A1-1", 15);
        assertTrue(ateraDa);

        // Orain hutsik egon beharko luke
        // (Berriro 1 unitate ateratzen saiatuz gero false eman beharko luke ez
        // dagoelako)
        boolean berriro = b.ateraStocka("5449000000100", "A1-1", 1);
        assertFalse(berriro, "Gelaxka jada hutsik egon beharko luke");
    }

    @Test
    void testBalidazioZuzenak() {
        Biltegia b = new Biltegia();
        // EAN zuzena
        assertTrue(b.balidatuEAN13("5449000000100"));
        // Gelaxka ID zuzena
        assertTrue(b.balidatuGelaxkaID("B2-4"));
        // Kantitate zuzena
        assertTrue(b.balidatuKantitatea(50));
    }
}
