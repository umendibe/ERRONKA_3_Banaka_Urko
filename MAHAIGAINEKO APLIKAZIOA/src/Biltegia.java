import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Biltegiaren kudeaketa logikoa egiten duen klase nagusia.
 * <p>
 * Klase honek produktuak sartzea, ateratzea eta mugitzea ahalbidetzen du,
 * baita biltegiaren arauak (kapazitate maximoa, balidazioak) betearaztea ere.
 * </p>
 */
public class Biltegia {
    private List<Produktua> produktuKatalogoa;

    /**
     * Biltegian dauden gelaxken stock zerrenda. Protected da testetarako sarbidea
     * izateko.
     */
    protected List<GelaxkaStock> stocka;

    // Gelaxka beteak (100 unitate) zerrenda honetan gordetzen dira
    private List<String> betetakoGelaxkak;

    /**
     * Biltegia hasieratzen du.
     * Zerrendak sortu eta hasierako proba-datuak kargatzen ditu.
     */
    public Biltegia() {
        produktuKatalogoa = new ArrayList<>();
        stocka = new ArrayList<>();
        betetakoGelaxkak = new ArrayList<>();
        kargatuDatuak();
    }

    /**
     * Proba egiteko hasierako datuak kargatzen ditu sisteman.
     * (Katalogoa eta hasierako stocka).
     */
    private void kargatuDatuak() {
        produktuKatalogoa.add(new Produktua("5449000000100", "Kamiseta Zubieta", "Kamisetak"));
        produktuKatalogoa.add(new Produktua("8437008888001", "Kirol Jertse Gorria", "Kirol-jertseak"));
        produktuKatalogoa.add(new Produktua("1112223334445", "Galtza Bakeroak Unisex", "Galtzak"));

        stocka.add(new GelaxkaStock("A1-1", "5449000000100", 15));
        stocka.add(new GelaxkaStock("A1-2", "1112223334445", 15));
        stocka.add(new GelaxkaStock("B2-4", "8437008888001", 15));
    }

    /**
     * Stock berria sartzen du gelaxka batean.
     * <p>
     * Balidazioak egiten ditu: datu formatuak, gelaxkaren kapazitatea (max 100),
     * eta gelaxka 'beteta' zerrendan ez dagoela egiaztatzen du.
     * </p>
     *
     * @param ean13      Produktuaren EAN kodea.
     * @param gelaxkaID  Helmugako gelaxkaren IDa.
     * @param kantitatea Sartu beharreko kopurua (positiboa izan behar du).
     * @return {@code true} sarrera arrakastaz egin bada; {@code false} erroreren
     *         bat badago.
     */
    public boolean sartuStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (!balidatuEAN13(ean13) || !balidatuGelaxkaID(gelaxkaID) || !balidatuKantitatea(kantitatea))
            return false;

        int gelaxkaKapazitatea = kalkulatuGelaxkaKapazitatea(gelaxkaID);

        if (gelaxkaKapazitatea + kantitatea > 100) {
            System.out.println("Errorea: Gelaxka " + gelaxkaID + " -en kapazitate limitea gainditu daiteke.");
            System.out.println("Unean: " + gelaxkaKapazitatea + " produktu, gehitu nahi: " + kantitatea);
            System.out.println("Gehienez: " + (100 - gelaxkaKapazitatea) + " produktu sartzen dira.");
            return false;
        }

