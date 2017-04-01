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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
import java.util.Map;

/**
 * Created by diego on 30/03/2017.
 */

public class LoginFragment extends Fragment {
    private EditText emailField ;
    private EditText pwField ;
    private Button loginButton;
    private CheckBox checkBox;
    private  LoginListener listener;

    private enum Result{
        LOGGED,NOT_LOGGED;
    }

    public interface LoginListener{
        void onSuccessfulLogin(String id,boolean toRemember);
        void onUnsuccessfulLogin();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LoginListener) {
            listener = (LoginListener) context;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    public static LoginFragment newInstance(){
        return new LoginFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_layout,container,false);
        this.emailField =(EditText) view.findViewById(R.id.edtMail);
        this.pwField =(EditText) view.findViewById(R.id.edtPw);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        this.loginButton =(Button) view.findViewById(R.id.btnLogin);
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginTask().execute(new ClienteImpl(emailField.getText().toString(),pwField.getText().toString()));
            }
        });
        return view;
    }

    public class LoginTask extends AsyncTask<Cliente,Void,Result> {
        private String getPostDataString(HashMap<String,String > params) throws UnsupportedEncodingException {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for(Map.Entry<String, String> entry : params.entrySet()){
                if(first)
                    first = false;
                    else
                    result.append("&");
                    result.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
                }

            return  result.toString();
        }

        //Nome dei parametri del json di risposta

        private static final String SUCCESS_URL = "http://192.168.1.118/adi_cv/login.php";
        Result toReturn ;
        HttpURLConnection httpURLConnection = null;
        StringBuilder response = new StringBuilder();
        BufferedReader rd = null;

        @Override
        protected void onPostExecute(Result result) {
            if(result.equals(Result.LOGGED)){
                listener.onSuccessfulLogin(response.toString(),checkBox.isChecked());
            }else{
                listener.onUnsuccessfulLogin();
            }
        }

        @Override
        protected Result doInBackground(Cliente... params) {
            try {
                URL url = new URL(SUCCESS_URL); //Enter URL here

                httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                //httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                HashMap<String,String> toPass = new HashMap<>();
                toPass.put("email",params[0].getEmail());
                toPass.put("password",params[0].getPassword());
                writer.write(getPostDataString(toPass));
                writer.flush();
                writer.close();
                os.close();
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == httpURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    rd = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = rd.readLine())!= null){
                        response.append(line);
                    }
                }
                if(response.toString().isEmpty()){
                    toReturn = Result.NOT_LOGGED;
                    Log.d("risposta","Credenziali errate");
                }else{
                    toReturn = Result.LOGGED;
                    Log.d("risposta",response.toString());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if( rd != null){
                    try {
                        rd.close();
                    }catch (Exception e){

                    }
                }
                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }
            }

            return toReturn;
        }
    }

}
