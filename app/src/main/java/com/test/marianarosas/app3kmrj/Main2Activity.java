package com.test.marianarosas.app3kmrj;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Main2Activity extends AppCompatActivity {

    private ListView list2;
    private String idd="";
    private TextView textView5;
    ByteArrayInputStream inputStream;

    ArrayList<CursoDesc> datos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //DEFINIR OBJETOS Y RECIBIR VARIABLES DE LA ACT 1
        list2 = (ListView) findViewById(R.id.listview2);
        Integer pos =(Integer) getIntent().getSerializableExtra("pos");
        Long num =(Long) getIntent().getSerializableExtra("num");

        //VARIABLE PARA CREAR LA URL A LA QUE SE VA A DIRIGIR
        idd = String.valueOf(num);

        //CONSULTA
        if (savedInstanceState == null) {
            new ConexionHttp().execute("");
        }




    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class ConexionHttp extends AsyncTask<String, Float, String> {

    boolean sinError = true;

    @Override
    protected String doInBackground(String... strings) {
        try {
            StringBuffer fileData = new StringBuffer(4096);

           // URL sourceURL = new URL("https://serverbpw.com/cm/2019-2/curso_desc.php?id=6656");

            String url="https://serverbpw.com/cm/2019-2/curso_desc.php?id="+idd;
            URL sourceURL = new URL(url);

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
                    Log.d("DATO2", "id: " + obtenValor("id", elemento2));
                    Log.d("DATO2", "nombre: " + obtenValor("nombre", elemento2));
                    Log.d("DATO2", "descripcion: " + obtenValor("descripcion", elemento2));
               /*     Log.d("DATO", "fechainic: " + obtenValor("fechainic", elemento2));
                    Log.d("DATO", "fechafin: " + obtenValor("fechafin", elemento2));*/
                    CursoDesc animeTmp = new CursoDesc(Long.valueOf(obtenValor("id", elemento2)),
                            obtenValor("nombre", elemento2),
                            obtenValor("descripcion", elemento2));

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
        CustomListview2 customListview2=new CustomListview2(Main2Activity.this, datos);
        list2.setAdapter(customListview2);

        if (sinError) {

           // textView5=(TextView)datos.get(1).getNombre();


            //MOSTRAR LISTA DE SERVICIO REST
/*            CustomListview customListview=new CustomListview(Main2Activity.this, datos);
            list2.setAdapter(customListview);*/

            //ID
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    CursoDesc listaAlumnos = (CursoDesc) list2.getItemAtPosition(position);
                    Toast.makeText(getApplication(),"ID"+(position+1),Toast.LENGTH_SHORT).show();

                    //CLICK PARA IR A UNA SIGUIENTE ACTIVITY
                //    Intent i = new Intent(MainActivity.this, Main2Activity.class);
                //    i.putExtra("pos", position);  //ENVIAR ARRAY CON EL CONTENIDO DE LA LISTA
                 //   startActivity(i);                  //INICIAR ACTIVITY 2


                }
            }
            );


            for (int i = 0; i < datos.size(); i++) {

                Toast.makeText(Main2Activity.this, datos.get(i).getNombre(), Toast.LENGTH_SHORT).show();
            }
        } else {
            new AlertDialog.Builder(Main2Activity.this)
                    .setTitle("Aviso")
                    .setMessage("Servicio no disponible en estos momentos.")
                    .setPositiveButton("Reintentar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new Main2Activity.ConexionHttp().execute("");
                        }
                    }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    new Main2Activity.ConexionHttp().execute("");
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
