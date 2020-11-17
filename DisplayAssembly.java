/**
 * Represents a printer's display. To connect to an actual display, this class must be extended by a subclass
 * that represents the screen. For purposes of this homework, that is either the console or JavaFX.
 * Denis Labrecque, November 2020
 */
public abstract class DisplayAssembly extends AssemblyUnit implements ISimAssembly {

    protected LaserPrinter printer;

    protected Light tonerLED = new Light(); // Solid yellow if warning, flashing red if error
    protected Light drumLED  = new Light(); // Solid yellow if warning, flashing red if error
    protected Light errorLED = new Light(Light.Color.RED); // Flashing red if error
    protected Light readyLED = new Light(Light.Color.GREEN); // Solid green, flashing green while powering up or printing

    AssemblyException currentException = null;
    String currentWarning = null;
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
            pushMessage("Display already activated.");
            return;
        }

        // Print a startup message, wait for this component to turn on
        try {
            Thread.sleep(300); // Time for the screen to turn on
            activated = true;
            pushMessage("Welcome.");
            Thread.sleep(500); // Welcome screen time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Turn off this display. Does nothing if the display is already off.
     * @throws AssemblyException Will not throw.
     */
    @Override
    public void deactivate() throws AssemblyException {
        if(activated == false)
            return;

        try {
            pushMessage("Goodbye.");
            Thread.sleep(300);
            activated = false;
            refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setValue(int newValue) {  }

    @Override
    public int getValue() {
        return 0;
    }

    /**
     * Pass a warning message to the display. Will not do anything if the display is not activated. Will refresh the
     * screen with the new warning.
     * @param warning Message to display to the user. Other warnings may take precedence.
     */
    public void pushWarning(String warning) {
        if(activated == false)
            return;

        if(warning != null && warning.isEmpty() == false) {
            currentWarning = warning;
            refresh();
        }
    }

    /**
     * Pass a message to display. Will not do anything if the display is not activated. Will refresh the screen with
     * the new message.
     * @param message Message to display to the user.
     */
    public void pushMessage(String message) {
        if(activated == false)
            return;

        if(message != null && message.isEmpty() == false) {
            currentMessage = message;
            refresh();
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

    public abstract void resetDisplay();

    /**
     * Solid green if nothing, solid yellow if warning, flashing red if error.
     */
    protected void setTonerLightState() {
        if (printer.toner().isActive()) {
            if (printer.toner().isError()) {
                tonerLED.setColor(Light.Color.RED);
                tonerLED.setPattern(Light.Pattern.FLASHING);
            }
            else if (printer.toner().isWarning()) {
                tonerLED.setColor(Light.Color.YELLOW);
                tonerLED.setPattern(Light.Pattern.SOLID);
            }
            else {
                tonerLED.setColor(Light.Color.GREEN);
                tonerLED.setPattern(Light.Pattern.SOLID);
            }
        }
        else
            tonerLED.setPattern(Light.Pattern.OFF);
    }

    /**
     *  Solid green if nothing, solid yellow if warning, flashing red if error.
     */
    protected void setDrumLightState() {
        if (printer.printAssembly().drumIsActive()) {
            if (printer.printAssembly().isError()) {
                drumLED.setColor(Light.Color.RED);
                drumLED.setPattern(Light.Pattern.FLASHING);
            } else if (printer.printAssembly().isWarning()) {
                drumLED.setColor(Light.Color.YELLOW);
                drumLED.setPattern(Light.Pattern.SOLID);
            } else {
                drumLED.setColor(Light.Color.GREEN);
                drumLED.setPattern(Light.Pattern.SOLID);
            }
        }
        else
            drumLED.setPattern(Light.Pattern.OFF);
    }

    /**
     * Flashing red when there is an error.
     */
    protected void setErrorLightState() {
        if (!printer.isOn())
            errorLED.setPattern(Light.Pattern.OFF);
        if (printer.isError())
            errorLED.setPattern(Light.Pattern.FLASHING);
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
}