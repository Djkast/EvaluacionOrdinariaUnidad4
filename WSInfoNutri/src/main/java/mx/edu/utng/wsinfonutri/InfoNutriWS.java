package mx.edu.utng.wsinfonutri;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Kast on 31/03/2017.
 */

public class InfoNutriWS extends AppCompatActivity implements View.OnClickListener {
    private EditText etContenido;
    private EditText etProteinas;
    private EditText etLipidos;
    private EditText etGrasas;
    private EditText etColesterol;
    private EditText etCarbohidratos;
    private EditText etAzucares;
    private EditText etFibra;
    private EditText etSodio;
    private EditText etCalcio;
    private Button btGuardar;
    private Button btListar;
    private InfoNutri infoNutri = null;
    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.178:8087/WSInfoNutri/services/InfoNutriWS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nutri_ws);
        components();
    }

    private void components() {
        etContenido = (EditText) findViewById(R.id.et_contenido);
        etProteinas = (EditText) findViewById(R.id.et_proteinas);
        etLipidos = (EditText) findViewById(R.id.et_lipidos);
        etGrasas = (EditText) findViewById(R.id.et_grasas);
        etColesterol = (EditText) findViewById(R.id.et_colesterol);
        etCarbohidratos = (EditText) findViewById(R.id.et_carbohidratos);
        etAzucares = (EditText) findViewById(R.id.et_azucares);
        etFibra = (EditText) findViewById(R.id.et_fibra);
        etSodio = (EditText) findViewById(R.id.et_sodio);
        etCalcio = (EditText) findViewById(R.id.et_calcio);
        btGuardar = (Button) findViewById(R.id.bt_save);
        btListar = (Button) findViewById(R.id.bt_list);
        btGuardar.setOnClickListener(this);
        btListar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        String contenido = etContenido.getText().toString();
        String proteinas = etProteinas.getText().toString();
        String lipidos = etLipidos.getText().toString();
        String grasas = etGrasas.getText().toString();
        String colesterol = etColesterol.getText().toString();
        String carbohidratos = etCarbohidratos.getText().toString();
        String azucares = etAzucares.getText().toString();
        String fibra = etFibra.getText().toString();
        String sodio = etSodio.getText().toString();
        String calcio = etCalcio.getText().toString();



        if (v.getId() == btGuardar.getId()) {
            if (contenido != null && !contenido.isEmpty() && contenido != null &&
                    !proteinas.isEmpty() && proteinas != null &&
                    lipidos != null && !lipidos.isEmpty() &&
                    grasas != null && !grasas.isEmpty()&&
                    colesterol!= null && !colesterol.isEmpty() &&
                    carbohidratos != null && !carbohidratos.isEmpty() &&
                    azucares != null && !azucares.isEmpty() &&
                    fibra != null && !fibra.isEmpty() &&
                    sodio != null && !sodio.isEmpty() && sodio != null &&
                    calcio != null && !calcio.isEmpty()&& calcio != null) {
                try {
                    if (getIntent().getExtras().getString("accion")
                            .equals("modificar")) {
                        updateInfoNutri tarea = new updateInfoNutri();
                        tarea.execute();
                        //cleanEditTex();
                    }

                } catch (Exception e) {
                    //Cuando no se haya mandado una accion por defecto es insertar.
                    InsertInfoNutri tarea = new InsertInfoNutri();
                    tarea.execute();
                }
            } else {
                Toast.makeText(this, "llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }
        if (btListar.getId() == v.getId()) {
            startActivity(new Intent(InfoNutriWS.this, ListInfoNutri.class));
        }
    }//fin conClick

    private void cleanEditTex() {
        etContenido.setText("");
        etProteinas.setText("");
        etLipidos.setText("");
        etGrasas.setText("");
        etColesterol.setText("");
        etCarbohidratos.setText("");
        etAzucares.setText("");
        etFibra.setText("");
        etSodio.setText("");
        etCalcio.setText("");

    }


    private class InsertInfoNutri extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addInfoNutri";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request =
                    new SoapObject(NAMESPACE, METHOD_NAME);

            infoNutri = new InfoNutri();
            infoNutri.setProperty(0, 0);
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("infoNutri");
            info.setValue(infoNutri);
            info.setType(infoNutri.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "InfoNutri", InfoNutri.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                cleanEditTex();
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//fin tarea insertar

    private class updateInfoNutri extends
            AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "editInfoNutri";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            infoNutri = new InfoNutri();
            infoNutri.setProperty(0, getIntent().getExtras().getString("valor0"));
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("infoNutri");
            info.setValue(infoNutri);
            info.setType(infoNutri.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "InfoNutri", infoNutri.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive resultado_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }

            return result;

        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void obtenerDatos() {
        infoNutri.setProperty(1, etContenido.getText().toString());
        infoNutri.setProperty(2, Integer.parseInt(etProteinas.getText().toString()));
        infoNutri.setProperty(3, Integer.parseInt(etLipidos.getText().toString()));
        infoNutri.setProperty(4, Integer.parseInt(etGrasas.getText().toString()));
        infoNutri.setProperty(5, Integer.parseInt(etColesterol.getText().toString()));
        infoNutri.setProperty(6, Integer.parseInt(etCarbohidratos.getText().toString()));
        infoNutri.setProperty(7, Integer.parseInt(etAzucares.getText().toString()));
        infoNutri.setProperty(8, Integer.parseInt(etFibra.getText().toString()));
        infoNutri.setProperty(9, etSodio.getText().toString());
        infoNutri.setProperty(10, etCalcio.getText().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {

            etContenido.setText(datosRegreso.getString("valor1"));
            etProteinas.setText(datosRegreso.getString("valor2"));
            etLipidos.setText(datosRegreso.getString("valor3"));
            etGrasas.setText(datosRegreso.getString("valor4"));
            etColesterol.setText(datosRegreso.getString("valor5"));
            etCarbohidratos.setText(datosRegreso.getString("valor6"));
            etAzucares.setText(datosRegreso.getString("valor7"));
            etFibra.setText(datosRegreso.getString("valor8"));
            etSodio.setText(datosRegreso.getString("valor9"));
            etCalcio.setText(datosRegreso.getString("valor10"));
        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }

    }

}
