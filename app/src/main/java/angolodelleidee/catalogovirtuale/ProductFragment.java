package angolodelleidee.catalogovirtuale;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import angolodelleidee.catalogovirtuale.database.ExerciseDbManager;

/**
 * Created by diego on 15/03/2017.
 */

public class ProductFragment extends Fragment {
    private GridView lsvStates;
    private TextView txvName;
    private static Carrello carrello;
    private String name;
    private Product product;
    private int lastTouched = -1;
    ExerciseDbManager dbManager;
    public static ProductFragment newInstance(String name,Carrello carrelloPassato) {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        carrello = carrelloPassato;
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ProductFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_layout, container, false);
        dbManager = new ExerciseDbManager(getActivity());
        txvName = (TextView) view.findViewById(R.id.nameCategory);
        txvName.setText(name);

        for (Product p: Product.values()) {
            if(p.getNome().equals(name)){
                product = p;
            }
        }
        System.out.println(product);
        lsvStates = (GridView) view.findViewById(R.id.product_grid);

        /**
         * Creazione di un'istanza della classe ArrayAdapter. Tramite il costruttore vengono passati alla classe il riferimento all'activity
         * (tramite la parola chiave this), il nome del layout che definirà l'interfaccia di una singola cella, l'id della TextView all'interno
         * del suddetto layout che si occuperà della presentazione della stringa, e la collezzione di stringhe da presentare.
         */
        List<ProductItem> productItemList = new LinkedList<>();
        for(Prodotto pItem :product.getCategoryProducts()){
            productItemList.add(new ProductItem(pItem));
        }
        ProductListAdapter productListAdapter = new ProductListAdapter(getActivity(), R.layout.list_item_states,productItemList,carrello,dbManager);

        //L'adapter appena creato viene passato alla ListView tramite il metodo apposito
        lsvStates.setAdapter(productListAdapter);
//        lsvStates.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
//                setDialog((String) parent.getItemAtPosition(position));
//                return true;
//            }
//        });
//        lsvStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(lastTouched == position){
//                    final ProdottoImpl prodotto = new ProdottoImpl(product,(String) parent.getItemAtPosition(position));
//                    carrello.addProdotto(product,prodotto);
//                    dbManager.addProduct(prodotto);
//                    lastTouched = -1;
//                    Toast.makeText(getContext(),"Prodotto aggiunto al carrello",Toast.LENGTH_SHORT).show();
//                }else {
//                    lastTouched = position;
//                }
//
//            }
//        });
        System.out.println(name);

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    /**
     * Quando il fragment viene distrutto, viene eliminato il collegamento con l'activity.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
