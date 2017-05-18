package FabLab.View;

import FabLab.Backend.Backend;
import FabLab.MainApp;
import FabLab.Model.Machine;
import FabLab.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonas on 02/05/2017.
 */
public class NewSessionOrStopController {

    @FXML
    private Label machineLabel1;
    @FXML
    private Label machineLabel2;
    @FXML
    private Label machineLabel3;
    @FXML
    private Label machineLabel4;
    @FXML
    private Label machineLabel5;
    @FXML
    private Label machineLabel6;
    @FXML
    private Button stopButton1;
    @FXML
    private Button stopButton2;
    @FXML
    private Button stopButton3;
    @FXML
    private Button stopButton4;
    @FXML
    private Button stopButton5;
    @FXML
    private Button stopButton6;
    @FXML
    private Button newSessionButton;

    private List<Label> machineLabels;
    private List<Button> stopButtons;
    private List<Machine> machineList;
    private MainApp mainapp;
    private User currentUser;
    private String NFC_UID;


    public void initialize()
    {
        machineLabels = new ArrayList<>();
        stopButtons = new ArrayList<>();

        machineLabels.add(machineLabel1);
        machineLabels.add(machineLabel2);
        machineLabels.add(machineLabel3);
        machineLabels.add(machineLabel4);
        machineLabels.add(machineLabel5);
        machineLabels.add(machineLabel6);

        stopButtons.add(stopButton1);
        stopButtons.add(stopButton2);
        stopButtons.add(stopButton3);
        stopButtons.add(stopButton4);
        stopButtons.add(stopButton5);
        stopButtons.add(stopButton6);

        for(Label label: machineLabels)
        {
            label.setVisible(false);
            label.setText("");
        }
        for(Button button: stopButtons)
            button.setVisible(false);

    }

    public void setMainApp(MainApp mainapp)
    {
        this.mainapp = mainapp;
    }

    public void setCurrentUser(User user, String UID) {
            this.currentUser = user;
            this.NFC_UID = UID;
    }


    public void setMachineLabels()
    {
        if(currentUser != null)
        {
            machineList = currentUser.getMachinesInUse();
            for(int i=0; i<machineList.size(); i++)
            {
                machineLabels.get(i).setVisible(true);
                stopButtons.get(i).setVisible(true);
                machineLabels.get(i).setText(machineList.get(i).getName());
            }
        }

    }

    public void handleNewSession()
    {
        mainapp.showSelectionOverview(NFC_UID);
    }

    public void handleStop1()
    {
        Backend.checkOut(machineList.get(0),currentUser);
    }
    public void handleStop2()
    {
        Backend.checkOut(machineList.get(1),currentUser);
    }
    public void handleStop3()
    {
        Backend.checkOut(machineList.get(2),currentUser);
    }
    public void handleStop4()
    {
        Backend.checkOut(machineList.get(3),currentUser);
    }
    public void handleStop5()
    {
        Backend.checkOut(machineList.get(4),currentUser);
    }
    public void handleStop6()
    {
        Backend.checkOut(machineList.get(5),currentUser);
    }

}
