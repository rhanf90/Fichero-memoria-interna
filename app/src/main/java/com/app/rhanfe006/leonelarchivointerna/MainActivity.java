package com.app.rhanfe006.leonelarchivointerna;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    //se declaran las variables a utilizar
    private EditText txtArchivo;
    private Button btnGuardar, btnAbrir;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se enlaza con la vista

        txtArchivo = (EditText) findViewById(R.id.txtArchivo);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnAbrir = (Button) findViewById(R.id.btnAbrir);
        btnGuardar.setOnClickListener(this);
        btnAbrir.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(btnGuardar)){
            String str = txtArchivo.getText().toString();

            //esta clase es la que permite gravar el texto en un archivo
            FileOutputStream fout = null;

            try {
                //este metodo es el que escribe y abre un archivo con un nombre especifico
                fout = openFileOutput("txtArchivo.txt", MODE_WORLD_READABLE);

                OutputStreamWriter ows = new OutputStreamWriter(fout);
                ows.write(str); //escribe el buffer de cadena de texto
                ows.flush(); //
                ows.close();// cierra el archivo de texto

            } catch (IOException e) {

                // TODO Auto-generated catch block

                e.printStackTrace();
            }

            Toast.makeText(getBaseContext(), "El mensaje se ha Almacenado!!", Toast.LENGTH_LONG).show();
            txtArchivo.setText("");
        }
        if (v.equals(btnAbrir)){

            try {

                //se lee el archivo de texto
                FileInputStream fin = openFileInput("txtArchivo.txt");
                InputStreamReader isr = new InputStreamReader(fin);

                char[] inputBuffer = new char[READ_BLOCK_SIZE];
                String str = "";

                int charRead;
                while ((charRead = isr.read(inputBuffer)) > 0) {


                    String strRead = String.copyValueOf(inputBuffer, 0, charRead);
                    str += strRead;
                    inputBuffer = new char[READ_BLOCK_SIZE];
                }

                //se muestra el texto leido en caja de texto

                txtArchivo.setText(str);
                isr.close();
                Toast.makeText(getBaseContext(), "El archivo a sido cargado", Toast.LENGTH_SHORT).show();
            } catch (IOException e){

                //TODO: handle eception
                e.printStackTrace();
            }


        }


    }
}