public class FuserAssembly extends AssemblyUnit implements ISimAssembly {

    LaserPrinter printer;
    AssemblyException.PrinterIssue issue = null;

    /**
     * Constructor.
     * @param printer Reference back to the printer this fuser assembly is part of.
     */
    public FuserAssembly(LaserPrinter printer) {
        this.printer = printer;
    }

    @Override
    public void activate() throws AssemblyException {
        if(issue != null) // The issue was already present as the printer shut down, before activating again
            throw new AssemblyException(issue, this);

        try {
            printer.push("Heating up fuser.");
            Thread.sleep(10000); // Takes 10 seconds
            activated = true;
            printer.push("Fuser on.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new AssemblyException(AssemblyException.PrinterIssue.FUSER, this); // TEST
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
     * @return Current exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }
}
