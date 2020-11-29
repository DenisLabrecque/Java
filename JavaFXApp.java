import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.io.File;

public class JavaFXApp extends Application {

    public static final String SOUND_BEEP = "351208__gokhanbiyik__beep-03.wav";
    public static final String SOUND_DOUBLE_BEEP = "351209__gokhanbiyik__beep-02.wav";
    public static final String SOUND_ERROR = "445978__breviceps__error-signal-2.wav";

    public LaserPrinter printer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new HBox(10);

        // Left indicator light pane
        VBox leftBox = new VBox();
        leftBox.setAlignment(Pos.BOTTOM_CENTER);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 15, 20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblToner = new Label("Toner");
        Circle crcToner = new Circle(10, 10, 5, Color.YELLOW);
        crcToner.setOpacity(0.5);
        grid.add(lblToner, 0, 0);
        grid.add(crcToner, 1, 0);

        Label lblDrum = new Label("Drum");
        Circle crcDrum = new Circle(10, 10, 5, Color.YELLOW);
        crcDrum.setOpacity(0.5);
        grid.add(lblDrum, 0, 1);
        grid.add(crcDrum, 1, 1);

        Label lblError = new Label("Error");
        Circle crcError = new Circle(10, 10, 5, Color.YELLOW);
        crcError.setOpacity(0.5);
        grid.add(lblError, 0, 2);
        grid.add(crcError, 1, 2);

        Label lblReady = new Label("Ready");
        Circle crcReady = new Circle(10, 10, 5, Color.YELLOW);
        crcReady.setOpacity(0.5);
        grid.add(lblReady, 0, 3);
        grid.add(crcReady, 1, 3);

        leftBox.getChildren().add(grid);
        pane.getChildren().add(leftBox);



        // Screen
        VBox centerBox = new VBox();
        pane.getChildren().add(centerBox);
        printer = new LaserPrinter(centerBox);



        // Right side
        VBox rightBox = new VBox();

        // Paper tab
        Button btnPaper = new Button("Paper");
        btnPaper.getStyleClass().add("tabButton");
        btnPaper.setOnAction(e -> {
            displayWindow(DisplayAssembly.Window.PAPER_TRAYS);
        });

        // Toner tab
        Button btnToner = new Button("Toner");
        btnToner.getStyleClass().add("tabButton");
        btnToner.setOnAction(e -> {
            displayWindow(DisplayAssembly.Window.TONER_AND_DRUM);
        });

        // Fuser tab
        Button btnFuser = new Button("Fuser");
        btnFuser.getStyleClass().add("tabButton");
        btnFuser.setOnAction(e -> {
            displayWindow(DisplayAssembly.Window.FUSER);
        });

        // Queue tab
        Button btnQueue = new Button("Queue");
        btnQueue.getStyleClass().add("tabButton");
        btnQueue.setOnAction(e -> {
            displayWindow(DisplayAssembly.Window.PRINT_QUEUE);
        });

        // Error tab
        Button btnErrors = new Button("Errors");
        btnErrors.getStyleClass().add("tabButton");
        btnErrors.setOnAction(e -> {
            displayWindow(DisplayAssembly.Window.ERRORS);
        });

        // Power
        Button btnPower = new Button();
        btnPower.getStyleClass().add("power");
        btnPower.setOnAction(e -> {
            if(!printer.isOnOrPowering()) {
                printer.powerOn();
                playSound(SOUND_DOUBLE_BEEP);
            }
            else {
                printer.powerOff();
            }
        });

        rightBox.getChildren().addAll(btnPaper, btnToner, btnFuser, btnQueue, btnErrors, btnPower);
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

    public void displayWindow(DisplayAssembly.Window window) {
        if(printer.isOnOrPowering()) {
            playSound(SOUND_BEEP);
            printer.display().displayWindow(window);
        }
    }

    /**
     * Play a sound by file name.
     */
    private void playSound(String fileName) {
        Media sound = new Media(new File(fileName).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}