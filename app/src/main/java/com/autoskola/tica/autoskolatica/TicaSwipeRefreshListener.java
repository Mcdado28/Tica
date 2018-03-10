package com.autoskola.tica.autoskolatica;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebView;

public class TicaSwipeRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView;

    public TicaSwipeRefreshListener(SwipeRefreshLayout swipeRefreshLayout, WebView webView) {

        this.swipeRefreshLayout = swipeRefreshLayout;
        this.webView = webView;

    }

    @Override
    public void onRefresh() {

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(Color.BLACK);
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout.setRefreshing(false);
                webView.reload();
            }
        }, 1000);

    }

}
