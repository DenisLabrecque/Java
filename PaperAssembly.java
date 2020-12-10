public class PaperAssembly extends AssemblyUnit implements ISimAssembly {

	LaserPrinter laserPrinter;
	AssemblyException exception = null;
	private static final int MAX_PAPER_PAGES = 1000;
	private int currentPaperPages;
	boolean isPaperJammed = false;

	/**
	 * Constructor.
	 * @param laserPrinter Reference back to the printer for sending messages, warnings, and exceptions.
	 * @param sheets
	 */
	public PaperAssembly(LaserPrinter laserPrinter, int sheets) {
		super();
		this.laserPrinter = laserPrinter;
		currentPaperPages = sheets;
	}

	@Override
	public void activate() throws AssemblyException {
		if(exception != null)
			throw exception;

		try {
			laserPrinter.push("Reading current paper pages.");
			getValue();
			if(currentPaperPages > 0){
				activated = true;
				laserPrinter.push("Paper ready.");
			}
			if(isPaperJammed)
				throw new InterruptedException();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		//;
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
	 * @param sheets
	 */
	public void addPaper(int sheets) {
		if(currentPaperPages + sheets <= MAX_PAPER_PAGES)
		{
			currentPaperPages += sheets;
		}
		else 
		{
			currentPaperPages = MAX_PAPER_PAGES;
		}
	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return exception;
	}

	public void consumePaper(){
		int jam = 1 + (int) (Math.random () * 20);
		if(jam == 20 || isPaperJammed) {
			isPaperJammed = true;
			exception = new AssemblyException(AssemblyException.PrinterIssue.PAPER_JAM, this);
			laserPrinter.raiseException(exception);
		}
		else {
			currentPaperPages -= 1;
		}
	}


	/**
	 * Set paper levels back to 100%.
	 */
	public void refill() {
		currentPaperPages = MAX_PAPER_PAGES;
	}

	/**
	 * Solve a jam.
	 */
	public void unjam() {
		isPaperJammed = false;
	}
}
