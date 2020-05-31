package com.example.newgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Entrenamiento extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btGuardar;
    private RadioGroup rgOpciones;

    private String fecha, nombre, edad, genero;
    private int privacidad ,opcion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrenamiento);

        //Componentes
        btGuardar = findViewById(R.id.btGuardar);
        rgOpciones = findViewById(R.id.rgOpciones);

        //Toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        //Rescato la información enviada desde la anterior activity
        //Rescato la información enviada y la guardo en las variables globales
        Bundle datos = getIntent().getExtras();

        fecha = datos.getString("fecha");
        nombre = datos.getString("nombre");
        edad = datos.getString("edad");
        genero = datos.getString("genero");
        privacidad = datos.getInt("privacidad");

        //****************************************************************************************//
        //                                  R A D I O G R O U P                                   //
        //****************************************************************************************//

        rgOpciones.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rbRespuesta1){
                    opcion = 1;
                }else if(checkedId == R.id.rbRespuesta2){
                    opcion = 2;
                }else if(checkedId == R.id.rbRespuesta3){
                    opcion = 3;
                }else if(checkedId == R.id.rbRespuesta4){
                    opcion = 4;
                }else if(checkedId == R.id.rbRespuesta5){
                    opcion = 5;
                }
            }
        });

        //Creamos una instancia para poder acceder a la BD
        final HelperBD dbHelper = new HelperBD(this);

        //Evento hacer clic en el botón. Debo de validar que se seleccionó una opción
        btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==0){
                    Toast.makeText(getApplicationContext(),R.string.val_opcion, Toast.LENGTH_LONG).show();
                    return;
                }
                //Llegado a este punto habríamos validado la información
                //Accedemos a la BD en modo escritura
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                //Utilizamos un objeto del tipo ContenValues para almacenar pares clave-valor
                ContentValues values = new ContentValues();
                values.put(EstructuraBD.COLUMN_NAME_2, fecha);
                values.put(EstructuraBD.COLUMN_NAME_3, nombre);
                values.put(EstructuraBD.COLUMN_NAME_4, edad);
                values.put(EstructuraBD.COLUMN_NAME_5, genero);
                values.put(EstructuraBD.COLUMN_NAME_6, privacidad);
                values.put(EstructuraBD.COLUMN_NAME_7, opcion);

                // Insertamos la nueva fila devolviendo la clave primaria
                long newRowId = db.insert(EstructuraBD.TABLE_NAME, null, values);

                if(newRowId > 0) {
                    Toast.makeText(getApplicationContext(), R.string.men_insert, Toast.LENGTH_SHORT).show();
                    //Volvemos a la página de inicio
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.men_no_insert, Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    //****************************************************************************************//
    //                            M E N Ú   T O O L B A R                                     //
    //****************************************************************************************//
    @Override public boolean onCreateOptionsMenu(Menu mimenu){

        getMenuInflater().inflate(R.menu.menu, mimenu);

        return true;

    }

    @Override public boolean onOptionsItemSelected(MenuItem opcion_menu){


        int id = opcion_menu.getItemId();

        if( id==R.id.inicio){

            ver_pagina_principal(null);

            return true;
        }

        if( id==R.id.ayuda ){

            ver_ayuda(null);

            return true;
        }
        if( id==R.id.ver){

            ver_valoraciones(null);
            return true;
        }
        if( id==R.id.info ){

            ver_info(null);

            return true;
        }
        if( id==R.id.salir ){

            /*Finaliza la actividad actual así como todas las actividades que partan de ella.
              A diferencia de finish que sólo cerraría la actividad actual*/
            finishAffinity();
        }
        return super.onOptionsItemSelected(opcion_menu);
    }


    public void ver_ayuda(View view){

        Intent i = new Intent(this, Ayuda.class);

        startActivity(i);

    }

    public void ver_info(View view){

        Intent i = new Intent(this, Informacion.class);

        startActivity(i);

    }

    public void ver_valoraciones(View view){

        Intent i = new Intent(this, VerValoraciones.class);

        startActivity(i);
    }

    public void ver_pagina_principal(View view){

        Intent i = new Intent(this, MainActivity.class);

        startActivity(i);
    }
}
