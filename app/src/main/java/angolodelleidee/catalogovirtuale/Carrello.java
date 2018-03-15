package angolodelleidee.catalogovirtuale;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import angolodelleidee.catalogovirtuale.database.ExerciseDbManager;

/**
 * Created by diego on 18/03/2017.
 */

public class Carrello {
    private final EnumMap<Product,List<Prodotto>> carrello = new EnumMap<Product, List<Prodotto>>(Product.class);
    private static Carrello INSTANCE = null ;
    private final String id;
    private final ExerciseDbManager exerciseDbManager;
    private Carrello(String idCliente,ExerciseDbManager dbManager){
        this.id = idCliente;
        for( Product p : Product.values()){
            this.carrello.put(p,new LinkedList<Prodotto>());
        }
        this.exerciseDbManager = dbManager;
    }
    public static Carrello getInstance(String idCliente, ExerciseDbManager dbManager){
        if(Carrello.INSTANCE == null){
            synchronized (Carrello.class){
                if(Carrello.INSTANCE == null){
                    Carrello.INSTANCE =  new Carrello(idCliente,dbManager);
                }
            }
        }
        return Carrello.INSTANCE;
    }
    public Prodotto addProdotto(Product categoria,Prodotto prodotto){
        final List<String> productCodes = new LinkedList<>();
        for(Prodotto p : this.carrello.get(categoria)){
            productCodes.add(p.getCodice());
        }
        if(productCodes.contains(prodotto.getCodice())){
            for(Prodotto p : this.carrello.get(categoria)){
                if(p.getCodice().equals(prodotto.getCodice())){
                }
                p.aumentaQuantita(prodotto.getQuantita());
                return p;
            }
        }else {
            this.carrello.get(categoria).add(prodotto);
            return prodotto;
        }
        return null;
    }

    public boolean removeProduct(String id){
        final List<Prodotto> toRemoveList = new LinkedList<>();
        final Product toRemove = ProdottoImpl.getProductFromCode(id);
        for (int i = 0; i< carrello.get(toRemove).size();i++){
            final Prodotto p = carrello.get(toRemove).get(i);
            if(p.getCodice().equals(id)){
                carrello.get(toRemove).remove(p);
                this.exerciseDbManager.deleteProduct(new ProdottoImpl(p.getCategoria(),p.getCodice()));
                return true;
            }
        }
        return false;
    }
    /*
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
*/
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
    public void emptyCart(){
        for( Product p : Product.values()){
            this.carrello.put(p,new LinkedList<Prodotto>());
        }
    }
}
