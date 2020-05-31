package com.example.newgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button empezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Evento clic en el botón Empezar
        empezar = findViewById(R.id.btEmpezar);
        empezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), DatosPersonales.class);
                startActivity(i);
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

        //Debemos de comprobar si existen registros en la BD
        //Creamos una instancia para poder acceder a la BD
        final HelperBD dbHelper = new HelperBD(getApplicationContext());

        //Nos conectamos a la BD en modo lectura
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {

                EstructuraBD.COLUMN_NAME_1
        };

        Cursor cursor = db.query(
                EstructuraBD.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount() > 0 ){

            Intent i = new Intent(this, VerValoraciones.class);

            startActivity(i);

        }else {

            Toast.makeText(getApplicationContext(),R.string.men_no_registros,Toast.LENGTH_SHORT).show();
        }

    }

}//Fin de la clase
