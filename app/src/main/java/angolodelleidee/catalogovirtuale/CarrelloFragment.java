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
 * Created by diego on 18/03/2017.
 */

public class CarrelloFragment extends Fragment {
    private static Carrello carrello;
    ArrayAdapter<String> statesAdapter;
    private ListView lsvStates;
    public static CarrelloFragment newInstance(Carrello carrelloPassato) {
        CarrelloFragment fragment = new CarrelloFragment();
        carrello = carrelloPassato;
        return fragment;
    }
    public CarrelloFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carrello_layout, container, false);

        lsvStates = (ListView) view.findViewById(R.id.lista_prodotti);

        /**
         * Creazione di un'istanza della classe ArrayAdapter. Tramite il costruttore vengono passati alla classe il riferimento all'activity
         * (tramite la parola chiave this), il nome del layout che definirà l'interfaccia di una singola cella, l'id della TextView all'interno
         * del suddetto layout che si occuperà della presentazione della stringa, e la collezzione di stringhe da presentare.
         */

       statesAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_states, R.id.txv_state, carrello.getArticoli());

        //L'adapter appena creato viene passato alla ListView tramite il metodo apposito
        lsvStates.setAdapter(statesAdapter);
        lsvStates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }

    public void updateCarrello(){
        statesAdapter.clear();
        statesAdapter.addAll(carrello.getArticoli());
        statesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

