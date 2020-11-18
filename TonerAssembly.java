public class TonerAssembly extends AssemblyUnit implements ISimAssembly {

    /**
     * Constructor.
     * @param laserPrinter Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public TonerAssembly(LaserPrinter laserPrinter) {
        super();
    }

    @Override
    public void activate() throws AssemblyException {

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
