import javax.xml.transform.OutputKeys;

public class TonerAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int FULL_TONER = 2000;
	private static final int TONER_LOW  = 300;
	private static final int TONER_EMPTY= 25;
	
    LaserPrinter printer;
    AssemblyException exception = null;
    private int tonerLvl = 0;
    /**
     * Constructor.
     * @param printer Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public TonerAssembly(LaserPrinter printer) {
        super();
        this.printer = printer;
    }
    
    @Override
    public void activate() throws AssemblyException {
        if(exception != null) // The issue was already present as the printer shut down, before activating again
            throw exception;

        try {
            printer.push("Reading toner values.");
            Thread.sleep(100);
            activated = true;
            printer.push("Toner ready.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new AssemblyException(AssemblyException.PrinterIssue.TONER, this);
    }

    @Override
    public void deactivate() throws AssemblyException {

    }

    @Override
    public void setValue(int newValue) {
    	this.tonerLvl = newValue;
    }

    @Override
    public int getValue() {
        return tonerLvl;
    }


    /**
     * Retrieve the exception object (if an exception has occurred).
     */
    public AssemblyException exception() {
        return exception;
    }
    
    public boolean isWarning() {
    	if (tonerLvl <= TONER_LOW) {
    		System.out.println("Toner is low.");
    		return true;
    	}
    	else 
    		return false;
        }
    public boolean isEmpty() {
    	if (tonerLvl <= TONER_EMPTY) {
    		System.out.println("Toner is empty.");
    		return true;
    	}
    	else 
    		return false;
        }

    /**
     * Set levels back to 100%.
     */
    public void refill() {
        setValue(100);
    }

    /**
     * Property.
     * @return Warning message.
     */
    public String warning() {
        return "Toner almost empty.";
    }
}
