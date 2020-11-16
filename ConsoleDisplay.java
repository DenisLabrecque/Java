/**
 * Takes information sent to the display and prints it in console format.
 * Denis Labrecque, November 2020
 */
public class ConsoleDisplay extends DisplayAssembly {
    private LaserPrinter printer;

    /**
     * Constructor. Create a console display with a reference back to the printer.
     * @param printer The printer this display is showing information for.
     */
    public ConsoleDisplay(LaserPrinter printer) {
        this.printer = printer;
    }

    /**
     * Trigger a re-print of the display's information. Will not do anything if the display is not activated.
     */
    @Override
    public void refresh() {
        if(activated == false)
            return;

        // Clear the screen
        for(int i = 0; i < 10; i++)
            System.out.println();

        // Print exceptions
        if(currentException == null)
            System.out.println("ERROR  : none.");
        else
            System.out.println("ERROR  :" + currentException.getMessage());

        // Print message
        if(currentMessage == null)
            System.out.println("MESSAGE: none.");
        else
            System.out.println("MESSAGE: " + currentMessage);

        // Print lights
        System.out.println("TONER: " + tonerLED);
        System.out.println("DRUM : " + drumLED);
        System.out.println("ERROR: " + errorLED);
        System.out.println("READY: " + readyLED);
    }
}