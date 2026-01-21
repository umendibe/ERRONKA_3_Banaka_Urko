import java.util.regex.Pattern;

/**
 * Biltegi-gelaxka baten stock-a kudeatzen duen klasea.
 * <p>
 * Gelaxka bakoitzak ID zehatz bat du (Adib: A1-1) eta produktu mota baten
 * kantitate jakin bat gordetzen du.
 * </p>
 */
public class GelaxkaStock {
    /** Gelaxka IDak balioztatzeko eredu erregularra (Adib: A1-1). */
    private static final Pattern GELAXKA_EREDUA = Pattern.compile("^[A-Z]\\d+-\\d+$");

    private String gelaxkaID;
    private String produktuEAN13;
    private int kantitatea;

    /**
     * GelaxkaStock objektua sortzen du.
     * * @param gelaxkaID Gelaxkaren IDa (Formatua: LetraLarriaZenbakia-Zenbakia).
     * 
     * @param produktuEAN13 Produktuaren EAN kodea.
     * @param kantitatea    Produktu kopurua.
     * @throws IllegalArgumentException Gelaxka IDak ez badu formatu egokia
     *                                  betetzen.
     */
    public GelaxkaStock(String gelaxkaID, String produktuEAN13, int kantitatea) {
        if (!baliozkoaDaGelaxkaID(gelaxkaID)) {
            throw new IllegalArgumentException("GelaxkaID baliogabea");
        }
        this.gelaxkaID = gelaxkaID;
        this.produktuEAN13 = produktuEAN13;
        this.kantitatea = kantitatea;
    }

    /**
     * Gelaxkaren IDa lortzen du.
     * 
     * @return Gelaxkaren identifikadorea.
     */
    public String getGelaxkaID() {
        return gelaxkaID;
    }

    /**
     * Gelaxkan dagoen produktuaren EAN kodea lortzen du.
     * 
     * @return Produktuaren EAN-13 kodea.
     */
    public String getProduktuEAN13() {
        return produktuEAN13;
    }

    /**
     * Gelaxkan dagoen produktu kantitatea lortzen du.
     * 
     * @return Stock kantitatea.
     */
    public int getKantitatea() {
        return kantitatea;
    }

    /**
     * Gelaxkaren stock kantitatea eguneratzen du.
     * 
     * @param kantitatea Kantitate berria.
     */
    public void setKantitatea(int kantitatea) {
        this.kantitatea = kantitatea;
    }

    /**
     * Gelaxka IDaren formatua balioztatzen du barne mailan.
     * * @param id Egiaztatu beharreko IDa.
     * 
     * @return true formatua (LetraLarria+Zenbakiak+"-"+Zenbakiak) zuzena bada,
     *         false bestela.
     */
    private static boolean baliozkoaDaGelaxkaID(String id) {
        return id != null && GELAXKA_EREDUA.matcher(id).matches();
    }

    /**
     * Gelaxkaren egoera testu moduan itzultzen du.
     * 
     * @return Gelaxkaren informazioa String formatuan.
     */
    @Override
    public String toString() {
        return "Gelaxka: " + gelaxkaID + ", EAN: " + produktuEAN13 + ", Kantitate: " + kantitatea;
    }
}
