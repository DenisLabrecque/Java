public class FuserAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int TEMP_MAX = 2000;
	private static final int TEMP_MIN  = 450;
	private static final int TEMP_INCREASE= 25;
	

	int targetTemp = 0;
	LaserPrinter printer;
	AssemblyException issue = null;

	/**
	 * Constructor.
	 * @param printer Reference back to the printer this fuser assembly is part of.
	 */
	public FuserAssembly(LaserPrinter printer) {
		this.printer = printer;
	}

	@Override
	public void activate() throws AssemblyException {
		if(issue != null) // The issue was already present as the printer shut down, before activating again
			throw issue;
		
		
		throw new AssemblyException(AssemblyException.PrinterIssue.FUSER, this); // TEST
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;

		if(issue != null)
			throw issue;
	}

	@Override
	public void setValue(int newTemp) {

	}

	@Override
	public int getValue() {
		return (targetTemp);
	}


	public int increase(int targetTemp) {
		int currentTemp = 0; //placeholder value so it stops error
		while (currentTemp < targetTemp && currentTemp <= TEMP_MAX) {
			currentTemp = currentTemp + TEMP_INCREASE;
			try {
				printer.push("Heating up fuser.");
				currentTemp = TEMP_MIN;
				increase(currentTemp);// Takes 10 seconds
				Thread.sleep(10);
				activated = true;
				printer.push("Fuser on.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return currentTemp;
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return issue;
	}
}
