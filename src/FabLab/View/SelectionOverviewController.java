package FabLab.View;

import FabLab.Backend.Backend;
import FabLab.MainApp;
import FabLab.Model.Machine;
import FabLab.Model.Material;
import FabLab.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Label unitLabel1;
    @FXML
    private Label unitLabel2;
    @FXML
    private Label unitLabel3;
    @FXML
    private Label unitLabel4;
    @FXML
    private Label unitLabel5;
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
    private Map<Material, Double> selectedMaterials;
    private List<Label> materialLabels;
    private List<TextField> materialQuantityTFS;
    private List<Label> unitLabels;
    private int materialamount;
    private User currentUser;
    private HashMap<String, Machine> machineHashMap;

    //constructor is called BEFORE the initialize method
    public SelectionOverviewController()
    {
    }

    public void initialize()
    {
        materialLabels = new ArrayList<>();
        selectedMaterials = new HashMap<>();
        materialQuantityTFS = new ArrayList<>();
        unitLabels = new ArrayList<>();

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

        unitLabels.add(unitLabel1);
        unitLabels.add(unitLabel2);
        unitLabels.add(unitLabel3);
        unitLabels.add(unitLabel4);
        unitLabels.add(unitLabel5);

        materialamount = 0;

        for(int i=0; i<5; i++)
        {
            materialLabels.get(i).setVisible(false);
            materialQuantityTFS.get(i).setVisible(false);
            unitLabels.get(i).setVisible(false);
        }
    }

    @FXML
    public void handleStart()
    {
        HashMap<Material, Double> doubleMap = new HashMap<>();
        try {
            for(int j=0; j<materialamount; j++)
            {
                System.out.println("Error bij materialamount: " + j);
                if(!selectedMaterials.containsKey(selectedMachine.getMaterialHashMap().get(materialLabels.get(j).getText())))
                    selectedMaterials.put(selectedMachine.getMaterialHashMap().get(materialLabels.get(j).getText()),
                            Double.parseDouble(materialQuantityTFS.get(j).getText().replace(',', '.').replace(" ","")));
                else
                {
                    System.out.println("string being parsed: " + selectedMaterials.get(selectedMachine.getMaterialHashMap().get(materialLabels.get(j).getText())));
                    double amount = selectedMaterials.get(selectedMachine.getMaterialHashMap().get(materialLabels.get(j).getText()));
                    System.out.println("second string begin parsed: "+ materialQuantityTFS.get(j).getText());
                    amount += Double.parseDouble(materialQuantityTFS.get(j).getText().replace(',', '.').replace(" ",""));
                    selectedMaterials.put(selectedMachine.getMaterialHashMap().get(materialLabels.get(j).getText()), amount);
                }
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainapp.getPrimaryStage());
            alert.setTitle("ERROR: ongeldige input");
            alert.setHeaderText("corrigeer de hoeveelheidsvelden");
            alert.setContentText("Hoeveelheidsvelden mogen enkel nummers en komma's bevatten.");
            alert.showAndWait();
            return;
        }
        //TODO info naar de backend sturen; Log maken
        if(!Backend.checkIn(currentUser, selectedMachine, selectedMaterials))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainapp.getPrimaryStage());
            alert.setTitle("ERROR: PROBLEEM");
            alert.setHeaderText("Error 1337");
            alert.setContentText("Er is iets misgelopen!.");
            alert.showAndWait();
        }
        else
        {
            materialamount = 0;
            selectedMaterials.clear();
            currentUser = null;
            selectedMachine = null;
        }

        mainapp.showIdleScreen();
    }

    //Called by the MainApp to give a reference to itself
    public void setMainApp(MainApp mainApp)
    {
        this.mainapp = mainApp;
        machineBox.setItems(mainApp.getMachineStringList());
    }

    public void setMachineHashMap()
    {
        machineHashMap = mainapp.getMachineHashMap();
    }

    public void machineSelectHandle() //TODO ERROR wanneer een 2e keer gescanned wordt?
    {

            selectedMachine = machineHashMap.get(machineBox.getValue());
            selectedMachineLabel.setText(machineBox.getValue());
        if(selectedMachine != null)
            materialBox.setItems(selectedMachine.getMaterialStringList());

            materialamount = 0;
            for (int i = 0; i < 5; i++) {
                materialLabels.get(i).setVisible(false);
                materialLabels.get(i).setText("");
                materialQuantityTFS.get(i).setVisible(false);
                materialQuantityTFS.get(i).setText("");
                unitLabels.get(i).setVisible(false);
                unitLabels.get(i).setText("");
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
            unitLabels.get(materialamount).setVisible(true);
            materialLabels.get(materialamount).setText(materialBox.getValue());
            unitLabels.get(materialamount).setText(selectedMachine.getMaterialHashMap().get(materialBox.getValue()).getUnit());
            //selectedMaterials.put(selectedMachine.getMaterialHashMap().get(materialBox.getValue()), "");
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
        User user = Backend.getUser(UID);
        if(user != null)
        {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            rolNumberLabel.setText(user.getRolNumber());
            emailadressLabel.setText(user.getEmailAdress());
            studyLabel.setText(user.getStudy());
            userIDLabel.setText(user.getId()+"");
            setCurrentUser(user);
        }
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

    public void setFieldsOnRegister(String firstName, String lastName, String rolNumber, String email, String study, int userID)
    {
        this.firstNameLabel.setText(firstName);
        this.lastNameLabel.setText(lastName);
        this.rolNumberLabel.setText(rolNumber);
        this.emailadressLabel.setText(email);
        this.studyLabel.setText(study);
        this.userIDLabel.setText(userID + "");
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

}
