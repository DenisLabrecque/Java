import javafx.geometry.Insets;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Map;

/**
 * Takes information sent to the display and prints it to the screen.
 * The screen is a JavaFX pane.
 * Denis Labrecque, November 2020
 */
public class ScreenDisplay extends DisplayAssembly {

    Pane pane;

    /**
     * Constructor. Create a console display with a reference back to the printer.
     * @param printer The printer this display is showing information for.
     */
    public ScreenDisplay(LaserPrinter printer, Pane pane) {
        super(printer);
        pane.setPrefSize(400, 300);
        this.pane = pane;
        displayWindowOff();
    }

    /**
     * Trigger a re-print of the display's information. Only prints lights if the display is not on.
     */
    @Override
    public void refresh() {
        clearScreen();

        if(activated) {
            printMessages();
        }

        displayTonerWarningError();
        displayDrumWarningError();
        displayGeneralError();
        displayReadyState();

        displayWindow(currentWindow);
    }

    public void clearScreen() {
        for (int i = 0; i < 10; i++)
            System.out.println();

        //pane.getChildren().clear();
    }

    private void printMessages() {
        // Print message
        if (currentMessage != null) {
            System.out.println("MESSAGE");
            System.out.println("   " + currentMessage);
        }
    }


    @Override
    public void reportStatus() {
        clearScreen();

        System.out.println("PAPER TRAY");
        System.out.println("   " + printer.paperTray().getValue());

        System.out.println("PRINT QUEUE");
        System.out.println("   " + printer.queue().getValue());

        System.out.println("OUTPUT TRAY");
        System.out.println("   " + printer.outputTray().getValue());

        System.out.println("PRINT ASSEMBLY");
        System.out.println("   " + printer.printAssembly().getValue());

        System.out.println("FUSER TEMPERATURE");
        System.out.println("   " + printer.fuser().getValue());

        System.out.println("TONER");
        System.out.println("   " + printer.toner().getValue());
    }

    /**
     * Show the status of the queue.
     */
    @Override
    public void reportQueue() {
        if(activated) { // Display must be on to show messages
            clearScreen();
            System.out.println("QUEUE");
            System.out.println("   " + printer.queue().getValue());
        }
    }

    /**
     * Display toner warning/error.
     */
    @Override
    public void displayTonerWarningError() {
        setTonerLightState();
        System.out.println("TONER");
        System.out.println("   " + tonerLED);

        // Display must be on to show error messages
        if(activated && printer.containsErrorFor(printer.toner()) != null)
            System.out.println("   " + printer.containsErrorFor(printer.toner()).getMessage());
        if(activated && printer.toner().isWarning())
            System.out.println("   " + printer.toner().warning());
    }

    /**
     * Display drum warning/error.
     */
    @Override
    public void displayDrumWarningError() {
        setDrumLightState();
        System.out.println("DRUM");
        System.out.println("   " + drumLED);

        if(activated && printer.exceptions().containsKey(AssemblyException.PrinterIssue.DRUM))
            System.out.print(printer.exceptions().get(AssemblyException.PrinterIssue.DRUM).getMessage());
        if(activated && printer.printAssembly().isWarning())
            System.out.print(printer.printAssembly().drumWarning());
    }

    /**
     * Display general error (jam typically).
     */
    @Override
    public void displayGeneralError() {
        setErrorLightState();
        System.out.println("ERROR");
        System.out.println("   " + errorLED);

        if(activated) { // Display must be on to show error messages
            if (printer.isError()) {
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<AssemblyException.PrinterIssue, AssemblyException> entry : printer.exceptions().entrySet())
                    builder.append("   " + entry.getValue().getMessage() + "\n");
                System.out.print(builder.toString());
            }
        }
    }

    /**
     * Display powering up, printing, waiting.
     */
    @Override
    public void displayReadyState() {
        setReadyLightState();
        System.out.println("READY");
        System.out.println("   " + readyLED);
    }




    @Override
    protected void displayWindowOff() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    protected void displayWelcomeWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        Text greeting = new Text("Welcome");
        greeting.getStyleClass().add("white");
        greeting.getStyleClass().add("h1");
        Text info = new Text(currentMessage);
        pane.getChildren().addAll(greeting, info);
    }

    @Override
    protected void displayExitWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Exit Window"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayErrorWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Error Screen"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayPrintQueueWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Print Queue"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayFuserWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Fuser Screen"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayTonerAndDrumWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Toner and Drum Screen"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayPaperWindow() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        Text text = new Text("Paper Trays Screen"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }
}