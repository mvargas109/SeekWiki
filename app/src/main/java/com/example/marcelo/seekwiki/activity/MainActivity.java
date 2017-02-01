package com.example.marcelo.seekwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.marcelo.seekwiki.R;
import com.example.marcelo.seekwiki.analytics.AnalyticsApplication;
import com.example.marcelo.seekwiki.model.Wikipedia;
import com.example.marcelo.seekwiki.rest.ApiClient;
import com.example.marcelo.seekwiki.rest.ApiInterface;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText edtPesquisa;
    Button btnPesquisar;
    private static final String TAG = MainActivity.class.getSimpleName();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        // Get tracker.
        Tracker t = ((AnalyticsApplication) getApplication()).getTracker(AnalyticsApplication.TrackerName.APP_TRACKER);
        // Enable Display Features so you can see demographics in Google Analytics
        t.enableAdvertisingIdCollection(true);
        // Set screen name.
        t.setScreenName("Tela Principal");
        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());

        edtPesquisa = (EditText) findViewById(R.id.edtPesquisa);

        btnPesquisar = (Button)findViewById(R.id.btnPesquisar);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiService  = ApiClient.getClient().create(ApiInterface.class);
                Call<Object> call = apiService.getWikipedia("opensearch",edtPesquisa.getText().toString(),"1");
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object>call, Response<Object> response) {
                        Wikipedia wiki = new Wikipedia();
                        wiki.setRetorno( (ArrayList<Object>) response.body());
                        String chave = wiki.getRetorno().get(0).toString();
                        String texto = ((ArrayList<String>)wiki.getRetorno().get(2)).get(0);

                        if (texto != null ) {
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("chave", chave);
                            extras.putString("texto", texto);
                            intent.putExtras(extras);
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);
    }
}
