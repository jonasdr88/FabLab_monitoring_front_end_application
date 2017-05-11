package FabLab.View;

import FabLab.Backend.Backend;
import FabLab.MainApp;
import FabLab.Model.Machine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jonas on 25/04/2017.
 */
public class IdleScreenController {

    private MainApp mainapp;
    @FXML
    private Label MachineLabel1;
    @FXML
    private Label MachineLabel2;
    @FXML
    private Label MachineLabel3;
    @FXML
    private Label MachineLabel4;
    @FXML
    private Label MachineLabel5;
    @FXML
    private Label MachineLabel6;
    @FXML
    private Label MachineLabel7;
    @FXML
    private Label MachineLabel8;
    @FXML
    private Label UserLabel1;
    @FXML
    private Label UserLabel2;
    @FXML
    private Label UserLabel3;
    @FXML
    private Label UserLabel4;
    @FXML
    private Label UserLabel5;
    @FXML
    private Label UserLabel6;
    @FXML
    private Label UserLabel7;
    @FXML
    private Label UserLabel8;

    private List<Label> machineLabels;
    private List<Label> userLabels;
    private List<Machine> machineList;

    public IdleScreenController()
    {}

    public void initialize()
    {
        machineList = Backend.getMachines();
        machineLabels = new ArrayList<>();
        userLabels = new ArrayList<>();
        machineLabels.add(MachineLabel1);
        machineLabels.add(MachineLabel2);
        machineLabels.add(MachineLabel3);
        machineLabels.add(MachineLabel4);
        machineLabels.add(MachineLabel5);
        machineLabels.add(MachineLabel6);
        machineLabels.add(MachineLabel7);
        machineLabels.add(MachineLabel8);
        userLabels.add(UserLabel1);
        userLabels.add(UserLabel2);
        userLabels.add(UserLabel3);
        userLabels.add(UserLabel4);
        userLabels.add(UserLabel5);
        userLabels.add(UserLabel6);
        userLabels.add(UserLabel7);
        userLabels.add(UserLabel8);

        for(int i=0; i<8; i++)
        {
            machineLabels.get(i).setText("");
            userLabels.get(i).setText("");
        }
    }

    public void checkInUse()
    {
        int j = 0;
        for(int i=0; i<machineList.size(); i++)
        {
            if(machineList.get(i).getInUse())
            {
                machineLabels.get(j).setText(machineList.get(i).getName());
                userLabels.get(j).setText(machineList.get(i).getInUseBy());
                System.out.println(machineList.get(i).getName() + "     --    " + machineList.get(i).getInUseBy());
                j++;
                if(j>7)
                    return;
            }
        }
    }

    public void setMainApp(MainApp mainapp)
    {
        this.mainapp = mainapp;
    }

}
