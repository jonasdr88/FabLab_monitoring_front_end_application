package FabLab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by jonas on 11/04/2017.
 */
public class Material {

    private final StringProperty name;

    public Material(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getName()
    {
        return this.name.get();
    }
}
