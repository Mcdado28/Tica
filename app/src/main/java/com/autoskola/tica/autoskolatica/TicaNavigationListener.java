package com.autoskola.tica.autoskolatica;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.webkit.WebView;

public class TicaNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    WebView webView;
    DrawerLayout drawerLayout;

    public TicaNavigationListener(Context context, WebView webView, DrawerLayout drawerLayout) {
        this.context = context;
        this.webView = webView;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }

        switch (item.getItemId()) {
            case R.id.item_navigation_drawer_onama:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/o-nama/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_placanje:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/placanje/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_testovi:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/testovi/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_obuka:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/obuka/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_rute:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/ispitne-rute/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_upitnik:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/upitnik/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_instruktori:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/instruktori/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_galerija:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/galerija/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;



            case R.id.item_navigation_drawer_kontakt:
                item.setChecked(true);
                webView.loadUrl("http://autoskolatica.ba/kontakt/");
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;


            case R.id.item_navigation_drawer_info:
                item.setChecked(true);
                Intent launchPrefs = new Intent(context, Preferences.class);
                context.startActivity(launchPrefs);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

        }

        return true;
    }

}

