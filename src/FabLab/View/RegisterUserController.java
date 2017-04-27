package FabLab.View;

import FabLab.Backend.Backend;
import FabLab.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


/**
 * Created by jonas on 11/04/2017.
 */
public class RegisterUserController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField rolNumberTextField;
    @FXML
    private TextField emailAdressTextField;
    @FXML
    private ComboBox<String> studyComboBox;
    @FXML
    private TextField studyTextField;
    @FXML
    private Label studyLabel;
    @FXML
    private Label andereOpleidingLabel;

    private Stage registerUserStage;
    private User user;
    private boolean confirmed = false;
    private boolean isStudyDifferent = false;
    private SelectionOverviewController selectionOverviewController;
    private static ObservableList<String> studyStringList;

    public RegisterUserController()
    {
        //studyStringList.addAll("Elektronica-ICT", "Elektromechanica", "Bouwkunde", "Chemie", "Andere");
    }
    /* initializes controller class; called after the constructor */
    @FXML
    private void initialize()
    {
        andereOpleidingLabel.setVisible(false);
        studyTextField.setVisible(false);
        studyStringList = FXCollections.observableArrayList();
        studyStringList.addAll("Elektronica-ICT", "Elektromechanica", "Bouwkunde", "Chemie", "Andere");
        studyComboBox.setItems(studyStringList);
    }

    /*set the stage of the dialog window*/
    public void setRegisterUserStage(Stage registerUserStage)
    {
        this.registerUserStage = registerUserStage;
    }

    public void setSelectionOverviewController(SelectionOverviewController controller)
    {
        this.selectionOverviewController = controller;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    /* return true if the user confirmed the registration */
    public boolean isConfirmed()
    {
        return confirmed;
    }

    /*called method when user select a study from the combobox */
    @FXML
    private void handleStudySelect()
    {
        studyLabel.setText(studyComboBox.getValue());
        if(studyLabel.getText() == "Andere")
        {
            isStudyDifferent = true;
            andereOpleidingLabel.setVisible(true);
            studyTextField.setVisible(true);
        }
        else
        {
            isStudyDifferent = false;
            andereOpleidingLabel.setVisible(false);
            studyTextField.setVisible(false);
        }
    }

    /*called method when user clicks "register" */
    @FXML
    private void handleRegister()
    {

        if(isInputValid())
        {
            String study = studyComboBox.getValue().equalsIgnoreCase("Andere") ? studyTextField.getText() : studyComboBox.getValue();
            User newUser = Backend.createUser(user.getNFCId(), firstNameTextField.getText(), lastNameTextField.getText(), study, rolNumberTextField.getText(), emailAdressTextField.getText());
            if(newUser == null) {
                //TODO: iets misgegaan bij creatie, check console
                return;
            } else
                selectionOverviewController.setCurrentUser(newUser);

            user.setFirstName(firstNameTextField.getText());
            user.setLastName(lastNameTextField.getText());
            user.setRolNumber(rolNumberTextField.getText());
            user.setEmailAdress(emailAdressTextField.getText());
            if(!isStudyDifferent)
                user.setStudy(studyLabel.getText());
            else
                user.setStudy(studyTextField.getText());
            // userID is set in the MainApp when scanned
            selectionOverviewController.setFieldsOnRegister(user.getFirstName(), user.getLastName(), user.getRolNumber(),
                    user.getEmailAdress(), user.getStudy());
            confirmed = true;
            registerUserStage.close();
        }
    }

    @FXML
    private void handleCancel()
    {
        registerUserStage.close();
    }

    private boolean isInputValid()
    {
        String errorMessage = "";

        if(firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0)
            errorMessage += "Geef een geldige voornaam in. \n";
        if(lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0)
            errorMessage += "Geef een geldige familienaam in. \n";
        if(rolNumberTextField.getText() == null || rolNumberTextField.getText().length() == 0)
            errorMessage += "Geef een geldig rolnummer in. \n";
        if(emailAdressTextField.getText() == null || emailAdressTextField.getText().length() == 0 ||
                !emailAdressTextField.getText().contains("@") || !emailAdressTextField.getText().contains("."))
            errorMessage += "Geef een geldig e-mailadres in. \n";
        if(!isStudyDifferent)
        {
            if(studyLabel.getText() == null || studyLabel.getText().length() == 0)
                errorMessage += "Kies een opleiding.";
        }
        else
        {
            if(studyTextField.getText() == null || studyTextField.getText().length() == 0)
                errorMessage += "vul een geldige opleiding in.";
        }

        if(errorMessage.length() == 0)
            return true;
        else
        {
            //Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(registerUserStage);
            alert.setTitle("ERROR: ongeldige velden");
            alert.setHeaderText("corrigeer de ongeldige velden");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

}
