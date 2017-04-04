package mx.edu.utng.wsinfonutri;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Kast on 31/03/2017.
 */

public class ListInfoNutri extends ListActivity {


    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<InfoNutri> infoNutris = new ArrayList<InfoNutri>();
    private int idSeleccionado;
    private int posicionSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InfoNutris query = new InfoNutris();
        query.execute();
        registerForContextMenu(getListView());


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_modificar:
                InfoNutri infoNutri = infoNutris.get(posicionSeleccionado);
                Bundle bundleLibro = new Bundle();
                for (int i = 0; i < infoNutri.getPropertyCount(); i++) {
                    bundleLibro.putString("valor" + i, infoNutri.getProperty(i)
                            .toString());
                }
                bundleLibro.putString("accion", "modificar");
                Intent intent = new Intent(ListInfoNutri.this, InfoNutriWS.class);
                intent.putExtras(bundleLibro);
                startActivity(intent);

                return true;
            case R.id.item_eliminar:
                DeleteInfoNutri eliminar = new DeleteInfoNutri();
                eliminar.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_regresar:
                startActivity(new Intent(ListInfoNutri.this,InfoNutriWS.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position).toString());
        idSeleccionado = (Integer) infoNutris.get(info.position).getProperty(0);
        posicionSeleccionado = info.position;
        inflater.inflate(R.menu.menu_options, menu);
    }




    private class InfoNutris extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;
            final String METHOD_NAME = "getInfoNutris";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
            infoNutris.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(InfoNutriWS.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        InfoNutri infoNutri = new InfoNutri();
                        infoNutri.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                        infoNutri.setProperty(1, objSoap.getProperty("contenidoEnergetico").toString());
                        infoNutri.setProperty(2, Integer.parseInt(objSoap.getProperty("proteinas").toString()));
                        infoNutri.setProperty(3, Integer.parseInt(objSoap.getProperty("lipidos").toString()));
                        infoNutri.setProperty(4, Integer.parseInt(objSoap.getProperty("grasaSaturada").toString()));
                        infoNutri.setProperty(5, Integer.parseInt(objSoap.getProperty("colesterol").toString()));
                        infoNutri.setProperty(6, Integer.parseInt(objSoap.getProperty("carbohidratos").toString()));
                        infoNutri.setProperty(7, Integer.parseInt(objSoap.getProperty("azucares").toString()));
                        infoNutri.setProperty(8, Integer.parseInt(objSoap.getProperty("fibraDietetica").toString()));
                        infoNutri.setProperty(9, objSoap.getProperty("sodio").toString());
                        infoNutri.setProperty(10, objSoap.getProperty("calcio").toString());
                        infoNutris.add(infoNutri);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());

                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    InfoNutri infoNutri = new InfoNutri();
                    infoNutri.setProperty(0, Integer.parseInt(objSoap.getProperty("id").toString()));
                    infoNutri.setProperty(1, objSoap.getProperty("contenidoEnergetico").toString());
                    infoNutri.setProperty(2, Integer.parseInt(objSoap.getProperty("proteinas").toString()));
                    infoNutri.setProperty(3, Integer.parseInt(objSoap.getProperty("lipidos").toString()));
                    infoNutri.setProperty(4, Integer.parseInt(objSoap.getProperty("grasaSaturada").toString()));
                    infoNutri.setProperty(5, Integer.parseInt(objSoap.getProperty("colesterol").toString()));
                    infoNutri.setProperty(6, Integer.parseInt(objSoap.getProperty("carbohidratos").toString()));
                    infoNutri.setProperty(7, Integer.parseInt(objSoap.getProperty("azucares").toString()));
                    infoNutri.setProperty(8, Integer.parseInt(objSoap.getProperty("fibraDietetica").toString()));
                    infoNutri.setProperty(9, objSoap.getProperty("sodio").toString());
                    infoNutri.setProperty(10, objSoap.getProperty("calcio").toString());
                    infoNutris.add(infoNutri);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[infoNutris.size()];
                for (int i = 0; i < infoNutris.size(); i++) {
                    datos[i] = infoNutris.get(i).getProperty(0) + " - "
                            + infoNutris.get(i).getProperty(1)+ " - "
                            + infoNutris.get(i).getProperty(2)+ " - "
                            + infoNutris.get(i).getProperty(3)+ " - "
                            + infoNutris.get(i).getProperty(4)+ " - "
                            + infoNutris.get(i).getProperty(5)+ " - "
                            + infoNutris.get(i).getProperty(6)+ " - "
                            + infoNutris.get(i).getProperty(7)+ " - "
                            + infoNutris.get(i).getProperty(8)+ " - "
                            + infoNutris.get(i).getProperty(9)+ " - "
                            + infoNutris.get(i).getProperty(10);
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        ListInfoNutri.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class DeleteInfoNutri extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "removeInfoNutri";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("id", idSeleccionado);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(InfoNutriWS.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("0")) {
                    result = true;
                }

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Eliminado", Toast.LENGTH_SHORT).show();
                InfoNutris consulta = new InfoNutris();
                consulta.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}