public class PrinterQueue implements ISimAssembly {

    /**
     * Constructor.
     * @param laserPrinter Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public PrinterQueue(LaserPrinter laserPrinter) {
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

    @Override
    public void setValue(int newValue) {

    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void setError(int newValue) {

    }
}
