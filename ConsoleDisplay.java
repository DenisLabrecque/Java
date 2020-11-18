import java.util.Map;

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
            //printExceptions();
            //printWarnings();
            printMessages();
        }

        displayTonerWarningError();
        displayDrumWarningError();
        displayGeneralError();
        displayReadyState();
    }

    public void clearScreen() {
        for (int i = 0; i < 10; i++)
            System.out.println();
    }

    private void printExceptions() {
        // Print exceptions
        if (printer.exceptions().size() > 0) {
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<AssemblyException.PrinterIssue, AssemblyException> entry : printer.exceptions().entrySet())
                builder.append("   " + entry.getValue().getMessage() + "\n");
            System.out.println(builder.toString());
        }
    }

    private void printWarnings() {
        // Print warning
        // TODO find a good way of making warnings
        //if (currentWarning != null)
        //    System.out.println("WARNING: " + currentWarning);
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

    /**
     * Display toner warning/error.
     */
    @Override
    public void displayTonerWarningError() {
        setTonerLightState();

        System.out.println();
        System.out.println("TONER");
        System.out.println("   " + tonerLED); // TODO warning/error message
    }

    /**
     * Display drum warning/error.
     */
    @Override
    public void displayDrumWarningError() {
        setDrumLightState();

        System.out.println();
        System.out.println("DRUM");
        System.out.println("   " + drumLED);
        if(printer.exceptions().containsKey(AssemblyException.PrinterIssue.DRUM))
            System.out.print(printer.exceptions().get(AssemblyException.PrinterIssue.DRUM).getMessage());
    }

    /**
     * Display general error (jam typically).
     */
    @Override
    public void displayGeneralError() {
        setErrorLightState();

        System.out.println();
        System.out.println("ERROR");
        System.out.println("   " + errorLED);
        if(printer.isError()) {
            StringBuilder builder = new StringBuilder();
            for(Map.Entry<AssemblyException.PrinterIssue, AssemblyException> entry : printer.exceptions().entrySet())
                builder.append("   " + entry.getValue().getMessage() + "\n");
            System.out.print(builder.toString());
        }
    }

    /**
     * Display powering up, printing, waiting.
     */
    @Override
    public void displayReadyState() {
        setReadyLightState();

        System.out.println();
        System.out.println("STATUS");
        System.out.println("   " + readyLED);
    }
}