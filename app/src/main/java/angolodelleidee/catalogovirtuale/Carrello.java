package angolodelleidee.catalogovirtuale;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by diego on 18/03/2017.
 */

public class Carrello {
    private final EnumMap<Product,List<Prodotto>> carrello = new EnumMap<Product, List<Prodotto>>(Product.class);
    private static Carrello INSTANCE = null ;
    private final String id;
    private Carrello(String idCliente){
        this.id = idCliente;
        for( Product p : Product.values()){
            this.carrello.put(p,new LinkedList<Prodotto>());
        }
    }
    public static Carrello getInstance(String idCliente){
        if(Carrello.INSTANCE == null){
            synchronized (Carrello.class){
                if(Carrello.INSTANCE == null){
                    Carrello.INSTANCE =  new Carrello(idCliente);
                }
            }
        }
        return Carrello.INSTANCE;
    }
    public void addProdotto(Product categoria,String codice){
        if(this.carrello.get(categoria).contains(new ProdottoImpl(categoria,codice))){
            System.out.println("Gia contenuto");
            final List<Prodotto> list =this.carrello.get(categoria);
            for(Prodotto p : list){
                if(p.equals(new ProdottoImpl(categoria,codice)) ){
                    System.out.println("Eccolo");
                    p.aggiungiUno();
                }
            }
        }else{
            System.out.println("Non contenuto");
            this.carrello.get(categoria).add(new ProdottoImpl(categoria,codice));
        }
    }
    public List<Prodotto> getProducts(){
        final List<Prodotto> toReturn = new LinkedList<>();
        for(List<Prodotto> list:  this.carrello.values()){
            for(Prodotto p : list){
                toReturn.add(p);
            }
        }
        return toReturn;
    }
    @Override
    public String toString() {
        return "Carrello{" +
                "carrello=" + carrello +
                '}';
    }

    public String[] getArticoli(){
        int maxValue = 0   ;
        for ( List<Prodotto> list:  this.carrello.values()){
            maxValue+=list.size();
        }

        String[] toReturn = new String[maxValue];
        int i = 0;
        for ( List<Prodotto> list:  this.carrello.values()){
            for (Prodotto p : list){
                toReturn[i] = p.toString();
                i++;
            }
        }
        return toReturn;
    }

    public String getId() {
        return id;
    }
}
