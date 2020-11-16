import java.util.HashMap;

/**
 * Represents a printer's display. To connect to an actual display, this class must be extended by a subclass
 * that represents the screen. For purposes of this homework, that is either the console or JavaFX.
 * Denis Labrecque, November 2020
 */
public abstract class DisplayAssembly extends AssemblyUnit implements ISimAssembly {

    private Light tonerLED = new Light(); // On if error, yellow if warning, flashing red if error
    private Light drumLED  = new Light(); // On if error, yellow if warning, flashing red if error
    private Light errorLED = new Light(); // On if error, flashing red
    private Light readyLED = new Light(); // Flashing green while powering up or printing, solid green otherwise

    HashMap<String, Exception> exceptions = new HashMap<>();
    Exception currentException = null;
    String currentMessage = null;

    @Override
    public void activate() throws AssemblyException {
        // TODO check if already activated
        // Print a startup message, wait for this component to turn on
        try {
            Thread.sleep(300); // Time for the screen to turn on
            activated = true;
            push("Welcome.");
            Thread.sleep(500); // Welcome screen time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate() throws AssemblyException {
        activated = false;
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

        if(exception.getMessage() != null && exception.getMessage().isEmpty() == false) {
            // If this is the only exception, update the exception, otherwise print the other one first
            if(currentException == null)
                currentException = exception;
            exceptions.put(exception.getMessage(), exception);
        }
        refresh();
    }

    /**
     * Pass a message to display. Will not do anything if the display is not activated. Will refresh the screen with
     * the new message.
     * @param message Message to display to the user. Exceptions may take precedence if necessary.
     */
    public void push(String message) {
        if(activated == false)
            return;

        if(message != null && message.isEmpty() == false) {
            currentMessage = message;
            refresh();
        }
    }

    /**
     * Trigger a re-print of the display's information. Will not do anything if the display is not activated.
     */
    public abstract void refresh();
}
