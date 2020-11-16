public class PrinterQueue extends AssemblyUnit implements ISimAssembly {
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
     * @param document Document to add to the print queue.
     */
    public void add(Document document) {
    }

    /**
     * @param document Document to remove from the print queue.
     */
    public void remove(Document document) {
    }

    /**
     * @return Current exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return null;
    }
}
