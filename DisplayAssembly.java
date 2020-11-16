public class DisplayAssembly extends AssemblyUnit implements ISimAssembly {
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
     * Adds an exception that needs to be solved before the printer can continue printing.
     * The exception should not be added twice.
     * @param exception The problem to solve.
     */
    public void addException(AssemblyException exception) {

    }
}
