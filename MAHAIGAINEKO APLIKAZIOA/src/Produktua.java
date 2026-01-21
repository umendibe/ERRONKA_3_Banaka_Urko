/**
 * Produktu bat adierazten duen klasea.
 * <p>
 * Klase honek produktuaren oinarrizko informazioa gordetzen du:
 * EAN-13 kodea, izena eta kategoria.
 * </p>
 *
 * @author IndiUsurbil Taldea
 * @version 1.0
 */
public class Produktua {
    private String ean13;
    private String izena;
    private String kategoria;

    /**
     * Produktu berri bat sortzen du emandako datuekin.
     *
     * @param ean13     Produktuaren EAN-13 identifikagailu bakarra.
     * @param izena     Produktuaren izen komertziala.
     * @param kategoria Produktua zein kategoriatakoa den (adib. "Kamisetak").
     */
    public Produktua(String ean13, String izena, String kategoria) {
        this.ean13 = ean13;
        this.izena = izena;
        this.kategoria = kategoria;
    }

    /**
     * Produktuaren EAN-13 kodea itzultzen du.
     * * @return 13 digituko EAN kodea String formatuan.
     */
    public String getEan13() {
        return ean13;
    }

    /**
     * Produktuaren izena itzultzen du.
     * * @return Produktuaren izena.
     */
    public String getIzena() {
        return izena;
    }

    /**
     * Produktuaren kategoria itzultzen du.
     * * @return Produktuaren kategoria.
     */
    public String getKategoria() {
        return kategoria;
    }

    /**
     * Produktuaren informazioa testu formatuan itzultzen du.
     * Formatua: [EAN] Izena (Kategoria)
     *
     * @return Produktuaren String adierazpena.
     */
    @Override
    public String toString() {
        return "[" + ean13 + "] " + izena + " (" + kategoria + ")";
    }
}