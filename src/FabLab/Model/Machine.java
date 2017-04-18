package FabLab.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jonas on 11/04/2017.
 */
public class Machine {

    private StringProperty name;
    private ArrayList<Material> materialList;
    private ObservableList<String> materialStringList = FXCollections.observableArrayList();
    private HashMap<String, Material> materialHashMap;
    private int ID;
    boolean inUse;
    private String user;

    public Machine(String name,Material mat1, Material mat2, Material mat3)
    {
        materialList = new ArrayList<>();
        materialHashMap = new HashMap<>();
        this.name = new SimpleStringProperty(name);
        materialList.add(mat1);
        materialList.add(mat2);
        materialList.add(mat3);
        materialStringList.addAll(mat1.getName(), mat2.getName(), mat3.getName());
        materialHashMap.put(mat1.getName(), mat1);
        materialHashMap.put(mat2.getName(), mat2);
        materialHashMap.put(mat3.getName(), mat3);
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

}
