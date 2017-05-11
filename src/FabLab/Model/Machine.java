package FabLab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by jonas on 11/04/2017.
 */
public class Machine {


    private StringProperty name;
    private ArrayList<Material> materialList;
    private ObservableList<String> materialStringList = FXCollections.observableArrayList();
    private HashMap<String, Material> materialHashMap;
    private int id;
    boolean inUse = false;
    private String user;

    public Machine(int id, String name,ArrayList<Material> materials)
    {
        this.id = id;
        materialList = new ArrayList<>();
        materialHashMap = new HashMap<>();
        this.name = new SimpleStringProperty(name);
        for(Material material: materials) {
            materialList.add(material);
            materialHashMap.put(material.getName(), material);
        }
        materialStringList.addAll(materials.stream().map(m -> m.getName()).collect(Collectors.toList()));
    }

    public String getName()
    {
        return this.name.get();
    }

    public ArrayList<Material> getMaterialList()
    {
        return this.materialList;
    }

    public ObservableList<String> getMaterialStringList()
    {
        return this.materialStringList;
    }

    public HashMap<String, Material> getMaterialHashMap()
    {
        return this.materialHashMap;
    }

    public int getId() {
        return this.id;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
        if(!inUse)
        this.user = "";
    }

    public void setInUseBy(String name) {
        System.out.println("Setting in use by to "+name);
        this.user = name;
    }

    public boolean getInUse()
    {
        return this.inUse;
    }

    public String getInUseBy() {
        return this.user;
    }

}
