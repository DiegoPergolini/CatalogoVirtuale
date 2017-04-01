package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 18/03/2017.
 */

public interface Prodotto {
    public static final String TABLE_NAME = "Prodotti_carrello";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "surname";
    void aggiungiUno();
    void diminuisciUno();
    void aumentaQuantita(int quantita);
    void diminuisciQuantita(int quantita);
    int getQuantita();
    Product getCategoria();
    String getCodice();
}
