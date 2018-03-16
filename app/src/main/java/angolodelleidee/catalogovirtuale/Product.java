package angolodelleidee.catalogovirtuale;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by diego on 17/03/2017.
 */

public enum Product {
    APN("Appendiporta","APN",TipoCodice.NUMERICO,"30"),
    BAC("Banner Baby-BAC","BAC",TipoCodice.ALFABETICO,"Z"),
    BAR("Banner Baby-BAR","BAR",TipoCodice.ALFABETICO,"Z"),
    COR("Calamite Baby","COR",TipoCodice.ALFABETICO,"Z"),
    MCQ("Calamite Mignon","MCQ",TipoCodice.NUMERICO,"36"),
    CMS("Calamite Pois","CMS",TipoCodice.NUMERICO,"54"),
    CLQ("Calamite Francobollo","CLQ",TipoCodice.NUMERICO,"48"),
    FGO("Formelle ","FGO",TipoCodice.NUMERICO,"36"),
    TGV("Targhette Ovali","TGV",TipoCodice.NUMERICO,"76"),
    QRR("Quadretti con ferretto","QRR",TipoCodice.NUMERICO,"48"),
    SSP("Saponi Family","SSP",TipoCodice.NUMERICO,"3"),
    SBL("Segnalibri","SBL",TipoCodice.NUMERICO,"24"),
    STT("Sottopentola","STT",TipoCodice.NUMERICO,"10"),
    TFM("Targhe con ferretto","TFM",TipoCodice.NUMERICO,"86"),
    TSP("Targhe con spago","TSP",TipoCodice.NUMERICO,"60"),
    TVC("Tavole country","TVC",TipoCodice.NUMERICO,"24"),
    TRM("Termometri","TRM",TipoCodice.NUMERICO,"12");

    private enum TipoCodice{
        ALFABETICO,NUMERICO;
    }
    private final String nome;
    private final String codiceCategoria;
    private final TipoCodice tipoCodice;
    private final String codiceMax;


    Product(String nome, String codiceCategoria, TipoCodice tipoCodice, String codiceMax) {
        this.nome = nome;
        this.codiceCategoria = codiceCategoria;
        this.tipoCodice = tipoCodice;
        this.codiceMax = codiceMax;
    }

    public String getNome() {
        return nome;
    }

    public String getCodiceCategoria() {
        return codiceCategoria;
    }


    public TipoCodice getTipoCodice() {
        return tipoCodice;
    }

    public String getCodiceMax() {
        return codiceMax;
    }

    public String[] getCodiciProdotto(){
        int maxValue = 0;
        if(this.getTipoCodice() == TipoCodice.NUMERICO){
            maxValue = Integer.parseInt(this.codiceMax);
        }else {
            maxValue = 26;
            String[] toReturn = new String[maxValue];

            for (int i = 0; i< maxValue;i++){
                char lettera = (char) ('A'+i);
                toReturn[i]= (this.getCodiceCategoria()+"-"+lettera);
            }
            return toReturn;
        }
        String[] toReturn = new String[maxValue];
        System.out.println(maxValue);
        for (int i = 0; i< maxValue;i++){
            toReturn[i]= (this.getCodiceCategoria()+"-"+(i+1));
        }

        return toReturn;
    }
    public List<Prodotto> getCategoryProducts(){
        List<Prodotto> toReturn = new LinkedList<>();
        int maxValue = 0;
        if(this.getTipoCodice() == TipoCodice.NUMERICO){
            maxValue = Integer.parseInt(this.codiceMax);
        }else {
            maxValue = 26;

            for (int i = 0; i< maxValue;i++){
                char lettera = (char) ('A'+i);
                toReturn.add(new ProdottoImpl(this,this.getCodiceCategoria()+"-"+lettera));
            }
            return toReturn;
        }
        for (int i = 0; i< maxValue;i++){
            toReturn.add(new ProdottoImpl(this,this.getCodiceCategoria()+"-"+(i+1)));
        }

        return toReturn;
    }
    @Override
    public String toString() {
        return "Product{" +
                "nome='" + nome + '\'' +
                ", codiceCategoria='" + codiceCategoria + '\'' +
                ", tipoCodice=" + tipoCodice +
                ", codiceMax='" + codiceMax + '\'' +
                '}';
    }
}
