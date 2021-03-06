import javafx.scene.paint.Color;

/**
 * Represents a printer's display. To connect to an actual display, this class must be extended by a subclass
 * that represents the screen. For purposes of this homework, that is either the console or JavaFX.
 * Denis Labrecque, November 2020
 */
public abstract class DisplayAssembly extends AssemblyUnit implements ISimAssembly {

    public enum Window {
        OFF,
        WELCOME_WINDOW,
        EXIT,
        PAPER_TRAYS,
        TONER_AND_DRUM,
        FUSER,
        PRINT_QUEUE,
        ERRORS;
    }

    protected LaserPrinter printer;

    protected Light tonerLED = new Light(); // Solid yellow if warning, flashing red if error
    protected Light drumLED  = new Light(); // Solid yellow if warning, flashing red if error
    protected Light errorLED = new Light(Color.RED); // Flashing red if error
    protected Light readyLED = new Light(Color.GREEN); // Solid green, flashing green while powering up/printing

    Window currentWindow = Window.OFF;
    String currentMessage = null;

    /**
     * Constructor. Create a console display with a reference back to the printer.
     * @param printer The printer this display is showing information for.
     */
    public DisplayAssembly(LaserPrinter printer) {
        this.printer = printer;
    }

    /**
     * Turn on this display. Does nothing if the display is already on.
     * @throws AssemblyException Will not throw.
     */
    @Override
    public void activate() throws AssemblyException {
        if(activated == true) {
            pushMessage("DEBUG: display already activated.");
            return;
        }

        activated = true;
        displayWindow(Window.WELCOME_WINDOW);
    }

    /**
     * Turn off this display. Does nothing if the display is already off.
     * @throws AssemblyException Will not throw.
     */
    @Override
    public void deactivate() throws AssemblyException {
        if(activated) {
            displayWindow(Window.EXIT);
            displayWindow(Window.OFF);
            activated = false;
        }
    }

    @Override
    public void setValue(int newValue) {  }

    @Override
    public int getValue() {
        return 0;
    }

	public boolean isActive() {
		return activated;
	}

    /**
     * Pass a message to display. Will not do anything if the display is not activated. Refreshes the screen with the
     * new message.
     * @param message Message to display to the user.
     */
    public void pushMessage(String message) {
        if(activated) {

            if (message != null && message.isEmpty() == false) {
                currentMessage = message;
                refresh();
            }
        }
    }

    /**
     * Trigger a re-print of the display's information. Should not do anything if the display is not activated.
     */
    public abstract void refresh();

    /**
     * Show the status of the printer as a whole.
     */
    public abstract void reportStatus();

    /**
     * Show the status of the queue.
     */
    public abstract void reportQueue();

    public abstract void displayTonerWarningError();

    public abstract void displayDrumWarningError();

    public abstract void displayGeneralError();

    public abstract void displayReadyState();

    /**
     * Solid green if nothing, solid yellow if warning, flashing red if error.
     */
    protected void setTonerLightState() {
        if (printer.toner().isActive()) {
            if (printer.toner().isError()) {
                tonerLED.setColor(Color.RED);
                tonerLED.setPattern(Light.Pattern.FLASHING);
            }
            else if (printer.toner().isWarning()) {
                tonerLED.setColor(Color.YELLOW);
                tonerLED.setPattern(Light.Pattern.SOLID);
            }
            else
				tonerLED.setPattern(Light.Pattern.OFF);                
        }
        else
            tonerLED.setPattern(Light.Pattern.OFF);
    }

    /**
     *  Solid green if nothing, solid yellow if warning, flashing red if error.
     */
    protected void setDrumLightState() {
        if (printer.printAssembly().drumIsActive()) {
            if (printer.exceptions().containsKey(AssemblyException.PrinterIssue.DRUM)) {
                drumLED.setColor(Color.RED);
                drumLED.setPattern(Light.Pattern.FLASHING);
            } else if (printer.printAssembly().isWarning()) {
                drumLED.setColor(Color.YELLOW);
                drumLED.setPattern(Light.Pattern.SOLID);
            } else
                drumLED.setPattern(Light.Pattern.OFF);
        }
        else
            drumLED.setPattern(Light.Pattern.OFF);
    }

    /**
     * Flashing red when there is an error.
     */
    protected void setErrorLightState() {
        if (printer.isOnOrPowering()) {
            if (printer.isError())
                errorLED.setPattern(Light.Pattern.FLASHING);
            else
                errorLED.setPattern(Light.Pattern.OFF);
        }
        else
            errorLED.setPattern(Light.Pattern.OFF);
    }

    /**
     * Solid green if on, flashing green while powering up or printing.
     */
    protected void setReadyLightState() {
        if (printer.isPowering() || printer.isPrinting())
            readyLED.setPattern(Light.Pattern.FLASHING);
        else if (printer.isOn())
            readyLED.setPattern(Light.Pattern.SOLID);
        else
            readyLED.setPattern(Light.Pattern.OFF);
    }

    /**
     * Remove errors the printer has flagged. (Note that if they have not been properly resolved, they may get reported
     * again by the printer). Also clear the current message.
     * Replaces the print assembly drum.
     */
    protected void reset() {
        printer.exceptions().clear();
        currentMessage = null;
    }

    /**
     * Display a certain window.
     * @param window The window to display.
     */
    public void displayWindow(ScreenDisplay.Window window) {
        currentWindow = window;
        switch(window) {
            case OFF:
                displayWindowOff();
                return;
            case WELCOME_WINDOW:
                displayWelcomeWindow();
                return;
            case EXIT:
                displayExitWindow();
            case PAPER_TRAYS:
                displayPaperWindow();
                return;
            case TONER_AND_DRUM:
                displayTonerAndDrumWindow();
                return;
            case FUSER:
                displayFuserWindow();
                return;
            case PRINT_QUEUE:
                displayPrintQueueWindow();
                return;
            case ERRORS:
                displayErrorWindow();
                return;
        }
    }

    protected abstract void displayWindowOff();

    protected abstract void displayExitWindow();

    protected abstract void displayErrorWindow();

    protected abstract void displayPrintQueueWindow();

    protected abstract void displayFuserWindow();

    protected abstract void displayTonerAndDrumWindow();

    protected abstract void displayPaperWindow();

    protected abstract void displayWelcomeWindow();
}