public class PaperAssembly extends AssemblyUnit implements ISimAssembly {

    /**
     * Constructor.
     * @param laserPrinter Reference back to the printer for sending messages, warnings, and exceptions.
     * @param sheets
     */
    public PaperAssembly(LaserPrinter laserPrinter, int sheets) {

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
     * Add sheets to the paper tray.
     * @param sheets
     */
    public void addPaper(int sheets) {
    }

    /**
     * @return Current exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }

    /**
     * Set paper levels back to 100%.
     */
    public void refill() {
        // TODO
    }

    /**
     * Solve a jam.
     */
    public void unjam() {
        // TODO
    }
}
