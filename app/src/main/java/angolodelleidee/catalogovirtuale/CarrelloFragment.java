package angolodelleidee.catalogovirtuale;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by diego on 18/03/2017.
 */

public class CarrelloFragment extends Fragment {
    private static Carrello carrello;
    ArrayAdapter<String> statesAdapter;
    Button sendButton;
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
        this.sendButton = (Button) view.findViewById(R.id.sendButton);
        lsvStates = (ListView) view.findViewById(R.id.lista_prodotti);
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CartSender().execute(carrello.getProducts());
            }
        });
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


    public class CartSender extends AsyncTask<List<Prodotto>,Void,Void> {
        //Nome dei parametri del json di risposta
        private static final String JSON_MESSAGE = "Articolo";
        private static final String JSON_MESSAGE1 = "Quantita";
        private static final String JSON_DATA = "data";

        private static final String SUCCESS_URL = "http://192.168.1.118/adi_cv/saveOrder.php";


        @Override
        protected Void doInBackground(List<Prodotto>... params) {
            try {
                URL url = new URL(SUCCESS_URL); //Enter URL here
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.connect();

                JSONObject jsonObject = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                jsonObject.put("Cliente",carrello.getId());
                jsonObject.put("Array",jsonArray);

                for (Prodotto p: params[0]){
                    JSONObject product = new JSONObject();
                    product.put(JSON_MESSAGE,p.getCodice());
                    product.put(JSON_MESSAGE1,p.getQuantita());
                    jsonArray.put(product);
                }

                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(jsonObject.toString());
                wr.flush();
                wr.close();
                StringBuilder response = new StringBuilder();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                Log.d("risposta",response.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

