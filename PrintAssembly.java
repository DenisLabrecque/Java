public class PrintAssembly extends AssemblyUnit implements ISimAssembly {

    LaserPrinter printer;
    AssemblyException.PrinterIssue issue = null;

    /**
     * Constructor.
     * @param printer Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public PrintAssembly(LaserPrinter printer) {
        super();
        this.printer = printer;
    }

    @Override
    public void activate() throws AssemblyException {
        if(issue != null) // The issue was already present as the printer shut down, before activating again
            throw new AssemblyException(issue, this);

        try {
            printer.push("Starting print assembly.");
            Thread.sleep(1000);
            activated = true;
            printer.push("Print assembly ready.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate() throws AssemblyException {

    }

    @Override
    public void setValue(int newValue) {

    }

    @Override
    public int getValue() {
        return 0;
    }

    /**
     * Makes the drum brand new (no longer used).
     */
    public void replaceDrum() {
    }

    /**
     * @return Current exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }

    public boolean drumIsActive() {
        return activated; // TODO
    }

    public boolean isWarning() {
        return false; // TODO
    }
}
