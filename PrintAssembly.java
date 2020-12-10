import java.awt.print.PrinterException;

public class PrintAssembly extends AssemblyUnit implements ISimAssembly {

	private final int MIRROR_RPM = 200;
	private final int MIRROR_SPINUP = 50;
	private final int MIRROR_SPINDOWN = 25;
	private final int MAX_DRUM_LIFE = 5000;
	private final int DRUM_LIFE_WARNING = 100;

	private LaserPrinter printer;
	private AssemblyException assemblyException;
	private int rotationalSpeed;
	private boolean coronaChargeStatus;
	private boolean dischargeLampStatus;
	private int sheetsPrinted;

	/**
	 * Constructor.
	 * 
	 * @param laserPrinter Reference back to the printer for sending messages,
	 *                     warnings, and exceptions.
	 */
	public PrintAssembly(LaserPrinter laserPrinter) {
		super();
		coronaChargeStatus = false;
		dischargeLampStatus = false;
		sheetsPrinted = 0;
		printer = laserPrinter;
	}

	// Activates the PrintAssembly
	@Override
	public void activate() throws AssemblyException {
		spinUpLaserMirror();
		chargeCoronaWire();
		turnOnDischargeLamp();
		
		activated = true;
	}

	// Deactivates the PrintAssembly
	@Override
	public void deactivate() throws AssemblyException {
		spinDownLaserMirror();
		dischargeCoronaWire();
		turnOffDischargeLamp();

		activated = false;
	}

	public boolean isActive() {
		return activated;
	}

	// Spins up the laser mirror
	public void spinUpLaserMirror() {
		while (rotationalSpeed < MIRROR_RPM) {
			rotationalSpeed += MIRROR_SPINUP;
		}
	}

	// Spins down the laser mirror
	public void spinDownLaserMirror() {
		while (rotationalSpeed > 0) {
			rotationalSpeed -= MIRROR_SPINDOWN;
		}
	}

	// Charges the corona wire
	public void chargeCoronaWire() {
		coronaChargeStatus = true;
	}

	// Discharges the corona wire
	public void dischargeCoronaWire() {
		coronaChargeStatus = false;
	}

	// Turns on the discharge lamp
	public void turnOnDischargeLamp() {
		dischargeLampStatus = true;
	}

	// Turns off the discharge lamp
	public void turnOffDischargeLamp() {
		dischargeLampStatus = false;
	}

	// Resets the sheet counter when the drum is replaced
	public void replaceDrum() {
		this.sheetsPrinted = MAX_DRUM_LIFE;
		printer.removeException(assemblyException);
		assemblyException = null;
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return assemblyException;
	}

	// Status of PrintAssembly
	public boolean drumIsActive() {
		return activated;
	}

	// Return true if PrintAssembly has low drum life
	public boolean isWarning() {
		return sheetsPrinted >= MAX_DRUM_LIFE - DRUM_LIFE_WARNING;
	}

	/**
	 * Sets how many sheets have gone through the drum.
	 * @param sheetsPrinted
	 */
	@Override
	public void setValue(int sheetsPrinted) {
		this.sheetsPrinted = sheetsPrinted;
	}

	/**
	 * Gets a percent of how many sheets the drum can still print.
	 * @return
	 */
	@Override
	public int getValue() {
		sheetsPrinted = MAX_DRUM_LIFE;
	}

	/**
	 * Property.
	 * @return String warning the user that the drum is old.
	 */
	public String drumWarning() {
		return "Drum nearly worn out.";
	}

	/**
	 * Pass one sheet of paper through the drum. This must to be done for each sheet printed,
	 * because the drum can become used along the way.
	 */
	public void consumeDrum() {
		if(sheetsPrinted + 1 < MAX_DRUM_LIFE)
			sheetsPrinted++;
		else {
			assemblyException = new AssemblyException(AssemblyException.PrinterIssue.DRUM, this);
			printer.raiseException(assemblyException);
		}
	}
}
