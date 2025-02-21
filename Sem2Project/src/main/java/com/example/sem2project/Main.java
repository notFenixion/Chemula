package com.example.sem2project;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private Stage mainStage;
    private static final int SPLASH_WIDTH = 400;
    private static final int SPLASH_HEIGHT = 200;

    @Override
    // Load Splash Screen when initialized - referenced from Lab 8
    public void init() {
        //String splashLogoName =  "/nushigh.png"; //replace this with splashlogo.png later!!
        ImageView splash = new ImageView(getClass().getResource( "usedimages/splashscreen.jpeg").toExternalForm());
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label();
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle(
            "-fx-padding: 5; " +
            "-fx-background-color: cornsilk; " +
            "-fx-border-width:5; " +
            "-fx-border-color: " +
            "linear-gradient(" +
            "to bottom, " +
            "chocolate, " +
            "derive(chocolate, 50%)" +
            ");"
        );
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        //Create new Task
        final Task<Integer> friendTask = new Task<Integer>() {

            @Override
            protected Integer call() throws Exception {
                updateMessage("loading application... 1...");
                for (int i = 1; i <= 5; i++) {
                    Thread.sleep(1000);
                    updateProgress(i + 1, 6);
                    updateMessage("loading application... " + i + "...");
                }
                return 5;
            }
        };
        //Calls showSplash method
        showSplash(initStage, friendTask,
            () -> {
                try {
                    showInitialStage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        );
        new Thread(friendTask).start();
    }

    //Load  StartingScreen stage
    public void showInitialStage() throws IOException {
        mainStage = new Stage();
        //String startingScreenName =  "/startingscreen.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource( "View/startingscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        mainStage.setTitle("Chemula - An Organic Chem Simulator");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();
    }

    //Load Splash Screen
    public void showSplash(final Stage initStage, Task<?> task, InitCompletionHandler initCompletionHandler) {
        progressText.textProperty().bind(task.messageProperty());
        loadProgress.progressProperty().bind(task.progressProperty());
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                loadProgress.progressProperty().unbind();
                loadProgress.setProgress(1);
                initStage.toFront();
                //Use FadeTransition to animate the splash screen fading out.
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1.2), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
                initCompletionHandler.complete();
            }
        });
        //initStage is used for the splash screen
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.show();
    }

    public interface InitCompletionHandler {
        public void complete();
    }
    public static void main(String[] args) {
        launch();
    }


}