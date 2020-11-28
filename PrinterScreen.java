import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.io.File;

public class PrinterScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new HBox(10);

        // Left indicator light pane
        VBox lightPane = new VBox();
        Label lblToner = new Label("Toner");
        Label lblDrum = new Label("Drum");
        Label lblError = new Label("Error");
        Label lblReady = new Label("Ready");
        lightPane.getChildren().addAll(lblToner, lblDrum, lblError, lblReady);
        pane.getChildren().add(lightPane);



        //Image image = new Image("Screen.png");
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(1920);
        //imageView.setFitHeight(1080);
        //pane.getChildren().add(imageView);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Laser Printer");
        primaryStage.setScene(scene);
        primaryStage.show();

        Media sound = new Media(new File("Java/351209__gokhanbiyik__beep-02.wav").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}