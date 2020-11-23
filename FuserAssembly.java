public class FuserAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int TEMP_MAX = 451;   //maximum heat of 451 (honestly thought it was a boring book)
	private static final int TEMP_MIN  = 240;  //minimum heat of 240
	private static final int TEMP_INCREASE= 25;//increments of 25 degrees

	private int targetTemp; //fuser requested temperature
	AssemblyException.PrinterIssue issue = null;
	LaserPrinter printer;

	/**
	 * Constructor.
	 * @param printer Reference back to the printer this fuser assembly is part of.
	 */
	public FuserAssembly(LaserPrinter printer) {
		this.printer = printer;
	}
	
	/*public FuserAssembly(LaserPrinter printer) {
		this.targetTemp = targetTemp;
	}*/

	public void noHeat () {
		targetTemp = 0;
	}
	
	
	@Override
	public void activate() throws AssemblyException {
		activated = true;
		throw new AssemblyException(AssemblyException.PrinterIssue.FUSER, this); // TEST
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;

		if(issue != null)
			throw new AssemblyException(issue, this);
	}

	@Override
	public void setValue(int targetTemp) {
			this.targetTemp = targetTemp;
	}

	@Override
	public int getValue() {
		return targetTemp;
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
	public AssemblyException.PrinterIssue exception() {
		return issue;
	}
}
