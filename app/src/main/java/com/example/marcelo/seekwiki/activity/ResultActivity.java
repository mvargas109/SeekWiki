package com.example.marcelo.seekwiki.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.marcelo.seekwiki.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView txvTermoPesquisado;
    TextView txvResultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txvTermoPesquisado = (TextView) findViewById(R.id.txvTermoPesquisado);
        txvResultado = (TextView) findViewById(R.id.txvResultado);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            txvTermoPesquisado.setText(extras.getString("chave"));
            txvResultado.setText(extras.getString("texto"));
        }
    }
}
