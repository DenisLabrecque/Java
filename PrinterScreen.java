import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PrinterScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new HBox(10);
        Image image = new Image("Screen.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(1920);
        imageView.setFitHeight(1080);
        pane.getChildren().add(imageView);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Printer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}