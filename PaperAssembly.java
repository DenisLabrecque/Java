public class PaperAssembly extends AssemblyUnit implements ISimAssembly {

	LaserPrinter laserPrinter;
	AssemblyException exception = null;
	private static final int MAX_PAPER_PAGES = 1000;
	private int currentPaperPages;
	static boolean isPaperJammed;

	/**
	 * Constructor.
	 * 
	 * @param laserPrinter Reference back to the printer for sending messages,
	 *                     warnings, and exceptions.
	 * @param sheets
	 */
	public PaperAssembly(LaserPrinter laserPrinter, int sheets) {
		super();
		this.laserPrinter = laserPrinter;
		currentPaperPages = sheets;
		isPaperJammed = false;
	}

	@Override
	public void activate() throws AssemblyException {

		if (exception != null) // The issue was already present as the printer shut down, before activating
			// again
			throw exception;

		laserPrinter.push("Reading current paper pages.");
		if (currentPaperPages > 0) {
			activated = true;
			laserPrinter.push("Paper ready.");
		}
		if (isPaperJammed)
			exception = new AssemblyException(AssemblyException.PrinterIssue.PAPER_JAM, this);
		laserPrinter.raiseException(exception);
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;
	}

	@Override
	public void setValue(int newValue) {
		currentPaperPages = newValue;
	}

	@Override
	public int getValue() {
		return currentPaperPages;
	}

	public boolean isActive() {
		return activated;
	}

	/**
	 * Add sheets to the paper tray.
	 * 
	 * @param sheets
	 */
	public void addPaper(int sheets) {
		if (currentPaperPages + sheets <= MAX_PAPER_PAGES) {
			currentPaperPages += sheets;
		} else {
			currentPaperPages = MAX_PAPER_PAGES;
		}
		laserPrinter.removeException(exception);
		exception = null;
	}

	public void consumePaper() {
		int jam = 1 + (int) (Math.random() * 20);
		if (jam == 20 || isPaperJammed) {
			isPaperJammed = true;
			exception = new AssemblyException(AssemblyException.PrinterIssue.PAPER_JAM, this);
			laserPrinter.raiseException(exception);
		} else if (currentPaperPages > 0) {
			currentPaperPages -= 1;
		} else {
			exception = new AssemblyException(AssemblyException.PrinterIssue.PAPERSUPPLY, this);
			laserPrinter.raiseException(exception);
		}
	}

	/**
	 * Set paper levels back to 100%.
	 */
	public void refill() {
		currentPaperPages = MAX_PAPER_PAGES;
		laserPrinter.removeException(exception);
		exception = null;
	}

	/**
	 * Solve a jam.
	 */
	public void unjam() {
		activated = true;
		isPaperJammed = false;
		laserPrinter.removeException(exception);
		exception = null;
	}
}
