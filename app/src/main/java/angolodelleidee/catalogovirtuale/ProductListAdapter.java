package angolodelleidee.catalogovirtuale;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import angolodelleidee.catalogovirtuale.database.ExerciseDbManager;

/**
 * Created by Luca on 23/07/2017.
 */

public class ProductListAdapter extends ArrayAdapter<ProductItem> {

    private final List<ProductItem> dataset;
    private final Carrello carrello;
    private final ExerciseDbManager dbManager;
    public ProductListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ProductItem> objects,Carrello carrello, ExerciseDbManager dbManager) {
        super(context, resource, objects);
        this.carrello = carrello;
        dataset = objects;
        this.dbManager = dbManager;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ProductItem listViewItem = dataset.get(position);
        View view = convertView;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_states, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.product_text_view);
        ImageView imageAdd = (ImageView) convertView.findViewById(R.id.add_img);
        textView.setText(listViewItem.getProduct().getCodice());
        imageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductItem listViewItem = dataset.get(position);
                final Prodotto productToadd = listViewItem.getProduct();
                final Dialog d = new Dialog(getContext());
                d.setContentView(R.layout.custom);
                d.setTitle("Scelta quantità");
                Button alertButton = (Button) d.findViewById(R.id.button2);
//                final SeekBar bar = (SeekBar) d.findViewById(R.id.seekBar);
//                bar.setMax(10);
                final NumberPicker viewValue = (NumberPicker) d.findViewById(R.id.textView3);
                viewValue.setClickable(true);
                viewValue.setEnabled(true);
                viewValue.setMaxValue(100);
                viewValue.setMinValue(0);
//                bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                        viewValue.setValue(i);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
                alertButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProdottoImpl prodotto = new ProdottoImpl(productToadd.getCategoria(),productToadd.getCodice());
                        prodotto.aumentaQuantita(viewValue.getValue()-1);
                        carrello.addProdotto(productToadd.getCategoria(),prodotto);
                        dbManager.addProduct(prodotto);
                        Toast.makeText(getContext(),"Prodotto aggiunto al carrello",Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                });
                d.show();
            }
        });


        return convertView;
    }


}
