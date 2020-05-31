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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VerValoraciones extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvListadoValoraciones;

    //Lista de objetos de tipo Valoracion que utilizaré para volcar el resultado de la consulta a la BD
    private ArrayList<Valoracion> listaDeValoraciones;
    //Lista de Strings que mostraré en el ListView
    private ArrayList<String> listaDeCadenas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_valoraciones);

        //Toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Creamos una instancia para poder acceder a la BD
        final HelperBD dbHelper = new HelperBD(this);

        //Nos conectamos a la BD en modo lectura
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Definimoa las columnas a devolver en la consulta
        String[] projection = {
                //BaseColumns._ID,
                EstructuraBD.COLUMN_NAME_1,
                EstructuraBD.COLUMN_NAME_2,
                EstructuraBD.COLUMN_NAME_3,
                EstructuraBD.COLUMN_NAME_7
        };

        // Filter results WHERE "title" = 'My Title'
        //String selection = FeedEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        //String sortOrder =
        //        FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";


        Cursor cursor = db.query(
                EstructuraBD.TABLE_NAME,   // The table to query
                projection,                // The array of columns to return (pass null to get all)
                //selection,               // The columns for the WHERE clause
                null,
                //selectionArgs,           // The values for the WHERE clause
                null,
                null,             // don't group the rows
                null,              // don't filter by row groups
                //sortOrder               // The sort order
                null
        );

        //Guardo el resultado de la consulta en una lista de objetos del tipo Valoracion
        Valoracion v = null;
        listaDeValoraciones = new ArrayList<Valoracion>();
        while (cursor.moveToNext()) {
            v = new Valoracion();
            v.setId(cursor.getInt(0));
            v.setFecha(cursor.getString(1));
            v.setNombre(cursor.getString(2));
            v.setValoracion(cursor.getInt(3));

            //Agrego el objeto de tipo noticia a la lista
            listaDeValoraciones.add(v);
        }

        //Vuelco la lista de objetos de tipo valoracion en una lista de cadenas
        listaDeCadenas = new ArrayList<String>();
        for(int i=0; i<listaDeValoraciones.size();i++){
            listaDeCadenas.add(
                    listaDeValoraciones.get(i).getId()+" - "
                            +listaDeValoraciones.get(i).getFecha()+ " - "
                            +listaDeValoraciones.get(i).getNombre()+ " - "
                            +listaDeValoraciones.get(i).getStringValoracion()
            );
        }

        //Utilizamos un adaptador para pasar la lista de cadenas obtenida al ListView
        final ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaDeCadenas);
        lvListadoValoraciones = (ListView) findViewById(R.id.lvListadoValoraciones);
        lvListadoValoraciones.setAdapter(adaptador);

        //Evento hacer clic sobre una fila del listView
        lvListadoValoraciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Envío el id de la noticia seleccionada a la activity FichaNoticia
                Intent i = new Intent(getApplicationContext(), VerValoracion.class);
                i.putExtra("id", listaDeValoraciones.get(position).getId().toString());
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
