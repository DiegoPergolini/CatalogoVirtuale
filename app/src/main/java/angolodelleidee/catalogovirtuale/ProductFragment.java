package angolodelleidee.catalogovirtuale;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by diego on 15/03/2017.
 */

public class ProductFragment extends Fragment {
    private GridView lsvStates;
    private TextView txvName;
    private String name;
    private Product product;
    public static ProductFragment newInstance(String name) {
        ProductFragment fragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    public ProductFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_layout, container, false);
        txvName = (TextView) view.findViewById(R.id.nameCategory);
        txvName.setText(name);

        for (Product p: Product.values()) {
            if(p.getNome().equals(name)){
                product = p;
            }
        }
        System.out.println(product);
        lsvStates = (GridView) view.findViewById(R.id.product_grid);

        System.out.println(lsvStates==null);
        // Array di elementi di esempio

        String[] states = {"Targhette Ovali", "Formelle 20X20", "Formelle 22X36", "Calamite mignon", "Sottopentola",
                "Targhe con ferretto", "Targhe con spago", "Calamite pois", "Appendi porta", "I grandi classici in Cornice", "Saponi Naturali",
                "Termometri da muro","Quadretti con ferretto","Calamite con ferretto","Calamite legno naturale","Segnalibro","Bannere l'alfabeto degli orsetti",
                "Tavole country","Espositori"};

        /**
         * Creazione di un'istanza della classe ArrayAdapter. Tramite il costruttore vengono passati alla classe il riferimento all'activity
         * (tramite la parola chiave this), il nome del layout che definirà l'interfaccia di una singola cella, l'id della TextView all'interno
         * del suddetto layout che si occuperà della presentazione della stringa, e la collezzione di stringhe da presentare.
         */

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_states, R.id.txv_state, product.getCodiciProdotto());

        //L'adapter appena creato viene passato alla ListView tramite il metodo apposito
        lsvStates.setAdapter(statesAdapter);

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
