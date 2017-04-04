package mx.edu.utng.wsinfonutri;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;

public class InfoNutri implements KvmSerializable {
    private int id;
    private String contenido;
    private int proteinas;
    private int lipidos;
    private int grasas;
    private int colesterol;
    private int carbohidratos;
    private int azucares;
    private int fibra;
    private String sodio;
    private String calcio;


    public InfoNutri(int id, String contenido, int proteinas, int lipidos, int grasas,
                     int colesterol, int carbohidratos, int azucares, int fibra, String sodio, String calcio) {
        this.id = id;
        this.contenido = contenido;
        this.proteinas = proteinas;
        this.lipidos = lipidos;
        this.grasas = grasas;
        this.colesterol = colesterol;
        this.carbohidratos = carbohidratos;
        this.azucares = azucares;
        this.fibra = fibra;
        this.sodio = sodio;
        this.calcio = calcio;
    }

    public InfoNutri() {
        this(0,"",0,0,0,0,0,0,0,"","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return contenido;
            case 2:
                return proteinas;
            case 3:
                return lipidos;
            case 4:
                return grasas;
            case 5:
                return colesterol;
            case 6:
                return carbohidratos;
            case 7:
                return azucares;
            case 8:
                return fibra;
            case 9:
                return sodio;
            case 10:
                return calcio;
        }
        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 11;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id =Integer.parseInt(o.toString());
                break;
            case 1:
                contenido = o.toString();
                break;
            case 2:
                proteinas =Integer.parseInt(o.toString());
                break;
            case 3:
                lipidos =Integer.parseInt(o.toString());
                break;
            case 4:
                grasas =Integer.parseInt(o.toString());
                break;
            case 5:
                colesterol =Integer.parseInt(o.toString());
                break;
            case 6:
                carbohidratos =Integer.parseInt(o.toString());
                break;
            case 7:
                azucares =Integer.parseInt(o.toString());
                break;
            case 8:
                fibra=Integer.parseInt(o.toString());
                break;
            case 9:
                sodio = o.toString();
                break;
            case 10:
                calcio = o.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "contenidoEnergetico";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "proteinas";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "lipidos";
            case 4:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "grasaSaturada";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "colesterol";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "carbohidratos";
                break;
            case 7:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "azucares";
                break;
            case 8:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "fibraDietetica";
                break;
            case 9:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "sodio";
            case 10:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "calcio";
            default:
                break;
        }


    }
}