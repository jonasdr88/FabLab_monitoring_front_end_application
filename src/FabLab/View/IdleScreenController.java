package FabLab.View;

import FabLab.MainApp;

/**
 * Created by jonas on 25/04/2017.
 */
public class IdleScreenController {

    private MainApp mainapp;

    public IdleScreenController()
    {}

    public void initialize()
    {
    }

    public void setMainApp(MainApp mainapp)
    {
        this.mainapp = mainapp;
    }

    public void showSelectionOverviewScreen()
    {
        mainapp.showSelectionOverview();
    }
}
