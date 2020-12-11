import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;

/**
 * Takes information sent to the display and prints it to the screen.
 * The screen is a JavaFX pane.
 * Denis Labrecque, November 2020
 */
public class ScreenDisplay extends DisplayAssembly {

    Pane pane;
    Circle tonerLight;
    Circle drumLight;
    Circle errorLight;
    Circle readyLight;
	
	int fuserLevel = 65;
	
	XYChart.Series series = new XYChart.Series();
	

    /**
     * Constructor. Create a console display with a reference back to the printer.
     * @param printer The printer this display is showing information for.
     */
    public ScreenDisplay(LaserPrinter printer, Pane screen) {
        super(printer);
        screen.setPrefSize(400, 300);
        this.pane = screen;
        displayWindowOff();
    }

    /**
     * Passes in the different JavaFX LEDs so they can be updated and animated by the display.
     * @param tonerLight
     * @param drumLight
     * @param errorLight
     * @param readyLight
     */
    public void addLights(Circle tonerLight, Circle drumLight, Circle errorLight, Circle readyLight) {
        System.out.println("DEBUG: lights added");
        this.tonerLight = tonerLight;
        this.drumLight = drumLight;
        this.errorLight = errorLight;
        this.readyLight = readyLight;
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

        System.out.println("DEBUG: refresh() called and displaying window ->");
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
     * Display toner warning/error LED.
     * Yellow if warning, flashing red if error.
     */
    @Override
    public void displayTonerWarningError() {
        setTonerLightState();
        setLED(tonerLED, tonerLight);
        System.out.println("TONER");
        System.out.println("   " + tonerLED);

        // Display must be on to show error messages
        if(activated && printer.containsErrorFor(printer.toner()) != null)
            System.out.println("   " + printer.containsErrorFor(printer.toner()).getMessage());
        if(activated && printer.toner().isWarning())
            System.out.println("   " + printer.toner().warning());
    }

    /**
     * Display drum warning/error LED.
     * Yellow if warning, flashing red if error.
     */
    @Override
    public void displayDrumWarningError() {
        setDrumLightState();
        setLED(drumLED, drumLight);
        System.out.println("DRUM");
        System.out.println("   " + drumLED);

        if(activated && printer.exceptions().containsKey(AssemblyException.PrinterIssue.DRUM))
            System.out.print(printer.exceptions().get(AssemblyException.PrinterIssue.DRUM).getMessage());
        if(activated && printer.printAssembly().isWarning())
            System.out.print(printer.printAssembly().drumWarning());
    }

    /**
     * Display general error LED (jam typically).
     * Flashing red if error.
     */
    @Override
    public void displayGeneralError() {
        setErrorLightState();
        setLED(errorLED, errorLight);
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
     * Display powering up, printing, waiting LED.
     * Flashing green while powering up or printing, solid green otherwise.
     */
    @Override
    public void displayReadyState() {
        setReadyLightState();
        setLED(readyLED, readyLight);
        System.out.println("READY");
        System.out.println("   " + readyLED);
    }

    /**
     * Set the JavaFX circle that represents the LED to match its object representation.
     * @param lightObject Object that contains what the light's pattern and color should be
     * @param circle JavaFX light that represents a real-world LED
     */
    private void setLED(Light lightObject, Circle circle) {
        if(lightObject.pattern() == Light.Pattern.OFF) {
            circle.setFill(Color.WHITE);
            return;
        }
        else {
            circle.setFill(lightObject.color());
        }


//        // Flashing transition
//        if(lightObject.pattern() == Light.Pattern.FLASHING) {
//            FillTransition transition = new FillTransition(Duration.millis(1000), circle, lightObject.color(), Color.WHITE);
//            transition.setCycleCount(Timeline.INDEFINITE);
//            transition.setAutoReverse(true);
//            transition.play();
//        }
//        else {
//            FillTransition transition = new FillTransition(Duration.ZERO, circle, lightObject.color(), lightObject.color());
//            transition.setCycleCount(1);
//            transition.play();
//            transition.stop();
//        }
    }


    @Override
    protected void displayWindowOff() {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    protected void displayWelcomeWindow() {
        clearWithColor(Color.BLACK);
        Label greeting = new Label("Laser Printer");
        greeting.getStyleClass().add("white");
        greeting.getStyleClass().add("h1");

        Label label1 = new Label("A project presented by:");
        label1.getStyleClass().add("white");
        Label label2 = new Label("Joel Luther");
        label2.getStyleClass().add("white");
        Label label3 = new Label("Cameron Keefe");
        label3.getStyleClass().add("white");
        Label label4 = new Label("Denis Labrecque");
        label4.getStyleClass().add("white");
        Label label5 = new Label("Hendy Nathaniel");
        label5.getStyleClass().add("white");
        Label label6 = new Label("Zahin Uriostegui");
        label6.getStyleClass().add("white");
        pane.getChildren().addAll(greeting, label1, label2, label3, label4, label5, label6);
    }

    @Override
    protected void displayExitWindow() {
        clearWithColor(Color.WHITE);
        Text text = new Text("Exit Window"); // TODO this is just a stub; put a panel here with your graphics
        pane.getChildren().add(text);
    }

    @Override
    protected void displayErrorWindow() {
        clearWithColor(Color.ORANGE);
		Label title = new Label("Error Screen");
		//title.getStyleClass().add("white");
        title.getStyleClass().add("h1");
		
		Label error = new Label();
		//error.getStyleClass().add("white");
		if(printer.exceptions().isEmpty()) {
			error.setText("There is no error.");
		}
		else {
			StringBuilder builder = new StringBuilder();
			for (Map.Entry<AssemblyException.PrinterIssue, AssemblyException> entry : printer.exceptions().entrySet())
				builder.append("   " + entry.getValue().getMessage() + "\n");
			error.setText(builder.toString());			
		}
		
	
		
		Button clearErrorsButton = new Button("Clear Errors");
		clearErrorsButton.getStyleClass().add("queueButton");
		
		clearErrorsButton.setOnAction(e -> {
			printer.display().reset();
			//setLED(errorLED, errorLight);
			displayErrorWindow();
			refresh();
		});
		
        pane.getChildren().addAll(title, clearErrorsButton, error);
    }

    @Override
    protected void displayPrintQueueWindow() {
		
		int totalQueue = printer.queue().getValue();
		
		clearWithColor(Color.ORANGE);
		Label title = new Label("Print Queue");
		//title.getStyleClass().add("white");
        title.getStyleClass().add("h1");
		
		Label totalQueueLabel = new Label("Total Queue : " + Integer.toString(totalQueue));
		//totalQueueLabel.getStyleClass().add("white");
		
		/* Print, Cancel, Add, and Clear Button */
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setPadding(new Insets(10, 10, 15, 20));
		
		Button printButton = new Button("Print");
		printButton.getStyleClass().add("queueButton");
		grid.add(printButton, 1, 0, 1, 1);
		printButton.setOnAction(e -> {
			printer.printJob();
			refresh();
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.getStyleClass().add("queueButton");
		grid.add(cancelButton, 3, 0, 1, 1);
		cancelButton.setOnAction(e -> {
			if(printer.queue().getValue() != 0)
				printer.queue().remove(printer.queue().getID(0));
			refresh();
		});
		
		Button addButton = new Button("Add");
		addButton.getStyleClass().add("queueButton");
		grid.add(addButton, 2, 0, 1, 1);
		addButton.setOnAction(e -> displayAddQueueWindow());
		
		Button clearButton = new Button("Clear");
		clearButton.getStyleClass().add("queueButton");
		grid.add(clearButton, 4, 0, 1, 1);
		clearButton.setOnAction(e -> {
			printer.queue().clearQueue();
			refresh();
		});
		
		pane.getChildren().addAll(title, totalQueueLabel, grid);
		
		Label errorPrint = new Label();
		if(!printer.exceptions().isEmpty()) {
		    StringBuilder errors = new StringBuilder();
		    for(AssemblyException.PrinterIssue issue : printer.exceptions().keySet()) {
		        errors.append(printer.exceptions().get(issue).getMessage());
            }
			errorPrint.setText(errors.toString());
			errorPrint.getStyleClass().add("red");
			pane.getChildren().add(errorPrint);
		}
		
		Label queueEmpty = new Label();
		//queueEmpty.getStyleClass().add("white");
		if(totalQueue == 0) {
			queueEmpty.setText("There is no queue.");
			pane.getChildren().add(queueEmpty);
		}
		else {
			GridPane queueGrid = new GridPane();
			Label[] separateQueue = new Label[totalQueue];
			Label[] queueID = new Label[totalQueue];
			Label[] queueName = new Label[totalQueue];
			Label[] queuePages = new Label[totalQueue];
			
			for(int i = 0; i < totalQueue; i++)
			{
				separateQueue[i] = new Label("*****************************************************");
				//separateQueue[i].getStyleClass().add("white");


				queueID[i] = new Label("ID: " + Integer.toString(printer.queue().getID(i)));
				//queueID[i].getStyleClass().add("white");

				queueName[i] = new Label("Name: " + printer.queue().getName(i));
				//queueName[i].getStyleClass().add("white");
								
				queuePages[i] = new Label("Pages: " + printer.queue().getPages(i));
				//queuePages[i].getStyleClass().add("white");

				
				pane.getChildren().addAll(separateQueue[i], queueID[i], queueName[i], queuePages[i]);
			}
		}
    }

    @Override
    protected void displayFuserWindow() {
        Stage stageTwo = new Stage();
		
		Pane rootTwo = new Pane();
		Scene sceneTwo = new Scene(rootTwo, 750, 500);
		
		stageTwo.setScene(sceneTwo);
		
        clearWithColor(Color.ORANGE);
		Label fscreenHeader = new Label("Fuser Screen");
        fscreenHeader.getStyleClass().add("black");
		fscreenHeader.getStyleClass().add("h1");
        rootTwo.getChildren().add(fscreenHeader);
		
		CategoryAxis fxAxis = new CategoryAxis();
		fxAxis.setLabel("Temperature");

		final NumberAxis fyAxis = new NumberAxis(10, 410, 25);

		fyAxis.setLabel("Degrees");
		
		BarChart barChart1 = new BarChart(fxAxis, fyAxis);
		
		series.getData().clear();
		
		series.getData().add(new XYChart.Data("Fuser", printer.fuser().getValue()));
		
		barChart1.getData().add(series);
		
		Button normalFuserButton = new Button("Normal");
		normalFuserButton.setOnAction( e -> normalFuserAction());
		Button thickFuserButton = new Button("Thick");
		thickFuserButton.setOnAction(e -> thickFuserAction());
		
		HBox flevelButtons = new HBox();
		flevelButtons.setSpacing(50);
		flevelButtons.setAlignment(Pos.CENTER);
		flevelButtons.getChildren().addAll(normalFuserButton, thickFuserButton);
		
		VBox flevelLayout = new VBox(10);
		flevelLayout.setPadding(new Insets(5, 50, 5, 5));
		flevelLayout.getChildren().addAll(barChart1, flevelButtons);
		
		HBox displayElements1 = new HBox();
		displayElements1.setSpacing(10);
		displayElements1.setAlignment(Pos.CENTER);
		displayElements1.getChildren().addAll(flevelLayout);
		

		
		
		
		pane.getChildren().addAll(fscreenHeader, flevelButtons, flevelLayout, displayElements1);
    }

    @Override
    protected void displayTonerAndDrumWindow() {
		
        clearWithColor(Color.ORANGE);
		Label screenHeader = new Label("Toner and Drum Levels");
        screenHeader.getStyleClass().add("black");
		screenHeader.getStyleClass().add("h1");
		
		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Levels");
		
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Quanity %");
		
		BarChart barChart = new BarChart(xAxis, yAxis);
		
		series.getData().clear();
		
		series.getData().add(new XYChart.Data("Toner", printer.toner().getValue()));
		series.getData().add(new XYChart.Data("Drum", printer.printAssembly().getValue()));
		
		barChart.getData().add(series);
		
		Button replaceTonerButton = new Button("Replace Toner");
		replaceTonerButton.setOnAction( e -> replaceTonerAction());
		Button replaceDrumButton = new Button("Replace Drum");
		replaceDrumButton.setOnAction(e -> replaceDrumAction());
		
		HBox levelButtons = new HBox();
		levelButtons.setSpacing(50);
		levelButtons.setAlignment(Pos.CENTER);
		levelButtons.getChildren().addAll(replaceTonerButton, replaceDrumButton);
		
		VBox levelLayout = new VBox(10);
		levelLayout.setPadding(new Insets(5, 5, 5, 50));
		levelLayout.getChildren().addAll(barChart, levelButtons);
		
		HBox displayElements = new HBox();
		displayElements.setSpacing(10);
		displayElements.setAlignment(Pos.CENTER);
		displayElements.getChildren().addAll(levelLayout);
		
		pane.getChildren().addAll(screenHeader, levelButtons, levelLayout, displayElements);
    }

    @Override
    protected void displayPaperWindow() {
        clearWithColor(Color.ORANGE);
        
        // Input Paper Tray
        Text title = new Text("Input Paper Tray");
        title.getStyleClass().add("h1");
        HBox hbox = new HBox();
        HBox hboxPaper = new HBox();
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(0, 20, 20, 20));
        vbox.setSpacing(5);
        hboxPaper.setPadding(new Insets(20, 20, 20, 0));
        hboxPaper.setSpacing(10);
        
        Text pageCount = new Text("Paper Count = " + printer.paperTray().getValue());
        HBox hboxVisual = new HBox();
        HBox hboxFullVisual = new HBox();
        Rectangle paperVisual = new Rectangle(200, 200, 150, printer.paperTray().getValue() / 20);
        Rectangle paperFullVisual = new Rectangle(200, 200, 5, 50);
        hboxVisual.setAlignment(Pos.BOTTOM_CENTER);
        hboxFullVisual.setAlignment(Pos.BOTTOM_CENTER);
        paperVisual.fillProperty().set(Color.BEIGE);
        paperVisual.strokeProperty().set(Color.BLACK);
        
        // Adds paper to the printer
        Button addPaperButton = new Button("Add paper");
        addPaperButton.setPrefSize(100, 20);
        addPaperButton.setOnAction(e -> {printer.paperTray().addPaper(20); displayPaperWindow();});
        
        // Unjams the printer and refreshes the error leds
        Button unjamPrinterButton = new Button("Unjam Printer");
        unjamPrinterButton.setPrefSize(100, 20);
        unjamPrinterButton.setOnAction(e -> {printer.paperTray().unjam(); printer.display().reset(); refresh();});
        
        hboxVisual.getChildren().addAll(paperVisual);
        hboxFullVisual.getChildren().addAll(paperFullVisual);
        vbox.getChildren().addAll(pageCount, addPaperButton, unjamPrinterButton);
        hboxPaper.getChildren().addAll(hboxFullVisual, hboxVisual);
        hbox.getChildren().addAll(hboxPaper, vbox);
        
        
        // Output Paper Tray
        Text title2 = new Text("Output Paper Tray");
        title2.getStyleClass().add("h1");
        HBox hbox2 = new HBox();
        HBox hboxPaper2 = new HBox();
        VBox vbox2 = new VBox();
        vbox2.setPadding(new Insets(0, 20, 20, 20));
        vbox2.setSpacing(5);
        hboxPaper2.setPadding(new Insets(20, 20, 20, 0));
        hboxPaper2.setSpacing(10);
        
        Text pageCount2 = new Text("Paper Count = " + printer.outputTray().getValue());
        HBox hboxVisual2 = new HBox();
        Rectangle paperVisual2 = new Rectangle(200, 200, 150, printer.outputTray().getValue() / 20);
        Rectangle paperFullVisual2 = new Rectangle(200, 200, 5, 50);
        hboxVisual2.setAlignment(Pos.BOTTOM_LEFT);
        paperVisual2.fillProperty().set(Color.BEIGE);
        paperVisual2.strokeProperty().set(Color.BLACK);
        
        
        // Remove printed paper from the output tray
        Button removePaperButton = new Button("Empty paper");
        removePaperButton.setPrefSize(100, 20);
        removePaperButton.setOnAction(e -> {printer.outputTray().emptyTray(); displayPaperWindow();});
        
        hboxVisual2.getChildren().addAll(paperVisual2);
        vbox2.getChildren().addAll(pageCount2, removePaperButton);
        hboxPaper2.getChildren().addAll(paperFullVisual2, hboxVisual2);
        hbox2.getChildren().addAll(hboxPaper2, vbox2);
        
        pane.getChildren().addAll(title, hbox, title2, hbox2);
    }

    /**
     * Empty the window of any contents, and change the background color.
     * @param color Black if the printer is supposed to be off, and white if the printer is on.
     */
    private void clearWithColor(Color color) {
        System.out.println("DEBUG: window " + currentWindow);
        pane.getChildren().clear();
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
   
	
	private void replaceTonerAction() {
		printer.toner().refill();
		series.getData().clear();
		series.getData().add(new XYChart.Data("Toner", printer.toner().getValue()));
		series.getData().add(new XYChart.Data("Drum", printer.printAssembly().getValue()));
		refresh();
	}
	
	private void replaceDrumAction() {
		printer.printAssembly().replaceDrum();
		series.getData().clear();
		series.getData().add(new XYChart.Data("Toner", printer.toner().getValue()));
		series.getData().add(new XYChart.Data("Drum", printer.printAssembly().getValue()));
		refresh();
	}
	
	private void normalFuserAction() {
		fuserLevel = 240;
		series.getData().clear();
		series.getData().add(new XYChart.Data("Fuser", fuserLevel));
	}
	private void thickFuserAction() {
		fuserLevel = 350;
		series.getData().clear();
		series.getData().add(new XYChart.Data("Fuser", fuserLevel));
	}
	private void displayAddQueueWindow() {
		pane.getChildren().clear();
		
		Label title = new Label("Add To Queue");
		//title.getStyleClass().add("white");
        title.getStyleClass().add("h1");
		
		
		Label labelName = new Label("Name: ");
		//labelName.getStyleClass().add("white");
		TextField fieldName = new TextField();

		Label labelPage = new Label("Pages: ");
		//labelPage.getStyleClass().add("white");
		TextField fieldPage = new TextField();
		
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.BOTTOM_CENTER);
        grid.setPadding(new Insets(10, 10, 15, 20));
				
		Button cancel = new Button("Cancel");
		cancel.getStyleClass().add("queueButton");
		grid.add(cancel, 2, 0, 1, 1);
		cancel.setOnAction(e -> refresh());
		
		Button add = new Button("Add");
		add.getStyleClass().add("queueButton");
		grid.add(add, 1, 0, 1, 1);
		add.setOnAction(e -> {
			if(!fieldName.getText().isEmpty() && !fieldPage.getText().isEmpty()) {
				try {
				printer.addJob(fieldName.getText(), Integer.parseInt(fieldPage.getText()));
				refresh();
				}
				catch (Exception NumberFormatException) {
					labelPage.getStyleClass().add("red");
					labelPage.setText("Pages: Please enter a valid number");
				}
			}
		});
		
		
		pane.getChildren().addAll(title, labelName, fieldName, labelPage, fieldPage, grid);
	}
}