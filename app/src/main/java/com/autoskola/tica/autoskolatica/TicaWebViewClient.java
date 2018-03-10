package com.autoskola.tica.autoskolatica;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.MailTo;
import android.net.Uri;
import android.provider.Settings;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TicaWebViewClient extends WebViewClient {

    Context context;

    public TicaWebViewClient(Context context){
        this.context = context;
    }

    // pokazivanje postavki kada aplikacija nema pristup internetu

    @SuppressWarnings("deprecation")
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (errorCode == -2) {
            view.loadData("", "", null);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        builder.setCancelable(false);
        builder.setTitle(Html.fromHtml("<font color='#BA7704'><b>Auto škola Tica</b></font>"));
        builder.setMessage(Html.fromHtml("<font color='#000000'>Nemate internet konekcije. Konektujte se i pokušajte ponovo !</font>"));
        builder.setPositiveButton(Html.fromHtml("<font color='#388E3C'><b>Wifi Postavke</b></font>"), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                context.startActivity(intent);

            }
        });


        builder.setNegativeButton(Html.fromHtml("<font color='#7F02AE'><b>Zatvori</b></font>"), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    //sakrivanje html class-a unutar aplikacije
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('hero')[0]) != 'undefined' && document.getElementsByClassName('hero')[0] != null){document.getElementsByClassName('hero')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('copyright')[0]) != 'undefined' && document.getElementsByClassName('copyright')[0] != null){document.getElementsByClassName('copyright')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('sidebar')[0]) != 'undefined' && document.getElementsByClassName('sidebar')[0] != null){document.getElementsByClassName('sidebar')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('col-sm-4 aplikacija')[0]) != 'undefined' && document.getElementsByClassName('col-sm-4 aplikacija')[0] != null){document.getElementsByClassName('col-sm-4 aplikacija')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('col-md-8 sadrzaj')[0]) != 'undefined' && document.getElementsByClassName('col-md-8 sadrzaj')[0] != null){document.getElementsByClassName('col-md-8 sadrzaj')[0].style.paddingBottom = '435px';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('loader')[0]) != 'undefined' && document.getElementsByClassName('loader')[0] != null){document.getElementsByClassName('loader')[0].style.display = 'none';} void 0");

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        // Mogucnost poziva na klik unutar Webview-a

        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            context.startActivity(intent);
            return true;
            // Mogucnost slanja email-a na klik unutar Webview-a
        } else if (url.startsWith("mailto:")) {
            MailTo mailTo = MailTo.parse(url);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo.getTo()});
            intent.putExtra(Intent.EXTRA_TEXT, mailTo.getBody());
            intent.putExtra(Intent.EXTRA_SUBJECT, mailTo.getSubject());
            intent.putExtra(Intent.EXTRA_CC, mailTo.getCc());
            intent.setType("message/rfc822");
            context.startActivity(intent);
            return true;
        } else {
            return false;
        }
    }

}
