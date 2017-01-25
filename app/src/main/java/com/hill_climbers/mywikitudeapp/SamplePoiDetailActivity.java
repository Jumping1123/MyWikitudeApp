package com.hill_climbers.mywikitudeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class SamplePoiDetailActivity extends AppCompatActivity {
    private WebView mWebView;

    public static final String EXTRAS_KEY_POI_ID = "id";
    public static final String EXTRAS_KEY_POI_TITILE = "title";
    public static final String EXTRAS_KEY_POI_DESCR = "description";
    public static final String EXTRAS_KEY_POI_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sample_poidetail);

        //メニュー：戻るボタン
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //メニュー：タイトル表示
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getExtras().getString(EXTRAS_KEY_POI_TITILE));

//        ((TextView)findViewById(R.id.poi_id)).setText( getIntent().getExtras().getString(EXTRAS_KEY_POI_ID) );
//        ((TextView)findViewById(R.id.poi_title)).setText( getIntent().getExtras().getString(EXTRAS_KEY_POI_TITILE) );
//        ((TextView)findViewById(R.id.poi_description)).setText( getIntent().getExtras().getString(EXTRAS_KEY_POI_DESCR) );

        //WebView
        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl(getIntent().getExtras().getString(EXTRAS_KEY_POI_URL));
    }

    //戻るボタンの動作内容
    @Override
    public boolean onOptionsItemSelected(MenuItem item){ // Called when you tap a menu item
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }
}
