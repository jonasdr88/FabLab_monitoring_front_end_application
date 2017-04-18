package FabLab.View;

import FabLab.Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    private Stage registerUserStage;
    private User user;
    private boolean confirmed = false;
    private SelectionOverviewController selectionOverviewController;

    /* initializes controller class; called after the constructor */
    @FXML
    private void initialize()
    {

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

    /*called method when user clicks "register" */
    @FXML
    private void handleRegister()
    {
        if(isInputValid())
        {
            user.setFirstName(firstNameTextField.getText());
            user.setLastName(lastNameTextField.getText());
            user.setRolNumber(rolNumberTextField.getText());
            // userID is set in the MainApp when scanned
            selectionOverviewController.setFieldsOnRegister(user.getFirstName(), user.getLastName(), user.getRolNumber());
            confirmed = true;
            registerUserStage.close();
            //TODO de gemaakte user doorsturen naar backend userdatabase
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
