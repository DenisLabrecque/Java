public class PaperAssembly extends AssemblyUnit implements ISimAssembly {
    public PaperAssembly(int i) {
    }

    public void activate() {
    }

    public void deactivate() {
    }

    public boolean isActive() {
        return true;
    }

    @Override
    public void setValue(int newValue) {

    }

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
