public class PrintAssembly extends AssemblyUnit implements ISimAssembly {

    /**
     * Constructor.
     * @param laserPrinter Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public PrintAssembly(LaserPrinter laserPrinter) {
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
