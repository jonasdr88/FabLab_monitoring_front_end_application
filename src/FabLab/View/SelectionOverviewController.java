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
    private Label[] materialLabels;
    private TextField[] materialQuantityTFS;
    private int materialamount;

    //constructor is called BEFORE the initialize method
    public SelectionOverviewController()
    {
        materialLabels = new Label[5];
        materialQuantityTFS = new TextField[5];
        materialLabels[0] = selectedMaterialLabel1;
        materialLabels[1] = selectedMaterialLabel2;
        materialLabels[2] = selectedMaterialLabel3;
        materialLabels[3] = selectedMaterialLabel4;
        materialLabels[4] = selectedMaterialLabel5;
        materialQuantityTFS[0] = materialQuantityTF1;
        materialQuantityTFS[1] = materialQuantityTF2;
        materialQuantityTFS[2] = materialQuantityTF3;
        materialQuantityTFS[3] = materialQuantityTF4;
        materialQuantityTFS[4] = materialQuantityTF5;

        materialamount = 0;
        materialLabels[0].setVisible(false);
        materialLabels[1].setVisible(false);
        materialLabels[2].setVisible(false);
        materialLabels[3].setVisible(false);
        materialLabels[4].setVisible(false);
        materialQuantityTFS[0].setVisible(false);
        materialQuantityTFS[1].setVisible(false);
        materialQuantityTFS[2].setVisible(false);
        materialQuantityTFS[3].setVisible(false);
        materialQuantityTFS[4].setVisible(false);



    }

    public void initialize()
    {
//        materialamount = 0;
//        for(int i = 0; i<5; i++)
//        {
//            materialLabels[i].setVisible(false);
//            materialLabels[i].setText("");
//            materialQuantityTFS[i].setVisible(false);
//            materialQuantityTFS[i].setText("");
//        }
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
//        for(int i = 0; i<5; i++)
//        {
//            materialLabels[2].setVisible(false);
//            materialLabels[2].setText("");
//            materialQuantityTFS[2].setVisible(false);
            materialQuantityTFS[2].setText("");
//        }

    }

    public void materialSelectHandle()
    {
    }

    public void plusButtonHandle()
    {
//        materialLabels[materialamount].setVisible(true);
//        materialQuantityTFS[materialamount].setVisible(true);
//        materialLabels[materialamount].setText(materialBox.getValue());
//        selectedMaterials.put(selectedMachine.getMaterialHashMap().get(materialBox.getValue()), materialQuantityTFS[materialamount].getText());
//        materialamount++;
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
