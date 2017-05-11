package FabLab.View;

import FabLab.MainApp;
import FabLab.Model.Machine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by jonas on 11/05/2017.
 */
public class StopOtherPersonsSessionController {

    @FXML
    private Button stopButton;
    @FXML
    private Button cancelButton;

    private Stage stopSessionStage;
    private static MainApp mainapp;
    private Machine selectedMachine;

    public StopOtherPersonsSessionController()
    {

    }

    private void initialize()
    {

    }

    public void setMainapp(MainApp mainapp)
    {
        this.mainapp = mainapp;
    }

    public void setStopSessionStage(Stage stopSessionStage)
    {
        this.stopSessionStage = stopSessionStage;
    }

    public Stage getStopSessionStage()
    {
        return this.stopSessionStage;
    }

    public void setSelectedMachine(Machine selectedMachine)
    {
        this.selectedMachine = selectedMachine;
    }

    public void handleStopButton()
    {
        mainapp.showAreYouSureScreen();
        stopSessionStage.close();
    }

    public void handleCancelButton()
    {
        stopSessionStage.close();
    }
}
