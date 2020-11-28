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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.io.File;

public class PrinterScreen extends Application {

    public LaserPrinter printer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new HBox(10);

        // Left indicator light pane
        VBox leftBox = new VBox();

        HBox hbxToner = new HBox(10);
        Label lblToner = new Label("Toner");
        Circle crcToner = new Circle(10, 10, 5, Color.YELLOW);
        crcToner.setOpacity(0.5);
        hbxToner.getChildren().addAll(lblToner, crcToner);

        HBox hbxDrum = new HBox(10);
        Label lblDrum = new Label("Drum");
        Circle crcDrum = new Circle(10, 10, 5, Color.YELLOW);
        crcDrum.setOpacity(0.5);
        hbxDrum.getChildren().addAll(lblDrum, crcDrum);

        HBox hbxError = new HBox(10);
        Label lblError = new Label("Error");
        Circle crcError = new Circle(10, 10, 5, Color.YELLOW);
        crcError.setOpacity(0.5);
        hbxError.getChildren().addAll(lblError, crcError);

        HBox hbxReady = new HBox(10);
        Label lblReady = new Label("Ready");
        Circle crcReady = new Circle(10, 10, 5, Color.YELLOW);
        crcReady.setOpacity(0.5);
        hbxReady.getChildren().addAll(lblReady, crcReady);

        leftBox.getChildren().addAll(hbxToner, hbxDrum, hbxError, hbxReady);
        pane.getChildren().add(leftBox);



        // Screen
        VBox centerBox = new VBox();
        Rectangle rectScreen = new Rectangle(400, 300, Color.LIGHTGREY);
        centerBox.getChildren().add(rectScreen); // TODO there should probably be a switch screen function that switches
                                              // the FX element to simulate changing the window
        pane.getChildren().add(centerBox);
        printer = new LaserPrinter(centerBox);



        // Right side
        VBox rightBox = new VBox();
        Button btnPower = new Button("Power");
        btnPower.getStyleClass().add("power");
        btnPower.setOnAction(e -> {
            if(!printer.isOnOrPowering()) {
                printer.powerOn();
                Media sound = new Media(new File("Java/351209__gokhanbiyik__beep-02.wav").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
        });
        rightBox.getChildren().add(btnPower);
        pane.getChildren().add(rightBox);





        //Image image = new Image("Screen.png");
        //ImageView imageView = new ImageView(image);
        //imageView.setFitWidth(1920);
        //imageView.setFitHeight(1080);
        //pane.getChildren().add(imageView);

        Scene scene = new Scene(pane);
        scene.getStylesheets().add("printerStyles.css");
        primaryStage.setTitle("Laser Printer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}