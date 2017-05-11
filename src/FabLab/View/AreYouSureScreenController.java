package FabLab.View;

import FabLab.Backend.Backend;
import FabLab.MainApp;
import FabLab.Model.Machine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by jonas on 11/05/2017.
 */
public class AreYouSureScreenController {

    @FXML
    private Button YesButton;
    @FXML
    private Button NoButton;

    private Stage areYouSureStage;
    private static MainApp mainapp;
    private boolean stopSession;

    public AreYouSureScreenController()
    {

    }

    private void initialize()
    {

    }

    public void setMainapp(MainApp mainapp)
    {
        this.mainapp = mainapp;
    }

    public void setAreYouSureStage(Stage areYouSureStage)
    {
        this.areYouSureStage = areYouSureStage;
    }

    public boolean getStopSession()
    {
        return this.stopSession;
    }

    public void handleYesButton()
    {
        this.areYouSureStage.close();
        stopSession = true;
    }

    public void handleNoButton()
    {
        this.areYouSureStage.close();
        stopSession = false;
    }
}
