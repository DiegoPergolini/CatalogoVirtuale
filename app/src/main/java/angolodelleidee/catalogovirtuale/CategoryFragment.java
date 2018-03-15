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
import android.widget.ListView;

/**
 * Created by diego on 15/03/2017.
 */

public class CategoryFragment extends Fragment {

    public interface OnInputInteraction {
        void onButtonClick(String category);
    }

    private ListView lsvStates;
    private OnInputInteraction listener;
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_layout, container, false);
        lsvStates = (ListView) view.findViewById(R.id.listCategorie);

        System.out.println(lsvStates==null);
        // Array di elementi di esempio
        String[] states = {"Targhette Ovali", "Formelle 20x20", "Formelle 22X36", "Calamite mignon", "Sottopentola",
                "Targhe con ferretto", "Targhe con spago", "Calamite pois", "Appendi porta", "I grandi classici in Cornice", "Saponi Naturali",
                "Termometri da muro","Quadretti con ferretto","Calamite con ferretto","Calamite legno naturale","Segnalibro","Bannere l'alfabeto degli orsetti",
                "Tavole country","Espositori"};
        String[] categorie = new String[Product.values().length];
        for(int i = 0; i< Product.values().length;i++){
            categorie[i] = Product.values()[i].getNome();
        }
        /**
         * Creazione di un'istanza della classe ArrayAdapter. Tramite il costruttore vengono passati alla classe il riferimento all'activity
         * (tramite la parola chiave this), il nome del layout che definirà l'interfaccia di una singola cella, l'id della TextView all'interno
         * del suddetto layout che si occuperà della presentazione della stringa, e la collezzione di stringhe da presentare.
         */

        ArrayAdapter<String> statesAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_category, R.id.product_text_view, categorie);

        //L'adapter appena creato viene passato alla ListView tramite il metodo apposito
        lsvStates.setAdapter(statesAdapter);

        lsvStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /**
                 * Tramite l'oggetto AdapterView e la posizione cliccata, viene recuperato l'oggetto corrispondente alla cella
                 * selezionata; a questo punto viene effettuata una conversione esplicita verso la classe a cui l'oggetto appartiene
                 * (in questo caso, String).
                 */
                String itemClicked = (String) adapterView.getItemAtPosition(position);

                /**
                 * Costrutto per la creazione di un Toast. Come parametri vengono passati il riferimento all'activity (tramite la parola chiave this),
                 * il testo da mostrare all'utente, e una costante intera per la durata del Toast.
                 */
                System.out.println(itemClicked);
                listener.onButtonClick(itemClicked);
            }
        });



        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInputInteraction) {
            listener = (OnInputInteraction) context;
        }
    }

    /**
     * Quando il fragment viene distrutto, viene eliminato il collegamento con l'activity.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
