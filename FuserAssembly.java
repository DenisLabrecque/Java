public class FuserAssembly extends AssemblyUnit implements ISimAssembly {

    /**
     * Constructor.
     * @param laserPrinter Reference back to the printer this fuser assembly is part of.
     */
    public FuserAssembly(LaserPrinter laserPrinter) {

        try {
            laserPrinter.push("Heating up fuser.");
            Thread.sleep(10000); // Takes 10 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * @return Current exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }
}
