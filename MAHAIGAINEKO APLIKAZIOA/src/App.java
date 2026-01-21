import java.util.List;
import java.util.Scanner;

/**
 * Aplikazio nagusia (Main) eta Erabiltzaile Interfazea.
 * <p>
 * Klase honek kontsola bidezko interakzioa kudeatzen du eta
 * {@link Biltegia} klaseari deiak egiten dizkio erabiltzailearen
 * eskaerak betetzeko.
 * </p>
 */
public class App {
    // Biltegia eta sarrera irakurlea (Scanner) instantziatu
    private static Biltegia biltegia = new Biltegia();
    private static Scanner sarreraIrakurlea = new Scanner(System.in);

    /**
     * Programaren abiapuntua.
     * 
     * @param args Ez dira erabiltzen.
     */
    public static void main(String[] args) {
        imprimatuGoiburua();
        erakutsiMenuNagusia();
    }

    /**
     * Aplikazioaren goiburua inprimatzen du.
     */
    private static void imprimatuGoiburua() {
        System.out.println("*****************************************");
        System.out.println("* IndiUsurbil WMS - Biltegi Kudeaketa *");
        System.out.println("*****************************************");
    }

    /**
     * Menu nagusia bistaratzen du eta erabiltzailearen aukera prozesatzen du.
     */
    private static void erakutsiMenuNagusia() {
        int erabiltzaileAukera;
        do {
            System.out.println("\n=== MENU NAGUSIA ===");
            System.out.println("1. Stock Kudeaketa (Sarrera/Irteera)");
            System.out.println("2. Informazioa eta Kontsultak");
            System.out.println("3. Mugimenduak Biltegian");
            System.out.println("0. Irten");
            System.out.print("Zure aukera: ");

            if (sarreraIrakurlea.hasNextInt()) {
                erabiltzaileAukera = sarreraIrakurlea.nextInt();
                sarreraIrakurlea.nextLine(); // Bufferra garbitu

                switch (erabiltzaileAukera) {
                    case 1:
                        kudeatuStockMenua();
                        break;
                    case 2:
                        kudeatuKontsultaMenua();
                        break;
                    case 3:
                        kudeatuMugimenduMenua();
                        break;
                    case 0:
                        System.out.println("Agur! Aplikazioa ixten...");
                        break;
                    default:
                        System.out.println("Errorea: Aukera ez da zuzena.");
                }
            } else {
                System.out.println("Errorea: Zenbaki oso bat sartu behar duzu.");
                sarreraIrakurlea.nextLine(); // Bufferra garbitu
                erabiltzaileAukera = -1;
            }
        } while (erabiltzaileAukera != 0);
    }

    // ---------------------- 1. STOCK KUDEAKETA ----------------------

    /**
     * Stock-a kudeatzeko menua.
     */
    private static void kudeatuStockMenua() {
        System.out.println("\n--- Stock Kudeaketa ---");
        System.out.println("1. Produktua Sartu");
        System.out.println("2. Produktua Atera");
        System.out.print("Zer egin nahi duzu?: ");

        if (sarreraIrakurlea.hasNextInt()) {
            int aukera = sarreraIrakurlea.nextInt();
            sarreraIrakurlea.nextLine();

            if (aukera == 1) {
                prozesatuSarrera();
            } else if (aukera == 2) {
                prozesatuIrteera();
            } else {
                System.out.println("Aukera okerra.");
            }
        } else {
            System.out.println("Zenbaki bat sartu behar da.");
            sarreraIrakurlea.nextLine();
        }
    }

