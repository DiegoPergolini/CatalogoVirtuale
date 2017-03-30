package angolodelleidee.catalogovirtuale;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,CategoryFragment.OnInputInteraction ,LoginFragment.LoginListener{
    private ListView listCategorie;
    private Carrello carrello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoginFragment fragment = LoginFragment.newInstance();
       // CategoryFragment fragment = CategoryFragment.newInstance();
        addFragment(fragment,false);
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
    public void onSuccessfulLogin(String id) {
        this.carrello = Carrello.getInstance(id);
        CategoryFragment fragment = CategoryFragment.newInstance();
        replaceFragment(fragment,false);
    }

    @Override
    public void onUnsuccessfulLogin() {

    }
}
