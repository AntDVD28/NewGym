package com.example.newgym;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class VerValoracion extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTituloValoracion;
    private EditText etFecha, etNombre, etEdad;
    private Spinner spGenero;
    private CheckBox cbPrivacidad;
    private RadioGroup rgOpciones;
    private RadioButton rbRespuesta1, rbRespuesta2, rbRespuesta3, rbRespuesta4, rbRespuesta5;
    private Button btModificar, btEliminar;

    //Variables donde guardaremos los valores actualizados por el usuario
    private String fecha, nombre, edad, genero;
    private int privacidad, opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_valoracion);

        //Toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Componentes
        tvTituloValoracion = findViewById(R.id.tvTituloValoracion);
        etFecha = findViewById(R.id.etFecha);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        spGenero = findViewById(R.id.spGenero);
        cbPrivacidad = findViewById(R.id.cbPrivacidad);
        rgOpciones = findViewById(R.id.rgOpciones);
        rbRespuesta1 = findViewById(R.id.rbRespuesta1);
        rbRespuesta2 = findViewById(R.id.rbRespuesta2);
        rbRespuesta3 = findViewById(R.id.rbRespuesta3);
        rbRespuesta4 = findViewById(R.id.rbRespuesta4);
        rbRespuesta5 = findViewById(R.id.rbRespuesta5);
        btModificar = findViewById(R.id.btModificar);
        btEliminar = findViewById(R.id.btEliminar);


        //Rescato la información enviada desde la activity VerValoraciones
        Bundle datos = getIntent().getExtras();
        final String id = datos.getString("id");
        //Toast.makeText(getApplicationContext(), "Id:"+id, Toast.LENGTH_LONG).show();

        //Agregamos al título el id de la valoración
        tvTituloValoracion.append(" número "+id);

        //****************************************************************************************//
        //                                    S P I N N E R                                       //
        //****************************************************************************************//
        //Creamos el adaptador, el contenido del mismo lo cargamos de un recurso
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,R.array.combo_generos,android.R.layout.simple_spinner_item);
        //Asociamos el adaptador a la vista
        spGenero.setAdapter(adaptador);

        spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                genero = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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

        //****************************************************************************************//
        //                      R E S C A T A R    I N F O    B D                                 //
        //****************************************************************************************//
        //Creamos una instancia para poder acceder a la BD
        final HelperBD dbHelper = new HelperBD(this);

        //Nos conectamos a la BD en modo lectura
        final SQLiteDatabase db = dbHelper.getReadableDatabase();

        //Definimoa las columnas a devolver en la consulta
        String[] projection = {
                EstructuraBD.COLUMN_NAME_1,
                EstructuraBD.COLUMN_NAME_2,
                EstructuraBD.COLUMN_NAME_3,
                EstructuraBD.COLUMN_NAME_4,
                EstructuraBD.COLUMN_NAME_5,
                EstructuraBD.COLUMN_NAME_6,
                EstructuraBD.COLUMN_NAME_7
        };
        // Filter results WHERE "title" = 'My Title'
        String selection = EstructuraBD.COLUMN_NAME_1 + " = ?";
        String[] selectionArgs = {id} ;

        final Cursor cursor = db.query(
                EstructuraBD.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );
        //Tenemos que ejecutar esta instrucción para situarnos en el primer registro
        cursor.moveToNext();

        //Guardo el resultado en un objeto de tipo valoración
        final Valoracion val = new Valoracion(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5),
                cursor.getInt(6)
        );
        //****************************************************************************************//
        //                 R E L L E N A R    V I E W S    I N T E R F A Z                        //
        //****************************************************************************************//
        //EditTexts
        etFecha.setText(val.getFecha());
        etNombre.setText(val.getNombre());
        etEdad.setText(val.getEdad());

        //Spinner
        if(val.getGenero().equals("Hombre")){
            spGenero.setSelection(1);
            genero="Hombre";
        }
        if(val.getGenero().equals("Mujer")){
            spGenero.setSelection(2);
            genero="Mujer";
        }
        //Checkbox
        if(val.getPrivacidad() == 1){
            cbPrivacidad.setChecked(true);
            privacidad=1;
        }
        //Radiobuttons
        if(val.getValoracion() == 1){
            rbRespuesta1.setChecked(true);
            opcion=1;
        }else if(val.getValoracion() == 2){
            rbRespuesta2.setChecked(true);
            opcion=2;
        }else if(val.getValoracion() == 3){
            rbRespuesta3.setChecked(true);
            opcion=3;
        }else if(val.getValoracion() == 4){
            rbRespuesta4.setChecked(true);
            opcion=4;
        }else {
            rbRespuesta5.setChecked(true);
            opcion=5;
        }


        //****************************************************************************************//
        //                           E V E N T O    M O D I F I C A R                             //
        //****************************************************************************************//
        btModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mostramos una alerta para confirmar la modificación
                AlertDialog.Builder alerta = new AlertDialog.Builder(VerValoracion.this);
                alerta.setMessage("¿Desea modificar la valoración "+id+"?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Rescato los valores de los editText y checkbox. Los valores de los componentes spinner y radiogroup ya los recogimos anteriormente
                                fecha = etFecha.getText().toString();
                                nombre = etNombre.getText().toString();
                                edad = etEdad.getText().toString();
                                if(cbPrivacidad.isChecked()){
                                    privacidad = 1;
                                }else {
                                    privacidad = 0;
                                }
                                //Guardo todos los valores en un objeto de tipo Valoracion
                                Valoracion val = new Valoracion(
                                        cursor.getInt(0),
                                        fecha,
                                        nombre,
                                        edad,
                                        genero,
                                        privacidad,
                                        opcion
                                );

                                //Nos conectamos a la BD en modo escritura
                                SQLiteDatabase db = dbHelper.getWritableDatabase();

                                //Utilizamos un objeto del tipo ContenValues para almacenar pares clave-valor
                                ContentValues values = new ContentValues();
                                values.put(EstructuraBD.COLUMN_NAME_1, val.getId());
                                values.put(EstructuraBD.COLUMN_NAME_2, val.getFecha());
                                values.put(EstructuraBD.COLUMN_NAME_3, val.getNombre());
                                values.put(EstructuraBD.COLUMN_NAME_4, val.getEdad());
                                values.put(EstructuraBD.COLUMN_NAME_5, val.getGenero());
                                values.put(EstructuraBD.COLUMN_NAME_6, val.getPrivacidad());
                                values.put(EstructuraBD.COLUMN_NAME_7, val.getValoracion());

                                //Indicamos la condición
                                String selection = EstructuraBD.COLUMN_NAME_1 + " LIKE ?";
                                //Indicamos argumentos de la condición
                                String[] selectionArgs = { val.getId().toString() };

                                //Procedemos a actualizar, devolvemos el número de registros modificados
                                int count = db.update(
                                        EstructuraBD.TABLE_NAME,
                                        values,
                                        selection,
                                        selectionArgs);
                                if(count==1){
                                    //Mostramos mensaje
                                    Toast.makeText(getApplicationContext(),R.string.men_update,Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getApplicationContext(), R.string.men_no_update, Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Modificar Valoración");
                titulo.show();
            }
        });
        //****************************************************************************************//
        //                           E V E N T O    E L I M I N A R                               //
        //****************************************************************************************//
        btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Mostramos una alerta para confirmar la modificación
                AlertDialog.Builder alerta = new AlertDialog.Builder(VerValoracion.this);
                alerta.setMessage("¿Desea eliminar la valoración "+id+"?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Indicamos condición
                                String selection = EstructuraBD.COLUMN_NAME_1 + " LIKE ?";
                                //Indicamos argumentos de la condición
                                String[] selectionArgs = { val.getId().toString() };
                                int deletedRows = db.delete(EstructuraBD.TABLE_NAME, selection, selectionArgs);

                                //Si se borró el registro mostramos un mensaje y volvemos a la activity VerValoraciones
                                if(deletedRows==1){
                                    Toast.makeText(getApplicationContext(),R.string.men_delete,Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), VerValoraciones.class);
                                    startActivity(i);
                                }else {
                                    Toast.makeText(getApplicationContext(),R.string.men_no_delete,Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Eliminar Valoración");
                titulo.show();
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
