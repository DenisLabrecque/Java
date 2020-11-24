public class FuserAssembly extends AssemblyUnit implements ISimAssembly {
	private static final int TEMP_MAX = 450;   //maximum heat of 451 (honestly thought it was a boring book)
	private static final int TEMP_MIN  = 240;  //minimum heat of 240
	private static final int TEMP_INCREASE= 10;//increments of 25 degrees

	private int targetTemp; //fuser requested temperature
	private int currentTemp;
	AssemblyException issue = null;
	LaserPrinter printer;
	/**
	 * Constructor.
	 * @param printer Reference back to the printer this fuser assembly is part of.
	 */
	public FuserAssembly(LaserPrinter printer) {
		super();
		this.printer = printer;
		targetTemp = TEMP_MIN;
		currentTemp = 60;
	}
	
	
	@Override
	public void activate() throws AssemblyException {
		if(issue != null)
			throw issue;
		increase();
		
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;
	}

	@Override
	public void setValue(int targetTemp) {
		if(targetTemp > TEMP_MAX)
			targetTemp = TEMP_MAX;
		else if (targetTemp < TEMP_MIN)
			targetTemp = TEMP_MIN;
		this.targetTemp = targetTemp;
		increase();
	}

	@Override
	public int getValue() {
		return currentTemp;
	}

	

	public int increase() {
		printer.push("Heating up fuser.");
		int sign;
		if (currentTemp < targetTemp)
			sign = 1;
		else 
			sign = -1;
		while (currentTemp < targetTemp) {
			currentTemp += TEMP_INCREASE * sign;
			try {
				Thread.sleep(10);
				System.out.println("Current: " + currentTemp);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		printer.push("Fuser on.");
		return currentTemp;
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return issue;
	}
}
