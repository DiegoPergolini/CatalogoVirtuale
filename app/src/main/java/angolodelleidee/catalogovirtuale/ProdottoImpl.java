package angolodelleidee.catalogovirtuale;

/**
 * Created by diego on 18/03/2017.
 */

public class ProdottoImpl implements Prodotto {
    private final Product categoria;
    private int quantita;
    private final String codice;

    public ProdottoImpl(Product categoria, String codice) {
        this.categoria = categoria;
        this.codice = codice;
        this.quantita = 1;
    }
    public ProdottoImpl(Product categoria, String codice,int quantita) {
        this.categoria = categoria;
        this.codice = codice;
        this.quantita = quantita;
    }
    @Override
    public void aggiungiUno() {
        this.quantita++;
    }

    @Override
    public void diminuisciUno() {
        this.quantita--;
    }

    @Override
    public void aumentaQuantita(int quantita) {
        this.quantita += quantita;
    }

    @Override
    public void diminuisciQuantita(int quantita) {
        this.quantita -= quantita;
    }

    @Override
    public int getQuantita() {
        return this.quantita;
    }

    @Override
    public Product getCategoria() {
        return this.categoria;
    }

    @Override
    public String getCodice() {
        return this.codice;
    }

    @Override
    public String toString() {
        return "Codice: "+this.codice+" ,Quantita': "+ this.quantita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdottoImpl prodotto = (ProdottoImpl) o;

        if (categoria != prodotto.categoria) return false;
        return codice.equals(prodotto.codice);

    }

    @Override
    public int hashCode() {
        int result = categoria.hashCode();
        result = 31 * result + codice.hashCode();
        return result;
    }
}
