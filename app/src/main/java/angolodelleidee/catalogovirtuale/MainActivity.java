package angolodelleidee.catalogovirtuale;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import angolodelleidee.catalogovirtuale.database.ExerciseDbManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,CategoryFragment.OnInputInteraction ,LoginFragment.LoginListener, CarrelloFragment.CartListener{
    private ListView listCategorie;
    private Carrello carrello;
    private static final String EXAMPLE_SHARED = "catalogo_virtuale";

    private static final String USERNAME_KEY = "username";
    private static final String ORDER_NUMBER = "order_number";
    private static final int START_NUMBER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getText(MainActivity.this,MainActivity.USERNAME_KEY).isEmpty()){
            LoginFragment fragment = LoginFragment.newInstance();
            addFragment(fragment,false);
        }else {
            this.carrello = Carrello.getInstance(getText(MainActivity.this,MainActivity.USERNAME_KEY));
            CategoryFragment fragment = CategoryFragment.newInstance();
            ExerciseDbManager dbManager = new ExerciseDbManager(MainActivity.this);
            final List<ProdottoImpl> productList = dbManager.getProductsInCart();
            for (ProdottoImpl p : productList){
                this.carrello.addProdotto(p.getCategoria(),p.getCodice());
            }

            addFragment(fragment,false);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Carrello", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                FragmentManager manager = getSupportFragmentManager();

                if (manager.getBackStackEntryCount() > 0) {
                    manager.popBackStackImmediate();
                }

                Fragment fragment = manager.findFragmentById(R.id.main);
                FragmentTransaction transaction = manager.beginTransaction();
                if (fragment != null && fragment instanceof CarrelloFragment) {
                    ((CarrelloFragment) fragment).updateCarrello();
                    transaction.replace(R.id.main, fragment);
                }else{
                    CarrelloFragment carrelloFragment = CarrelloFragment.newInstance(carrello);
                    transaction.replace(R.id.main, carrelloFragment);

                }
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

            if (id == R.id.nav_scelta_articolo) {

                CategoryFragment fragment = CategoryFragment.newInstance();
                replaceFragment(fragment,false);
                // Handle the camera action
        } else if (id == R.id.nav_storico) {

        } else if (id == R.id.nav_carrello) {
                FragmentManager manager = getSupportFragmentManager();

                if (manager.getBackStackEntryCount() > 0) {
                    manager.popBackStackImmediate();
                }

                Fragment fragment = manager.findFragmentById(R.id.main);
                FragmentTransaction transaction = manager.beginTransaction();
                if (fragment != null && fragment instanceof CarrelloFragment) {
                    ((CarrelloFragment) fragment).updateCarrello();
                    transaction.replace(R.id.main, fragment);
                }else{
                    CarrelloFragment carrelloFragment = CarrelloFragment.newInstance(this.carrello);
                    transaction.replace(R.id.main, carrelloFragment);

                }
                transaction.addToBackStack(null);
                transaction.commit();

            System.out.println(this.carrello.getArticoli());

        }  else if (id == R.id.nav_contatti) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onButtonClick(String category) {
        System.out.println(category);
        ProductFragment fragment = ProductFragment.newInstance(category,this.carrello);
        replaceFragment(fragment,true);
    }


    protected void addFragment(Fragment fragment, boolean back) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main, fragment);
            if (back) {
                transaction.addToBackStack(null);
            }
            transaction.commit();

    }

    protected void replaceFragment(Fragment fragment, boolean back) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main, fragment);
        if (back) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    @Override
    public void onSuccessfulLogin(String id,boolean toRemember) {
        this.carrello = Carrello.getInstance(id);
        if(toRemember){
            setText(MainActivity.this, USERNAME_KEY,id);
        }
        CategoryFragment fragment = CategoryFragment.newInstance();
        replaceFragment(fragment,false);
    }

    @Override
    public void onUnsuccessfulLogin() {
        DialogInterface.OnClickListener listener  = null;
        final  Dialog d = new AlertDialog.Builder(MainActivity.this).setTitle("Accesso non effettuato!").setMessage("Credenziali errate").setNeutralButton("Ok",listener).create();
        listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                d.dismiss();
            }
        };
        d.show();
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return getSharedPreferences(EXAMPLE_SHARED, Context.MODE_PRIVATE);
    }
    private void setText(Context context, String key, String text) {
        getSharedPreferences(context).edit().putString(key, text).apply();
    }

    /**
     * Metodo per leggere una stringa dalle SharedPreferences.
     *
     * @param context riferimento al contesto
     * @param key chiave utilizzata in fase di scrittura
     * @return valore salvato, o se non presente, valore di default
     */
    private String getText(Context context, String key) {
        return getSharedPreferences(context).getString(key, "");
    }

    @Override
    public void orderSended() {
        ExerciseDbManager dbManager = new ExerciseDbManager(MainActivity.this);
       for(ProdottoImpl p : dbManager.getProductsInCart()){
           dbManager.deleteProduct(p);
       }
        this.carrello.emptyCart();
        /*
        final String orderNumber = getText(MainActivity.this,MainActivity.ORDER_NUMBER);
        if(orderNumber.isEmpty()){
            setText(MainActivity.this,MainActivity.ORDER_NUMBER,String.valueOf(MainActivity.START_NUMBER));
        }else{
            setText(MainActivity.this,MainActivity.ORDER_NUMBER,String.valueOf(Integer.parseInt(orderNumber)+1));
        }*/
    }
}
