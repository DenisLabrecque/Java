public class TonerAssembly extends AssemblyUnit implements ISimAssembly {

    LaserPrinter printer;
    AssemblyException exception = null;
    int tonerLvl = 0;
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
        if(exception != null) // The issue was already present as the printer shut down, before activating again
            throw exception;

        try {
            printer.push("Reading toner values.");
            Thread.sleep(100);
            activated = true;
            printer.push("Toner ready.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new AssemblyException(AssemblyException.PrinterIssue.TONER, this);
    }

    @Override
    public void deactivate() throws AssemblyException {

    }

    @Override
    public void setValue(int newValue) {
    	tonerLvl = newValue;
    }

    @Override
    public int getValue() {
        return tonerLvl;
    }


    /**
     * Retrieve the exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return exception;
    }

    public boolean isWarning() {
        return false; // TODO add a warning string that returns false when null and true when filled
    }

    /**
     * Set levels back to 100%.
     */
    public void refill() {
        setValue(100);
    }

    /**
     * Property.
     * @return Warning message.
     */
    public String warning() {
        return "Toner almost empty.";
    }
}
