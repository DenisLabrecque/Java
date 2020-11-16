public class OutputAssembly extends AssemblyUnit implements ISimAssembly {

    private Light tonerLED = new Light(); // On if error, yellow if warning, flashing red if error
    private Light drumLED  = new Light(); // On if error, yellow if warning, flashing red if error
    private Light errorLED = new Light(); // On if error, flashing red
    private Light readyLED = new Light(); // Flashing green while powering up or printing, solid green otherwise

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
