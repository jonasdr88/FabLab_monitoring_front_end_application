package FabLab.View;

import FabLab.MainApp;
import FabLab.Model.Machine;
import FabLab.Model.Material;
import FabLab.Model.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jonas on 11/04/2017.
 */
public class SelectionOverviewController {

    @FXML
    private Button startButton;
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
    private Label emailadressLabel;
    @FXML
    private Label studyLabel;
    @FXML
    private Label userIDLabel;
    @FXML
    private Label selectedMachineLabel;
    @FXML
    private Label selectedMaterialLabel1;
    @FXML
    private Label selectedMaterialLabel2;
    @FXML
    private Label selectedMaterialLabel3;
    @FXML
    private Label selectedMaterialLabel4;
    @FXML
    private Label selectedMaterialLabel5;
    @FXML
    private TextField materialQuantityTF1;
    @FXML
    private TextField materialQuantityTF2;
    @FXML
    private TextField materialQuantityTF3;
    @FXML
    private TextField materialQuantityTF4;
    @FXML
    private TextField materialQuantityTF5;
    @FXML
    private Button plusButton;

    // reference to main application
    private MainApp mainapp;
    private Machine selectedMachine;
    private Map<Material, String> selectedMaterials;
    private List<Label> materialLabels;
    private List<TextField> materialQuantityTFS;
    private int materialamount;

    //constructor is called BEFORE the initialize method
    public SelectionOverviewController()
    {
    }

    public void initialize()
    {
        materialLabels = new ArrayList<>();
        selectedMaterials = new HashMap<>();
        materialQuantityTFS = new ArrayList<>();

        materialLabels.add(selectedMaterialLabel1);
        materialLabels.add(selectedMaterialLabel2);
        materialLabels.add(selectedMaterialLabel3);
        materialLabels.add(selectedMaterialLabel4);
        materialLabels.add(selectedMaterialLabel5);

        materialQuantityTFS.add(materialQuantityTF1);
        materialQuantityTFS.add(materialQuantityTF2);
        materialQuantityTFS.add(materialQuantityTF3);
        materialQuantityTFS.add(materialQuantityTF4);
        materialQuantityTFS.add(materialQuantityTF5);

        materialamount = 0;

        for(int i=0; i<5; i++)
        {
            materialLabels.get(i).setVisible(false);
            materialQuantityTFS.get(i).setVisible(false);
        }

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

        materialamount = 0;
        for(int i=0; i<5; i++)
        {
            materialLabels.get(i).setVisible(false);
            materialQuantityTFS.get(i).setVisible(false);
        }
    }

    public void materialSelectHandle()
    {
    }

    public void plusButtonHandle()
    {
        if(materialamount <= 4)
        {
            materialLabels.get(materialamount).setVisible(true);
            materialQuantityTFS.get(materialamount).setVisible(true);
            materialLabels.get(materialamount).setText(materialBox.getValue());
            selectedMaterials.put(selectedMachine.getMaterialHashMap().get(materialBox.getValue()), materialQuantityTFS.get(materialamount).getText());
            materialamount++;
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainapp.getPrimaryStage());
            alert.setTitle("ERROR: materialen");
            alert.setHeaderText("Maximaal aantal materialen bereikt.");
            alert.setContentText("Om de materialen te verwijderen: selecteer een andere machine.");
            alert.showAndWait();
        }

    }

    public void setUserIDLabel(String UID)
    {
        userIDLabel.setText(UID);
    }

    public void getUserInfo(String UID)
    {
        //TODO check UID met database, user bestaat: object maken
        User user = new User("Jonas", "De Rynck", "s0140499", UID, "derynck.jonas@gmail.com", "EICT");
        boolean registered;
        //registered = true;
        registered = false;
        if(registered)
        {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            rolNumberLabel.setText(user.getRolNumber());
            emailadressLabel.setText(user.getEmailAdress());
            studyLabel.setText(user.getStudy());
            userIDLabel.setText(user.getUserID());
            //TODO info van backend halen
        }
        //TODO user bestaat niet: registreren
        else
            registerUser(UID);
    }

    public void registerUser(String UID)
    {
        if(!mainapp.registerWindowOpen)
        {
            mainapp.showRegisterUserDialog(UID);
        }
        else // if registerWindowOpen == true (window is al open)
        {
            //Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainapp.getPrimaryStage());
            alert.setTitle("ERROR: registreer venster");
            alert.setHeaderText("Registreervenster al open");
            alert.setContentText("Sluit het huidige registreervenster om een nieuw te openen.");

            alert.showAndWait();
        }
    }

    public void setFieldsOnRegister(String firstName, String lastName, String rolNumber, String email, String study)
    {
        this.firstNameLabel.setText(firstName);
        this.lastNameLabel.setText(lastName);
        this.rolNumberLabel.setText(rolNumber);
        this.emailadressLabel.setText(email);
        this.studyLabel.setText(study);
    }

}
