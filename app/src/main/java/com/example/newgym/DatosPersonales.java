package com.example.newgym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DatosPersonales extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText etFecha, etNombre, etEdad;
    private Spinner spGenero;
    private CheckBox cbPrivacidad;
    private Button btValorar;

    //Variables donde guardaremos los valores introducidos/seleccionados por el usuario
    private String fecha, nombre, edad, genero;
    private int privacidad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        //Componentes
        etFecha = findViewById(R.id.etFecha);
        etNombre = findViewById(R.id.etNombre);
        etEdad = findViewById(R.id.etEdad);
        spGenero = findViewById(R.id.spGenero);
        cbPrivacidad = findViewById(R.id.cbPrivacidad);
        btValorar = findViewById(R.id.btValorar);

        //Toolbar
        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Obtenemos la fecha del sistema
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        fecha = dateFormat.format(date);

        //Rellenamos el editText de la fecha con la fecha actual y lo ponemos no editable
        etFecha.setText(fecha);
        etFecha.setEnabled(false);

        //Solicitramos el foco en el editText del Nombre
        etNombre.requestFocus();

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


        //Evento hacer clic en el botón Valorar. Debemos de comprobar que todos los campos están rellenos y que además son válidos
        btValorar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Validación del nombre
                nombre = etNombre.getText().toString();
                if(nombre.length() == 0){
                    Toast.makeText(getApplicationContext(), R.string.val_nombre,Toast.LENGTH_LONG).show();
                    return;
                }

                //Validación de la edad
                edad = etEdad.getText().toString();

                if (edad.length() == 0){

                    Toast.makeText(getApplicationContext(), R.string.val_edad, Toast.LENGTH_LONG).show();
                    return;

                }else{

                    int edad_i = Integer.parseInt(edad);

                    if(edad_i < 7) {

                        Toast.makeText(getApplicationContext(), R.string.val_joven, Toast.LENGTH_LONG).show();
                        return;

                    }else if (edad_i > 146) {

                        Toast.makeText(getApplicationContext(), R.string.val_mayor, Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //Validación del género
                if(genero.equals("- Seleccionar -")) {

                    Toast.makeText(getApplicationContext(), R.string.val_genero, Toast.LENGTH_LONG).show();
                    return;
                }

                //Validación de la privacidad
                if(!cbPrivacidad.isChecked()){

                    Toast.makeText(getApplicationContext(), R.string.val_privacidad, Toast.LENGTH_LONG).show();
                    return;
                }else {
                    privacidad = 1;
                }

                //Llegado a este punto habremos validado todos los datos introducidos por el usuario
                //Enviamos todas la información recogida a la siguiente activity
                Intent i = new Intent(getApplicationContext(), Entrenamiento.class);

                i.putExtra("fecha", fecha);
                i.putExtra("nombre", nombre);
                i.putExtra("edad", edad);
                i.putExtra("genero", genero);
                i.putExtra("privacidad", privacidad);

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
