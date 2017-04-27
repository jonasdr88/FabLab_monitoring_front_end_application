package FabLab;

import FabLab.Model.Machine;
import FabLab.Model.Material;
import FabLab.Model.User;
import FabLab.Reader.JavaReader;
import FabLab.View.IdleScreenController;
import FabLab.View.RegisterUserController;
import FabLab.View.SelectionOverviewController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by jonas on 11/04/2017.
 */

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private static ObservableList<String> machineStringList;
    private static HashMap<String,Machine> machineHashMap;
    private static SelectionOverviewController selectionOverviewController;
    private static IdleScreenController idleScreenController;
    public boolean registerWindowOpen = false;
    private static MainApp mainapp;

    public MainApp()
    {
        mainapp = this;
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("FabLab App");

        initRootLayout();
        showIdleScreen();
    }

    /* initialize the root layout */
    public void initRootLayout()
    {
        try
        {
            //Loads the root layout from fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            //Shows the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* Show the Main screen for selection of machines and materials */
    public void showSelectionOverview()
    {
        try
        {
            // Load the selectionOverview from FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/SelectionOverview.fxml"));
            AnchorPane selectionOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(selectionOverview);
            selectionOverviewController = loader.getController();
            selectionOverviewController.setMainApp(this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* shows the dialog when the person needs to be registered */
    public boolean showRegisterUserDialog(String UID)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/RegisterUser.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //create the stage
            Stage registerUserStage = new Stage();
            registerUserStage.setTitle("Registreer");
            registerUserStage.initModality(Modality.WINDOW_MODAL);
            registerUserStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            registerUserStage.setScene(scene);

            RegisterUserController controller = loader.getController();
            controller.setRegisterUserStage(registerUserStage);
            controller.setUser(new User("", "", "", UID, "", ""));
            controller.setSelectionOverviewController(selectionOverviewController);

            registerWindowOpen = true;
            registerUserStage.showAndWait();
            registerWindowOpen = false;
            return controller.isConfirmed();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void showIdleScreen()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/IdleScreen.fxml"));
            AnchorPane IdleScreen = (AnchorPane) loader.load();
            rootLayout.setCenter(IdleScreen);
            idleScreenController = loader.getController();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /* Return the Main Stage (Primarystage) */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public ObservableList<String> getMachineStringList()
    {
        return this.machineStringList;
    }

    public HashMap<String, Machine> getMachineHashMap()
    {
        return this.machineHashMap;
    }

    public static void main(String[] args)
    {
        machineHashMap = new HashMap<>();
        machineStringList = FXCollections.observableArrayList();
        checkData();
        Timeline timeline = new Timeline(new KeyFrame(
                new Duration(60000),
                ae -> heartBeat()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        RFIDReader RFIDreader = new JavaReader();
        if(RFIDreader.detectReader())
        {
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String UID = RFIDreader.readUID();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                mainapp.showSelectionOverview();
                                selectionOverviewController.setUserIDLabel(UID);
                                selectionOverviewController.getUserInfo(UID);
                                //TODO communicatie met backend en naam displayen
                            }
                        });
                    }
                }
            });
            readThread.start();
            launch(args);
        }
        else
        {
            System.out.println("SOMETHING WENT WRONG!!!!");
        }
    }

    public static void heartBeat()
    {
        //TODO send a heartbeat to backend
        System.out.println("heartbeat in the mainapp to backend");
    }

    public static void checkData()
    {
        //TODO Communicatie met server om de machines en materialen op te vragen
        Machine machine1 = new Machine("Machine1", new Material("Material1"), new Material("material2"),
                new Material("Material3"));
        Machine machine2 = new Machine("Machine2", new Material("Material4"), new Material("material5"),
                new Material("Material6"));
        Machine machine3 = new Machine("Machine3", new Material("Material7"), new Material("material8"),
                new Material("Material9"));
        machineHashMap.put(machine1.getName(), machine1);
        machineHashMap.put(machine2.getName(), machine2);
        machineHashMap.put(machine3.getName(), machine3);
        machineStringList.addAll(machine1.getName(), machine2.getName(), machine3.getName());
    }

}
