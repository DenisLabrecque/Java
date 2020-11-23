public class PrintAssembly extends AssemblyUnit implements ISimAssembly {

	private final int MIRROR_RPM = 200;
	private final int MIRROR_SPINUP = 50;
	private final int MIRROR_SPINDOWN = 25;
	private final int MAX_DRUM_LIFE = 500;
	private final int DRUM_LIFE_WARNING = 100;

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

	// Spins up the laser mirror
	public void spinUpLaserMirror() {
		while (rotationalSpeed < MIRROR_RPM) {
			rotationalSpeed += MIRROR_SPINUP;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// Spins down the laser mirror
	public void spinDownLaserMirror() {
		while (rotationalSpeed > 0) {
			rotationalSpeed -= MIRROR_SPINDOWN;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
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

	// Increases the sheet counter each time the drum life is decreased by a new
	// page being printed
	public void updateDrumLife() {
		sheetsPrinted++;
	}

	// Resets the sheet counter when the drum is replaced
	public void replaceDrum() {
		sheetsPrinted = 0;
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return null;
	}

	// Status of PrintAssembly
	public boolean drumIsActive() {
		return activated;
	}

	// Return true if PrintAssembly has low drum life
	public boolean isWarning() {
		return MAX_DRUM_LIFE - sheetsPrinted <= DRUM_LIFE_WARNING;
	}

	@Override
	public void setValue(int sheetsPrinted) {
		this.sheetsPrinted =  sheetsPrinted;
	}

	@Override
	public int getValue() {
		return sheetsPrinted;
	}

	/**
	 * Property.
	 * @return String warning the user that the drum is old.
	 */
	public String drumWarning() {
		return "Drum nearly worn out.";
	}
}
