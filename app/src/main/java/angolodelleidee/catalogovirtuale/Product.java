package angolodelleidee.catalogovirtuale;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by diego on 17/03/2017.
 */

public enum Product {
    TGV("Targhette Ovali","TGV",TipoCodice.NUMERICO,"76"),
    FGO("Formelle 20x20","FGO",TipoCodice.NUMERICO,"36"),
    CMQ("Calamite Mignon","MCQ",TipoCodice.NUMERICO,"36"),
    STT("Sottopentola","STT",TipoCodice.NUMERICO,"12"),
    FGM("Formelle 22x36","FGM",TipoCodice.NUMERICO,"8"),
    TFM("Targhe con ferretto","TFM",TipoCodice.NUMERICO,"80"),
    TSP("Targhe con spago","TSP",TipoCodice.NUMERICO,"48"),
    BAC("Banner l'alfabeto degli Orsetti-BAC","BAC",TipoCodice.ALFABETICO,"Z");

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
