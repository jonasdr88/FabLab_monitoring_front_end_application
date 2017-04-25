package FabLab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jonas on 11/04/2017.
 */
public class Material {

    private final StringProperty name;
    private StringProperty unit;

    public Material(String name) {
        this.name = new SimpleStringProperty(name);
        this.unit = new SimpleStringProperty("m³");
    }

    public String getName()
    {
        return this.name.get();
    }

    public String getUnit()
    {
        return this.unit.get();
    }
}
