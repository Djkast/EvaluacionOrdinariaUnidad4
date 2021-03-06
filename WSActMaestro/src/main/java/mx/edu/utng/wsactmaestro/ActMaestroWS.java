package mx.edu.utng.wsactmaestro;

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

public class ActMaestroWS extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etDescription;
    private EditText etActiv;
    private Button btGuardar;
    private Button btListar;
    private ActMaestro actMaestro = null;
    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.178:8087/WSMaestro/services/ActividadMaestoWS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_maestro_ws);
        components();
    }

    private void components() {
        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);
        etActiv = (EditText) findViewById(R.id.et_active);
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
        String name = etName.getText().toString();
        String description = etDescription.getText().toString();
        String activ = etActiv.getText().toString();


        if (v.getId() == btGuardar.getId()) {
            if (name != null && !name.isEmpty() &&
                    name != null && !description.isEmpty() &&
                    description != null && !description.isEmpty() &&
                    activ != null && !description.isEmpty()) {
                try {
                    if (getIntent().getExtras().getString("accion")
                            .equals("modificar")) {
                        updateActMaestro tarea = new updateActMaestro();
                        tarea.execute();
                        cleanEditTex();
                    }

                } catch (Exception e) {
                    //Cuando no se haya mandado una accion por defecto es insertar.
                    InsertActMaestro tarea = new InsertActMaestro();
                    tarea.execute();
                }
            } else {
                Toast.makeText(this, "llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }
        if (btListar.getId() == v.getId()) {
            startActivity(new Intent(ActMaestroWS.this, ListActMaestro.class));
        }
    }//fin conClick

    private void cleanEditTex() {
        etName.setText("");
        etDescription.setText("");
        etActiv.setText("");
    }


    private class InsertActMaestro extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addActMaestro";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request =
                    new SoapObject(NAMESPACE, METHOD_NAME);

            actMaestro = new ActMaestro();
            actMaestro.setProperty(0, 0);
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("actMaestro");
            info.setValue(actMaestro);
            info.setType(actMaestro.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "ActMaesto", ActMaestro.class);

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

    private class updateActMaestro extends
            AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "editActMaestro";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            actMaestro = new ActMaestro();
            actMaestro.setProperty(0, getIntent().getExtras().getString("valor0"));
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("actMaestro");
            info.setValue(actMaestro);
            info.setType(actMaestro.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "ActMaestro", actMaestro.getClass());

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
        actMaestro.setProperty(1, etName.getText().toString());
        actMaestro.setProperty(2, etDescription.getText().toString());
        actMaestro.setProperty(3, Integer.parseInt(etActiv.getText().toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {

            etName.setText(datosRegreso.getString("valor1"));
            etDescription.setText(datosRegreso.getString("valor2"));
            etActiv.setText(datosRegreso.getString("valor3"));
        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());
        }

    }

}