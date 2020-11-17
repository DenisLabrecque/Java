import java.util.HashMap;

/**
 * Represents a printer's display. To connect to an actual display, this class must be extended by a subclass
 * that represents the screen. For purposes of this homework, that is either the console or JavaFX.
 * Denis Labrecque, November 2020
 */
public abstract class DisplayAssembly extends AssemblyUnit implements ISimAssembly {

    protected LaserPrinter printer;

    protected Light tonerLED = new Light(); // On if error, yellow if warning, flashing red if error
    protected Light drumLED  = new Light(); // On if error, yellow if warning, flashing red if error
    protected Light errorLED = new Light(); // On if error, flashing red
    protected Light readyLED = new Light(); // Flashing green while powering up or printing, solid green otherwise

    HashMap<String, Exception> exceptions = new HashMap<>();
    Exception currentException = null;
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
            printer.push("Display already activated.");
            return;
        }

        // Print a startup message, wait for this component to turn on
        try {
            Thread.sleep(300); // Time for the screen to turn on
            activated = true;
            printer.push("Welcome.");
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
            printer.push("Goodbye.");
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
     * Adds an exception that needs to be solved before the printer can continue printing.
     * The exception should not be added twice. The exception must contain a message.
     * Will not do anything if the display is not activated. Will refresh the screen.
     * @param exception The problem to solve.
     */
    public void addException(AssemblyException exception) {
        // Simulate the display's being off
        if(activated == false)
            return;

        if(exception != null) {
            // If this is the only exception, update the exception, otherwise print the other one first
            if(currentException == null)
                currentException = exception;

            if(exception.getMessage() == null || exception.getMessage().isEmpty())
                exceptions.put("Unknown error.", exception);
            else
                exceptions.put(exception.getMessage(), exception);
        }
        refresh();
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
     * @param message Message to display to the user. Exceptions may take precedence if necessary.
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
}
