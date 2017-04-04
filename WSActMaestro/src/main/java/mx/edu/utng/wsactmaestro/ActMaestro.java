package mx.edu.utng.wsactmaestro;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import java.util.Hashtable;

/**
 * Created by Kast on 30/03/2017.
 */
public class ActMaestro implements KvmSerializable {
    private int id;
    private String name;
    private String description;
    private String activ;


    public ActMaestro(int id, String name, String description, String activ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.activ = activ;
    }

    public ActMaestro() {
        this(0,"","","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return description;
            case 3:
                return activ;
        }
        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 4;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id =Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                description = o.toString();
                break;
            case 3:
                activ = o.toString();
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
                propertyInfo.name = "name";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "description";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "activ";
                break;
            default:
                break;
        }


    }
}