        if (betetakoGelaxkak.contains(gelaxkaID)) {
            System.out.println("Errorea: Gelaxka 'beteta' gisa markatuta dago. Ezin da sartu.");
            return false;
        }

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                item.setKantitatea(item.getKantitatea() + kantitatea);
                if (item.getKantitatea() >= 100) {
                    betetakoGelaxkak.add(gelaxkaID);
                    System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' gisa markatuta dago.");
                }
                return true;
            }
        }

        stocka.add(new GelaxkaStock(gelaxkaID, ean13, kantitatea));
        if (gelaxkaKapazitatea + kantitatea >= 100) {
            betetakoGelaxkak.add(gelaxkaID);
            System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' gisa markatuta dago.");
        }
        return true;
    }

    /**
     * Stocka ateratzen du gelaxka batetik.
     * <p>
     * Stock nahikoa dagoela egiaztatzen du. Gelaxka hutsik geratzen bada,
     * stock zerrendatik ezabatzen du. 'Beteta' egoeran bazegoen, egoera horretatik
     * ateratzen da.
     * </p>
     *
     * @param ean13      Produktuaren EAN kodea.
     * @param gelaxkaID  Jatorrizko gelaxka.
     * @param kantitatea Atera beharreko kopurua.
     * @return {@code true} irteera arrakastaz egin bada; {@code false} nahikoa
     *         stock ez badago edo produktua ez badago.
     */
    public boolean ateraStocka(String ean13, String gelaxkaID, int kantitatea) {
        if (kantitatea <= 0)
            return false;

        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                if (item.getKantitatea() >= kantitatea) {
                    item.setKantitatea(item.getKantitatea() - kantitatea);
                    if (item.getKantitatea() < 100 && betetakoGelaxkak.contains(gelaxkaID)) {
                        betetakoGelaxkak.remove(gelaxkaID);
                        if (item.getKantitatea() == 0) {
                            System.out.println("Abisua: Gelaxka " + gelaxkaID + " hutsik dago.");
                            garbituGelaxkaHutsa(gelaxkaID);
                            return true;
                        }
                        System.out.println("Abisua: Gelaxka " + gelaxkaID + " 'beteta' egoeratik atera da.");
                    }
                    return true;
                } else {
                    System.out.println("Errorea: Ez dago nahikoa stock gelaxka horretan.");
                    return false;
                }
            }
        }
        System.out.println("Errorea: Produktu hori ez da aurkitu gelaxka horretan.");
        return false;
    }

    /**
     * Produktu kantitate bat gelaxka batetik bestera mugitzen du.
     * <p>
     * Transakzio atomiko bat simulatzen du: lehenik atera egiten da eta ondoren
     * sartu.
     * Sarrerak huts egiten badu (adib. kapazitateagatik), irteera desegin egiten da
     * (rollback).
     * </p>
     *
     * @param ean13               Produktuaren EAN kodea.
     * @param jatorrizkoGelaxkaID Jatorrizko gelaxkaren IDa.
     * @param helmugaGelaxkaID    Helmugako gelaxkaren IDa.
     * @param kantitatea          Mugitu beharreko kopurua.
     * @return {@code true} mugimendua osatu bada, {@code false} bestela.
     */
    public boolean mugituProduktua(String ean13, String jatorrizkoGelaxkaID, String helmugaGelaxkaID, int kantitatea) {
        if (kantitatea <= 0)
            return false;

        int helmugaKapazitatea = kalkulatuGelaxkaKapazitatea(helmugaGelaxkaID);

        if (helmugaKapazitatea + kantitatea > 100) {
            System.out.println(
                    "Errorea: Helmugako gelaxka " + helmugaGelaxkaID + " -en kapazitate limitea gainditu daiteke.");
            System.out.println("Unean: " + helmugaKapazitatea + " produktu, gehitu nahi: " + kantitatea);
            System.out.println("Gehienez: " + (100 - helmugaKapazitatea) + " produktu mugitu daitezke.");
            return false;
        }

        boolean ateraDa = ateraStocka(ean13, jatorrizkoGelaxkaID, kantitatea);

        if (!ateraDa)
            return false;

        boolean sartuDa = sartuStocka(ean13, helmugaGelaxkaID, kantitatea);

        if (sartuDa) {
            return true;
        } else {
            System.err.println("Mugimendua ezin izan da osatu. Jatorrizko kokalekua berreskuratzen.");
            sartuStocka(ean13, jatorrizkoGelaxkaID, kantitatea);
            return false;
        }
    }

    /**
     * Gelaxka zehatz baten edukia kontsultatzen du.
     * * @param gelaxkaID Kontsultatu nahi den gelaxkaren IDa.
     * 
     * @return Gelaxka horretan dauden stock elementuen zerrenda.
     */
    public List<GelaxkaStock> kontsultatuGelaxka(String gelaxkaID) {
        List<GelaxkaStock> emaitzak = new ArrayList<>();
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID)) {
                emaitzak.add(item);
            }
        }
        return emaitzak;
    }

    /**
     * Biltegiaren inbentario osoa itzultzen du.
     * * @return Stock osoaren zerrenda (List&lt;GelaxkaStock&gt;).
     */
    public List<GelaxkaStock> kontsultatuInbentarioa() {
        return stocka;
    }

    // Metodo pribatuak (Internal helpers)

    private int kalkulatuGelaxkaKapazitatea(String gelaxkaID) {
        int kapazitatea = 0;
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID)) {
                kapazitatea += item.getKantitatea();
            }
        }
        return kapazitatea;
    }

    /**
     * Gelaxka guztietan dauden produktuak eta haien egoera (LIBRE/BETETA) erakusten
     * du kontsolan.
     */
    public void erakutsiProduktuguztiakGelaxketan() {
        System.out.println("\n========================================");
        System.out.println("PRODUKTU GUZTIAK GELAXKETAN");
        System.out.println("========================================");

        if (stocka.isEmpty()) {
            System.out.println("Biltegia hutsa dago. Ez da produkturik aurkitu.");
            return;
        }

        Map<String, List<GelaxkaStock>> gelaxkaMap = new LinkedHashMap<>();

        for (GelaxkaStock item : stocka) {
            gelaxkaMap.computeIfAbsent(item.getGelaxkaID(), k -> new ArrayList<>()).add(item);
        }

        int totalProduktua = 0;

        for (String gelaxkaID : gelaxkaMap.keySet()) {
            List<GelaxkaStock> gelaxkaProduktua = gelaxkaMap.get(gelaxkaID);
            int gelaxkaKapazitatea = gelaxkaProduktua.stream().mapToInt(GelaxkaStock::getKantitatea).sum();

            System.out.println("\n--- Gelaxka: " + gelaxkaID + " ---");
            System.out.println("Kapazitate Totala: " + gelaxkaKapazitatea + "/100");
            System.out.println("Egoera: " + (gelaxkaKapazitatea >= 100 ? "BETETA" : "LIBRE"));

            for (GelaxkaStock item : gelaxkaProduktua) {
                Produktua prod = bilatuProduktua(item.getProduktuEAN13());
                String produktuIzena = prod != null ? prod.getIzena() : "Ezezaguna";

                System.out.println("  - " + produktuIzena + " [EAN: " + item.getProduktuEAN13() + "]"
                        + " | Kantitatea: " + item.getKantitatea());
                totalProduktua += item.getKantitatea();
            }
        }

        System.out.println("\n========================================");
        System.out.println("TOTALA: " + totalProduktua + " produktu");
        System.out.println("========================================\n");
    }

    private Produktua bilatuProduktua(String ean13) {
        for (Produktua prod : produktuKatalogoa) {
            if (prod.getEan13().equals(ean13)) {
                return prod;
            }
        }
        return null;
    }

    private void garbituGelaxkaHutsa(String gelaxkaID) {
        stocka.removeIf(item -> item.getGelaxkaID().equals(gelaxkaID) && item.getKantitatea() == 0);
    }

    /**
     * EAN-13 kodea balioztatzen du.
     * <p>
     * Egiaztatzen du:
     * </p>
     * <ul>
     * <li>Ez dela nulua edo hutsa.</li>
     * <li>13 digitu zenbaki dituela.</li>
     * <li>Produktua katalogoan existitzen dela.</li>
     * </ul>
     * * @param ean13 Egiaztatu beharreko EAN kodea.
     * 
     * @return true kodea baliozkoa eta katalogoan existitzen bada.
     */
    public boolean balidatuEAN13(String ean13) {
        if (ean13 == null || ean13.trim().isEmpty()) {
            System.out.println("Errorea: EAN-13 kodea ezin da hutsik egon.");
            return false;
        }

        if (!ean13.matches("\\d{13}")) {
            System.out.println("Errorea: EAN-13 kodeak 13 digitu eduki behar ditu (zenbaki osoak).");
            return false;
        }

        if (bilatuProduktua(ean13) == null) {
            System.out.println("Errorea: EAN-13 kodea ez da katalogoan aurkitu.");
            System.out.println("Katalogoan dauden EAN-13 kodeak:");
            for (Produktua prod : produktuKatalogoa) {
                System.out.println("  - " + prod.getEan13() + ": " + prod.getIzena());
            }
            return false;
        }

        return true;
    }

    /**
     * Gelaxka IDaren formatua balioztatzen du.
     * Formatu zuzena: LetraLarria + Digituak + "-" + Digituak (Adib: A1-1).
     * * @param gelaxkaID Egiaztatu beharreko gelaxka IDa.
     * 
     * @return true formatua zuzena bada, false bestela.
     */
    public boolean balidatuGelaxkaID(String gelaxkaID) {
        if (gelaxkaID == null || gelaxkaID.trim().isEmpty()) {
            System.out.println("Errorea: Gelaxka IDa ezin da hutsik egon.");
            return false;
        }

        if (!gelaxkaID.matches("[A-Z]\\d+-\\d+")) {
            System.out.println("Errorea: Gelaxka IDaren formatua okerra da. Adibidez: A1-1, B2-4");
            return false;
        }

        return true;
    }

    /**
     * Kantitatea baliozkoa den egiaztatzen du (0 eta 1000 artean).
     * * @param kantitatea Egiaztatu beharreko kantitatea.
     * 
     * @return true baliozkoa bada (0 &lt; k &le; 1000).
     */
    public boolean balidatuKantitatea(int kantitatea) {
        if (kantitatea <= 0) {
            System.out.println("Errorea: Kantitatea 0 baino handiagoa izan behar da.");
            return false;
        }

        if (kantitatea > 1000) {
            System.out.println("Errorea: Kantitatea gehiegiz handia da (max 1000).");
            return false;
        }

        return true;
    }

    /**
     * Produktu bat gelaxka zehatz batean existitzen den egiaztatzen du.
     * * @param ean13 Produktuaren kodea.
     * 
     * @param gelaxkaID Gelaxkaren IDa.
     * @return true produktua gelaxka horretan badago, false bestela.
     */
    public boolean balidatuGelaxkaProduktua(String ean13, String gelaxkaID) {
        boolean aurkitu = false;
        for (GelaxkaStock item : stocka) {
            if (item.getGelaxkaID().equals(gelaxkaID) && item.getProduktuEAN13().equals(ean13)) {
                aurkitu = true;
                break;
            }
        }

        if (!aurkitu) {
            System.out.println("Errorea: Produktu hori ez da gelaxka horretan aurkitu.");
            return false;
        }

        return true;
    }
}