    /**
     * Stock sarrera bat egiteko prozesua.
     */
    private static void prozesatuSarrera() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.print("Idatzi EAN-13 kodea: ");
        String kodigoa = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuEAN13(kodigoa)) {
            System.out.println("Ezin izan da jarraitu.");
            return;
        }

        System.out.print("Idatzi Gelaxka IDa (Adib. A1-1): ");
        String gelaID = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(gelaID)) {
            System.out.println("Ezin izan da jarraitu.");
            return;
        }

        System.out.print("Zenbat unitate sartu?: ");
        if (!sarreraIrakurlea.hasNextInt()) {
            System.out.println("Baliogabekoa: Zenbakia beharrezkoa da.");
            sarreraIrakurlea.nextLine();
            return;
        }
        int kopurua = sarreraIrakurlea.nextInt();
        sarreraIrakurlea.nextLine();

        if (!biltegia.balidatuKantitatea(kopurua)) {
            return;
        }

        if (biltegia.sartuStocka(kodigoa, gelaID, kopurua)) {
            System.out.println("=> Stock-a ondo gorde da.");
        } else {
            System.out.println("=> Arazoa egon da stock-a gordetzerakoan.");
        }
    }

    /**
     * Stock irteera bat egiteko prozesua.
     */
    private static void prozesatuIrteera() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.print("Idatzi EAN-13 kodea: ");
        String kodigoa = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuEAN13(kodigoa)) {
            System.out.println("Ezin izan da jarraitu.");
            return;
        }

        System.out.print("Zein Gelaxkatatik (ID): ");
        String gelaID = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(gelaID)) {
            System.out.println("Ezin izan da jarraitu.");
            return;
        }

        if (!biltegia.balidatuGelaxkaProduktua(kodigoa, gelaID)) {
            System.out.println("Produktua ez dago gelaxka horretan.");
            return;
        }

        System.out.print("Zenbat unitate atera?: ");
        if (!sarreraIrakurlea.hasNextInt()) {
            System.out.println("Baliogabekoa: Zenbakia beharrezkoa da.");
            sarreraIrakurlea.nextLine();
            return;
        }
        int kopurua = sarreraIrakurlea.nextInt();
        sarreraIrakurlea.nextLine();

        if (!biltegia.balidatuKantitatea(kopurua)) {
            return;
        }

        if (biltegia.ateraStocka(kodigoa, gelaID, kopurua)) {
            System.out.println("=> Stock-a ondo atera da.");
        } else {
            System.out.println("=> Arazoa egon da stock-a ateratzerakoan.");
        }
    }

    // ---------------------- 2. MUGIMENDUAK ----------------------

    /**
     * Mugimenduen menua.
     */
    private static void kudeatuMugimenduMenua() {
        System.out.println("\n--- Mugimenduak ---");
        System.out.println("1. Mugitu Produktua Gelaxkaz");
        System.out.println("2. Transferitu Gelaxka Osoa (Ez dago gaituta)");
        System.out.print("Zure aukera: ");

        if (sarreraIrakurlea.hasNextInt()) {
            int aukera = sarreraIrakurlea.nextInt();
            sarreraIrakurlea.nextLine();

            if (aukera == 1) {
                exekutatuMugimendua();
            } else if (aukera == 2) {
                System.out.println("Laster erabilgarri...");
            } else {
                System.out.println("Aukera okerra.");
            }
        } else {
            sarreraIrakurlea.nextLine();
        }
    }

    /**
     * Produktu bat lekuz aldatzeko prozesua.
     */
    private static void exekutatuMugimendua() {
        biltegia.erakutsiProduktuguztiakGelaxketan();
        System.out.println("\n>> Mugimendu bat hasi...");

        System.out.print("Produktuaren EAN-13: ");
        String kodigoa = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuEAN13(kodigoa))
            return;

        System.out.print("Jatorrizko Gelaxka: ");
        String jatorria = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(jatorria))
            return;
        if (!biltegia.balidatuGelaxkaProduktua(kodigoa, jatorria))
            return;

        System.out.print("Helmugako Gelaxka: ");
        String helmuga = sarreraIrakurlea.nextLine().trim();

        if (!biltegia.balidatuGelaxkaID(helmuga))
            return;

        System.out.print("Kantitatea: ");
        if (!sarreraIrakurlea.hasNextInt()) {
            System.out.println("Zenbaki bat behar da.");
            sarreraIrakurlea.nextLine();
            return;
        }
        int kantitatea = sarreraIrakurlea.nextInt();
        sarreraIrakurlea.nextLine();

        if (!biltegia.balidatuKantitatea(kantitatea))
            return;

        if (biltegia.mugituProduktua(kodigoa, jatorria, helmuga, kantitatea)) {
            System.out.println("=> Produktua ondo mugitu da.");
        } else {
            System.out.println("=> Ezin izan da mugitu.");
        }
    }

    // ---------------------- 3. KONTSULTAK ----------------------

    /**
     * Kontsulten menua.
     */
    private static void kudeatuKontsultaMenua() {
        System.out.println("\n--- Kontsultak ---");
        System.out.println("1. Gelaxka baten edukia ikusi");
        System.out.println("2. Inbentario osoa zerrendatu (Raw)");
        System.out.println("3. Biltegi osoa ikusi (Formateatuta)");
        System.out.print("Aukeratu: ");

        if (sarreraIrakurlea.hasNextInt()) {
            int aukera = sarreraIrakurlea.nextInt();
            sarreraIrakurlea.nextLine();

            switch (aukera) {
                case 1:
                    bistaratuGelaxka();
                    break;
                case 2:
                    bistaratuInbentarioOsoa();
                    break;
                case 3:
                    biltegia.erakutsiProduktuguztiakGelaxketan();
                    break;
                default:
                    System.out.println("Aukera ezezaguna.");
            }
        } else {
            sarreraIrakurlea.nextLine();
        }
    }

    /**
     * Gelaxka baten datuak eskatu eta erakutsi.
     */
    private static void bistaratuGelaxka() {
        System.out.print("Zein gelaxka ikusi nahi duzu? (ID): ");
        String id = sarreraIrakurlea.nextLine();

        List<GelaxkaStock> edukia = biltegia.kontsultatuGelaxka(id);

        if (edukia.isEmpty()) {
            System.out.println("Gelaxka hutsa da edo ez da existitzen.");
        } else {
            System.out.println(">>> Gelaxka " + id + " <<<");
            for (GelaxkaStock item : edukia) {
                System.out.println("  • EAN: " + item.getProduktuEAN13() + " | Kopurua: " + item.getKantitatea());
            }
        }
    }

    /**
     * Inbentario gordina inprimatu.
     */
    private static void bistaratuInbentarioOsoa() {
        System.out.println("\n--- Inbentarioaren Egoera ---");
        for (GelaxkaStock s : biltegia.kontsultatuInbentarioa()) {
            System.out.println(s);
        }
    }
}
