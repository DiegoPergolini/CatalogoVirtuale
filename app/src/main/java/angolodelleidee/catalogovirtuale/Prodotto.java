package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 18/03/2017.
 */

public interface Prodotto {
    void aggiungiUno();
    void diminuisciUno();
    void aumentaQuantita(int quantita);
    void diminuisciQuantita(int quantita);
    int getQuantita();
    Product getCategoria();
    String getCodice();
}
