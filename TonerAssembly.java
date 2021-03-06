import java.awt.print.PrinterException;

public class TonerAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int FULL_TONER = 5000;
	private static final int TONER_LOW  = 300;
	private static final int TONER_EMPTY= 25;
	private int tonerLvl;
	
    LaserPrinter printer;
    AssemblyException exception = null;
    
    /**
     * Constructor.
     * @param printer Reference back to the printer for sending messages, warnings, and exceptions.
     */
    public TonerAssembly(LaserPrinter printer) {
        super();
        this.printer = printer;
        tonerLvl = FULL_TONER;
    }
    
    @Override
    public void activate() throws AssemblyException {
        if(exception != null) // The issue was already present as the printer shut down, before activating again
            throw exception;

        try {
            printer.push("Reading toner values.");
            Thread.sleep(0);
            activated = true;
            printer.push("Toner ready.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deactivate() throws AssemblyException {
		activated = false;
    }
    
    public boolean isActive() {
		return activated;
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
    	return (tonerLvl <= TONER_LOW);
        }
    
    public boolean isEmpty() {
    	return (tonerLvl <= TONER_EMPTY);
        }
    
    /**
     * Consume Toner
     *
    public int consumeToner() {
    	if (tonerLvl > TONER_EMPTY)
    		return tonerLvl -= 1;
    	else 
    		return TONER_EMPTY;
    }*/
	 
	 public void consumeToner() {
	 if(tonerLvl > TONER_EMPTY)
			tonerLvl -= 1;
		else {
			exception = new AssemblyException(AssemblyException.PrinterIssue.TONER, this);
			printer.raiseException(exception);
		}
	 }
    
    /**
     * Set levels back to 100%.
     */
    public void refill() {
        setValue(FULL_TONER);
        printer.removeException(exception);
    }
    
    /**
     * Property.
     * @return Warning message.
     */
    public String warning() {
    	if(isWarning())
    		return "Toner almost empty.";
    	else 
    		return null;
    }
}
