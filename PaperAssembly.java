public class PaperAssembly extends AssemblyUnit implements ISimAssembly {

    public PaperAssembly(int i) {
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
}
