package com.example.sqlite;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Entity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnGuardar, btnConsultar1, btnConsultar2, btnBorrar, btnEditar;
    private EditText et1, et2, et3;
    private TextView tvResultado;

    boolean aviso1 = false;
    boolean aviso2 = false;
    boolean aviso3 = false;
    boolean estadoGuarda = false;
    int resultadoInsert = 0;

  /*  modal ventanas = new modal();
    AdminSQLiteOpenHelper conexion = new AdminSQLiteOpenHelper(this);
    Dto datos = new Dto();*/

  MantenimientoMySQL manto = new MantenimientoMySQL();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea cerrar esta actividad?")
                    .setNegativeButton(android.R.string.cancel,null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {

                            finishAffinity();
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite-2019");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.colorAccent));
        toolbar.setTitle("Laura Santos");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().length() == 0) {

                    et1.setError("NO PUEDE QUEDAR VACIO");
                    aviso1 = false;
                } else {
                    aviso1 = true;

                }
                if (et2.getText().toString().length() == 0) {

                    et2.setError("NO PUEDE QUEDAR VACIO");
                    aviso2 = false;
                } else {
                    aviso2 = true;
                }
                if (et3.getText().toString().length() == 0) {

                    et3.setError("NO PUEDE QUEDAR VACIO");
                    aviso3 = false;
                } else {
                    aviso3 = true;
                }
                if (aviso1 && aviso2 && aviso3) {

                    String codigo = et1.getText().toString();
                    String descripcion = et2.getText().toString();
                    String precio = et3.getText().toString();
                    manto.guardar(MainActivity.this,codigo,descripcion,precio);

                   /* if (estadoGuarda){
                        Toast.makeText(MainActivity.this,"Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }else {
                        Toast.makeText(getApplicationContext(),"Error. Ya existe un registro\n" + " Código: "+et1.getText().toString(),Toast.LENGTH_LONG).show();
                        limpiarDatos();
                    }*/
                }
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnConsultar1 = (Button) findViewById(R.id.btnConsultar1);
        btnConsultar2 = (Button) findViewById(R.id.btnConsultar2);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        tvResultado = (TextView) findViewById(R.id.tvResultado);

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";

      try{  Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            codigo = bundle.getString("codigo");
            senal = bundle.getString("senal");
            descripcion = bundle.getString("descripcion");
            precio = bundle.getString("precio");
            if(senal.equals("1")){
                et1.setText(codigo);
                et2.setText(descripcion);
                et3.setText(precio);
            }
        }
      }catch (Exception e){

      }
    }


    private void confirmacion(){
        String mensaje ="¿Realmente desea cerrar esta pantalla?";
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {
                MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogo, int id) {

            }
        });
        dialogo.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_limpiar) {
            et1.setText(null);
            et2.setText(null);
            et3.setText(null);
            return true;
        }else if (id == R.id.action_ListaArticulos) {
            Intent spinnerActivity = new Intent(MainActivity.this,ConsultaSpinner.class);
            startActivity(spinnerActivity);
            return true;
        }else if(id == R.id.action_ListaArticulos1){
            Intent listViewActivity = new Intent(MainActivity.this,ListViewArticulos.class);
            startActivity(listViewActivity);
            return true;
        }else if (id == R.id.acercade) {
        Intent Acercade = new Intent(MainActivity.this, AcercaDe.class);
        startActivity(Acercade);
        return true;
    }

        return super.onOptionsItemSelected(item);
    }*/

/*

    public void alta(View view) {
        if (et1.getText().toString().length() == 0) {

            et1.setError("NO PUEDE QUEDAR VACIO");
            aviso1 = false;
        } else {
            aviso1 = true;

        }
        if (et2.getText().toString().length() == 0) {

            et2.setError("NO PUEDE QUEDAR VACIO");
            aviso2 = false;
        } else {
            aviso2 = true;
        }
        if (et3.getText().toString().length() == 0) {

            et3.setError("NO PUEDE QUEDAR VACIO");
            aviso3 = false;
        } else {
            aviso3 = true;
        }
        if (aviso1 && aviso2 && aviso3) {

            String codigo = et1.getText().toString();
            String descripcion = et2.getText().toString();
            String precio = et3.getText().toString();
            estadoGuarda = manto.guardar1(MainActivity.this,codigo,descripcion,precio);

            if (estadoGuarda){
                Toast.makeText(this,"Registro agregado satisfactoriamente!", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }else {
                Toast.makeText(getApplicationContext(),"Error. Ya existe un registro\n" + " Código: "+et1.getText().toString(),Toast.LENGTH_LONG).show();
                limpiarDatos();
            }
        }

    }*/

/*

    public void mensaje(String mensaje){
        Toast.makeText(this,""+mensaje,Toast.LENGTH_SHORT).show();
    }

    public void limpiarDatos(){
        et1.setText(null);
        et2.setText(null);
        et3.setText(null);
        et1.requestFocus();
    }
    */



}