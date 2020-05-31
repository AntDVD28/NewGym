package com.example.newgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Ayuda extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        btVolver = findViewById(R.id.btVolver);

        btVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cierra la activity en la que nos encontramos
                finish();
            }
        });
    }


}
