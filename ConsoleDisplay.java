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

        System.out.println("FUSER");
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
        // TODO warnings
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
        // TODO warnings
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
        // TODO warnings?
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
}