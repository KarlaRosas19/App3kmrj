package com.test.marianarosas.app3kmrj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.tallercm.appcm4.model.Anime;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;





public class MainActivity extends AppCompatActivity {

    //CREACION DE VARIABLES
    private ListView list2;
    private TextView tv;

    ByteArrayInputStream inputStream;
    ArrayList<Cursos> datos = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list2 = (ListView) findViewById(R.id.listview2);

        if (savedInstanceState == null) {
            new ConexionHttp().execute("");
        }


    }
   //SERVICIO REST
    public class ConexionHttp extends AsyncTask<String, Float, String> {

        boolean sinError = true;

        @Override
        protected String doInBackground(String... strings) {
            try {
                StringBuffer fileData = new StringBuffer(4096);

                URL sourceURL = new URL("https://serverbpw.com/cm/2019-2/cursos.php");

                BufferedReader in = new BufferedReader(new InputStreamReader(sourceURL.openStream()));

                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    fileData.append(inputLine);
                }

                in.close();

                inputStream = new ByteArrayInputStream(fileData.toString().getBytes());

                DocumentBuilderFactory dbFabrica = DocumentBuilderFactory.newInstance();

                DocumentBuilder dBuilder = dbFabrica.newDocumentBuilder();
                Document doc = dBuilder.parse(inputStream);

                Element elemento = doc.getDocumentElement();
                elemento.normalize();

                NodeList nList = doc.getElementsByTagName("item");

                for (int i = 0; i < nList.getLength(); i++) {

                    Node nodo = nList.item(i);
                    if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                        Element elemento2 = (Element) nodo;
                        Log.d("DATO", "id: " + obtenValor("id", elemento2));
                        Log.d("DATO", "nombre: " + obtenValor("nombre", elemento2));
                        Log.d("DATO", "sede: " + obtenValor("sede", elemento2));
                        Log.d("DATO", "fechainic: " + obtenValor("fechainic", elemento2));
                        Log.d("DATO", "fechafin: " + obtenValor("fechafin", elemento2));
                        Cursos animeTmp = new Cursos(Long.valueOf(obtenValor("id", elemento2)),
                                obtenValor("nombre", elemento2),
                                obtenValor("sede", elemento2),
                                obtenValor("fechainic", elemento2),
                                obtenValor("fechafin", elemento2));
                        datos.add(animeTmp);



                    }



                }


            } catch (Exception e) {
                e.printStackTrace();
                sinError = false;
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //     pbSpinner1.setVisibility(View.GONE);

            if (sinError) {




                //MOSTRAR LISTA DE SERVICIO REST
                 CustomListview customListview=new CustomListview(MainActivity.this, datos);
                 list2.setAdapter(customListview);

                 //ID
                list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Cursos listaAlumnos = (Cursos) list2.getItemAtPosition(position);
                        Toast.makeText(getApplication(),"ID"+(position+1),Toast.LENGTH_SHORT).show();
                        Long num=datos.get(position).getId();

                 //CLICK PARA IR A UNA SIGUIENTE ACTIVITY
                        Intent i = new Intent(MainActivity.this, Main2Activity.class);
                        i.putExtra("pos", position);  //ENVIAR ARRAY CON EL CONTENIDO DE LA LISTA
                        i.putExtra("num", num);  //ENVIAR ARRAY CON EL CONTENIDO DE LA LISTA
                        startActivity(i);                  //INICIAR ACTIVITY 2


                    }
                });


                for (int i = 0; i < datos.size(); i++) {

//                    Toast.makeText(MainActivity.this, datos.get(i).getNombre(), Toast.LENGTH_SHORT).show();
                }
            } else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Aviso")
                        .setMessage("Servicio no disponible en estos momentos.")
                        .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new ConexionHttp().execute("");
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        new ConexionHttp().execute("");
                    }
                }).setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                        .show();

            }

        }

    }

    private static String obtenValor(String tag, Element elemento) {
        NodeList listaNodos = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node nodo = listaNodos.item(0);
        return nodo.getNodeValue();
    }


}