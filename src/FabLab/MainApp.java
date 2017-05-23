package FabLab;

import FabLab.Backend.Backend;
import FabLab.Model.Machine;
import FabLab.Model.User;
import FabLab.Reader.JavaReader;
import FabLab.View.*;
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
    private static NewSessionOrStopController newSessionOrStopController;
    private static StopOtherPersonsSessionController stopOtherPersonsSessionController;
    private static AreYouSureScreenController areYouSureScreenController;
    public boolean registerWindowOpen = false;
    private static MainApp mainapp;
    private static boolean isIdle = true;
    private static String currentUID;
    public static long timestamp;
    private AnchorPane newSessionOrStop;
    private AnchorPane selectionOverview;
    private AnchorPane page;

    public MainApp()
    {
        mainapp = this;
        System.out.println("Loading panels...");
        loadPanels();
        System.out.println("Finished loading panels...");
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
    public void showSelectionOverview(String UID)
    {
            isIdle = false;
            rootLayout.setCenter(selectionOverview);
            selectionOverviewController.setMainApp(this);
            selectionOverviewController.setMachineHashMap();
            selectionOverviewController.setUserIDLabel(UID);
            selectionOverviewController.getUserInfo(UID);
    }

    public void loadPanels() {
        FXMLLoader loader = new FXMLLoader();

        try {
            loader.setLocation(MainApp.class.getResource("View/NewSessionOrStop.fxml"));
            newSessionOrStop = loader.load();
            newSessionOrStopController = loader.getController();

            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/SelectionOverview.fxml"));
            selectionOverview = (AnchorPane) loader.load();
            selectionOverviewController = loader.getController();

            loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/RegisterUser.fxml"));
            page = (AnchorPane) loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showNewSessionOrStopScreen()
    {
        isIdle = false;
        newSessionOrStopController.setMainApp(this);
        rootLayout.setCenter(newSessionOrStop);
    }

    /* shows the dialog when the person needs to be registered */
    public boolean showRegisterUserDialog(String UID)
    {
        isIdle = false;

            Stage registerUserStage = new Stage();
            registerUserStage.setTitle("Registreer");
            registerUserStage.initModality(Modality.WINDOW_MODAL);
            registerUserStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            registerUserStage.setScene(scene);
            FXMLLoader loader = new FXMLLoader();
            RegisterUserController controller = loader.getController();
            controller.setRegisterUserStage(registerUserStage);
            controller.setUser(new User(-1, "", "", "", "", "", UID, null));
            controller.setSelectionOverviewController(selectionOverviewController);
            controller.setMainapp(this);

            registerWindowOpen = true;
            registerUserStage.showAndWait();
            registerWindowOpen = false;
            return controller.isConfirmed();


    }

    public void showStopOtherPersonsSessionScreen()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/StopOtherPersonsSession.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage stopSessionStage = new Stage();
            stopSessionStage.setTitle("Stop een sessie");
            stopSessionStage.initModality(Modality.WINDOW_MODAL);
            stopSessionStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            stopSessionStage.setScene(scene);

            stopOtherPersonsSessionController = loader.getController();
            stopOtherPersonsSessionController.setStopSessionStage(stopSessionStage);
            stopOtherPersonsSessionController.setMainapp(this);
            stopOtherPersonsSessionController.setSelectedMachine(selectionOverviewController.getSelectedMachine());

            stopSessionStage.showAndWait();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showAreYouSureScreen()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/AreYouSureScreen.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage areYouSureStage = new Stage();
            areYouSureStage.setTitle("Bent u zeker?");
            areYouSureStage.initModality(Modality.WINDOW_MODAL);
            areYouSureStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            areYouSureStage.setScene(scene);

            areYouSureScreenController = loader.getController();
            areYouSureScreenController.setAreYouSureStage(areYouSureStage);
            areYouSureScreenController.setMainapp(this);

            areYouSureStage.showAndWait();

            if(areYouSureScreenController.getStopSession())
            {
                Backend.checkOut(selectionOverviewController.getSelectedMachine(), selectionOverviewController.getCurrentUser());
                checkData();
            }


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showIdleScreen()
    {
        isIdle = true;
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("View/IdleScreen.fxml"));
            AnchorPane IdleScreen = (AnchorPane) loader.load();
            rootLayout.setCenter(IdleScreen);
            idleScreenController = loader.getController();
            idleScreenController.checkInUse();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean getIdleState()
    {
        return isIdle;
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

    public SelectionOverviewController getSelectionOverviewController()
    {
        return this.selectionOverviewController;
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
                        currentUID = UID;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                checkData();
                                mainapp.showNewSessionOrStopScreen();
                                newSessionOrStopController.setCurrentUser(Backend.getUser(UID), UID);
                                newSessionOrStopController.setMachineLabels();
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
        if(isIdle)
        {
            System.out.println("heart beat");
            mainapp.showIdleScreen();
        }

    }

    public static void checkData()
    {
        machineHashMap.clear();
        machineStringList.clear();
        for(Machine machine: Backend.getMachines()) {
            machineHashMap.put(machine.getName(), machine);
            machineStringList.add(machine.getName());
        }
    }

}
