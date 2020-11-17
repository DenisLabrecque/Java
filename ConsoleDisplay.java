/**
 * Takes information sent to the display and prints it in console format.
 * Denis Labrecque, November 2020
 */
public class ConsoleDisplay extends DisplayAssembly {

    /**
     * Constructor. Create a console display with a reference back to the printer.
     * @param printer The printer this display is showing information for.
     */
    public ConsoleDisplay(LaserPrinter printer) {
        super(printer);
    }

    /**
     * Trigger a re-print of the display's information. Only prints lights if the display is not on.
     */
    @Override
    public void refresh() {
        clearScreen();

        if(activated) {
            printExceptions();
            printWarnings();
            printMessages();
        }

        printLights();
    }

    public void clearScreen() {
        for (int i = 0; i < 10; i++)
            System.out.println();
    }

    private void printExceptions() {
        // Print exceptions
        if (currentException != null)
            System.out.println("ERROR: " + currentException.getMessage());
    }

    private void printWarnings() {
        // Print warning
        if (currentWarning != null)
            System.out.println("WARNING: " + currentWarning);
    }

    private void printMessages() {
        // Print message
        if (currentMessage != null)
            System.out.println("MESSAGE: " + currentMessage);
    }

    private void printLights() {
        // Set light state
        setReadyLightState();
        setTonerLightState();

        // Print lights
        System.out.println("TONER: " + tonerLED);
        System.out.println("DRUM : " + drumLED);
        System.out.println("ERROR: " + errorLED);
        System.out.println("READY: " + readyLED);
    }

    private void setReadyLightState() {
        if (printer.isPowering())
            readyLED.switchTo(Light.Pattern.FLASHING);
        else if (printer.isOn())
            readyLED.switchTo(Light.Pattern.SOLID);
        else
            readyLED.switchTo(Light.Pattern.OFF);
    }

    private void setTonerLightState() {
        if (printer.toner().isActive()) {
            tonerLED.switchTo(Light.Pattern.SOLID);
            if (printer.toner().isError())
                tonerLED.setColor(Light.Color.RED);
            else if (printer.toner().isWarning())
                tonerLED.setColor(Light.Color.YELLOW);
            else
                tonerLED.setColor(Light.Color.GREEN);
        }
        else
            tonerLED.switchTo(Light.Pattern.OFF);
    }

    private void setDrumLightState() {
        //if(printer.printAssembly().isActive())
    }

    @Override
    public void reportStatus() {
        // Status
        System.out.println("PAPER TRAY: " + printer.paperTray().getValue());
        System.out.println("PRINT QUEUE: " + printer.queue().getValue());
        System.out.println("OUTPUT TRAY: " + printer.outputTray().getValue());
        System.out.println("PRINT ASSEMBLY: " + printer.printAssembly().getValue());
        System.out.println("FUSER: " + printer.fuser().getValue());
        System.out.println("TONER: " + printer.toner().getValue());
    }

    /**
     * Show the status of the queue.
     */
    @Override
    public void reportQueue() {
        if(activated) {
            String queue = "QUEUE: " + printer.queue().getValue();
            pushMessage(queue);
        }
    }
}