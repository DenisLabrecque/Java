public class TonerAssembly extends AssemblyUnit implements ISimAssembly {

    LaserPrinter printer;
    AssemblyException.PrinterIssue issue = null;

    /**
     * Constructor.
     * @param printer Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public TonerAssembly(LaserPrinter printer) {
        super();
        this.printer = printer;
    }

    @Override
    public void activate() throws AssemblyException {
        if(issue != null) // The issue was already present as the printer shut down, before activating again
            throw new AssemblyException(issue, this);

        try {
            printer.push("Reading toner values.");
            Thread.sleep(100);
            activated = true;
            printer.push("Toner ready.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new AssemblyException(AssemblyException.PrinterIssue.GENERAL, this);
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
     * Adds toner according to the assigned quantities in grams.
     * @param cyan
     * @param magenta
     * @param yellow
     * @param black
     */
    public void addToner(float cyan, float magenta, float yellow, float black) {
    }

    /**
     * Retrieve the exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }

    public boolean isWarning() {
        return false; // TODO add a warning string that returns false when null and true when filled
    }

    /**
     * Set levels back to 100%.
     */
    public void refill() {
        // TODO
    }
}
