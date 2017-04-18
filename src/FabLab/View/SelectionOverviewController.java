package FabLab.View;

import FabLab.MainApp;
import FabLab.Model.Machine;
import FabLab.Model.Material;
import FabLab.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Created by jonas on 11/04/2017.
 */
public class SelectionOverviewController {

    @FXML
    private ComboBox<String> machineBox;
    @FXML
    private ComboBox<String> materialBox;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label rolNumberLabel;
    @FXML
    private Label userIDLabel;
    @FXML
    private Label selectedMachineLabel;
    @FXML
    private Label selectedMaterialLabel;

    // reference to main application
    private MainApp mainapp;
    private Machine selectedMachine;
    private Material selectedMaterial;

    //constructor is called BEFORE the initialize method
    public SelectionOverviewController()
    {
    }

    public void initialize()
    {
    }

    @FXML
    public void handleStart()
    {
        //TODO info naar de backend sturen; Log maken
    }

    //Called by the MainApp to give a reference to itself
    public void setMainApp(MainApp mainApp)
    {
        this.mainapp = mainApp;
        machineBox.setItems(mainApp.getMachineStringList());
    }

    public void machineSelectHandle()
    {
        selectedMachine = mainapp.getMachineHashMap().get(machineBox.getValue());
        selectedMachineLabel.setText(machineBox.getValue());
        materialBox.setItems(selectedMachine.getMaterialStringList());
    }

    public void materialSelectHandle()
    {
        selectedMaterial = selectedMachine.getMaterialHashMap().get(materialBox.getValue());
        selectedMaterialLabel.setText(materialBox.getValue());
    }

    public void setUserIDLabel(String UID)
    {
        userIDLabel.setText(UID);
    }

    public void getUserInfo(String UID)
    {
        //TODO check UID met database, user bestaat: object maken
        User user = new User("Jonas", "De Rynck", "s0140499", UID);
        boolean registered;
        //registered = true;
        registered = false;
        if(registered)
        {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            rolNumberLabel.setText(user.getRolNumber());
            userIDLabel.setText(user.getUserID());
            //TODO info van backend halen
        }
        //TODO user bestaat niet: registreren
        else
            registerUser(UID);
    }

    public void registerUser(String UID)
    {
        mainapp.showRegisterUserDialog(UID);
    }

    public void setFieldsOnRegister(String firstName, String lastName, String rolNumber)
    {
        this.firstNameLabel.setText(firstName);
        this.lastNameLabel.setText(lastName);
        this.rolNumberLabel.setText(rolNumber);
    }

}