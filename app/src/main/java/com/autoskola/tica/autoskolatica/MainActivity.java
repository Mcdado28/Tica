package com.autoskola.tica.autoskolatica;

import android.*;
import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity  {

    DrawerLayout drawerLayout;
    CoordinatorLayout rootlayout;
    Toolbar toolbar;
    ActionBar actionBar;
    TextView textView;
    WebView webView;
    SharedPreferences sharedPreferences;

    // Izlazak iz aplikacije sa porukom
    long lastPress;

    // Definisi pocetnu stranicu
    public static final String TICA_HOME_URL = "http://autoskolatica.ba/";



    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);






        // Pokretanje Floating Action Button-a
        rootlayout = (CoordinatorLayout) findViewById(R.id.rootlayout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_orange_900)));

        // Pokreni toolbar
        pokreniToolbar();

        // Postavke WebView-a
        pokreniWebView();

        // Definisi swipe to refresh
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeit);
        swipeRefreshLayout.setOnRefreshListener(new TicaSwipeRefreshListener(swipeRefreshLayout, webView));

        // Ustimaj navigaciju
        drawerLayout = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new TicaNavigationListener(this, webView, drawerLayout));

        //OTVARANAJE LINK-A PUSH NOTIFIKACIJE UNUTAR SAME APLIKACIJE
        provjeriBundle();

    }   //END OF ON_CREATE



    // POKRENI TOOLBAR
    public void pokreniToolbar() {

        //postavka toolbar-a statusne trake u aplikaciji
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Auto škola Tica");
        toolbar.setTitleTextColor(Color.DKGRAY);
        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar = getSupportActionBar();

    }

    // POKRENI WEB VIEW KOMPONENTU
    public void pokreniWebView() {

        webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);

        // Iskljuci geolokaciju
        webView.getSettings().setGeolocationEnabled(false);

        // Iskljuci postavljanje pocetnog fokusa
        webView.getSettings().setNeedInitialFocus(false);

        // Iskljuci sacuvljavanje podataka u web formama
        webView.getSettings().setSaveFormData(false);

        // Dozvoli pristup fajlovima unutar WebView komponente
        webView.getSettings().setAllowFileAccess(true);

        webView.setWebViewClient(new TicaWebViewClient(this));

    }

    public void provjeriBundle() {

        Bundle extras = getIntent().getExtras();

        if(null != extras && extras.containsKey("poruka") && extras.containsKey("link")) {

            webView.loadUrl(extras.getString("link"));

        } else {

            webView.loadUrl(TICA_HOME_URL);
        }

    }

    // poruka na klik  koju koristimo na floating action button-u
    public void Poruka (View v) {
        Snackbar.make(rootlayout, "Da li želite da polažete? Testirajte svoje znanje.", Snackbar.LENGTH_LONG).setActionTextColor(Color.WHITE).setAction("više ->", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                webView.loadUrl("http://autoskolatica.ba/testovi/");

            }

        }).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        webView.loadUrl(TICA_HOME_URL);

        return super.onOptionsItemSelected(item);
    }

    // Sta se desava kada pritisnemo BACK dugmic
    // Napravimo ga pametnim :)
    @Override
    public void onBackPressed() {


        // Ako je navigation drawer otvoren zatvori ga
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        }

        // Ako je navigation drawer zatvoren i ako u memoriji ima jos jedna stranica idi jednu stranicu nazad
        else if (!drawerLayout.isDrawerOpen(GravityCompat.START) && webView.canGoBack()) {

            webView.goBack();

        } else  // Izlazak iz aplikacije sa porukom
        {
            long currentTime = System.currentTimeMillis();
            if(currentTime - lastPress > 1000){
                Toast.makeText(getBaseContext(), "Pritisni ponovo za izlaz.", Toast.LENGTH_SHORT).show();
                lastPress = currentTime;
            }else

                // Ako nista od navedenog nije istina napusti aplikaciju
                super.onBackPressed();
        }
    }

}


