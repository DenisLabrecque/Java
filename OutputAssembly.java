public class OutputAssembly extends AssemblyUnit implements ISimAssembly {


	AssemblyException.PrinterIssue issue = null;
	private LaserPrinter printer;
	private AssemblyException assemblyException;
	private final static int MAX_PAGES = 500; // Set pages to 500
	private int numberOfPages; // Number of pages in the tray

	/* *
	 *Default constructor
	 * */
	public OutputAssembly(LaserPrinter laserPrinter) {
		printer = laserPrinter;
		this.numberOfPages = 0;
	}

	/**
	 * Sets the paper tray level to initial value
	 * @param numberOfPages
	 */
	public OutputAssembly(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	/**
	 * Empties out the tray
	 */
	public void emptyTray () {

		numberOfPages = 0;
		printer.removeException(assemblyException);
	}

	@Override
	public void activate() throws AssemblyException {
		activated = true;
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;

		if(issue != null)
			throw new AssemblyException(issue, this);
	}

	@Override
	public void setValue(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	@Override
	public int getValue() {
		return numberOfPages;
	}

	public boolean isActive() {
		return activated;
	}

	public void printPaper()
	{
		if(numberOfPages < MAX_PAGES)
			numberOfPages++;
		else {
			assemblyException = new AssemblyException(AssemblyException.PrinterIssue.OUTPUTTRAY, this);
			printer.raiseException(assemblyException);
		}
	}
	
	public AssemblyException exception() {
		return assemblyException;
	}
}
