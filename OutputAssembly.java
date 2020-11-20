public class OutputAssembly extends AssemblyUnit implements ISimAssembly {

	
	AssemblyException.PrinterIssue issue = null;

    private final static int MAX_PAGES = 500;
	 private int numberOfPages;
	 
	 /* Default constructor */
	public OutputAssembly(LaserPrinter printer) {
		this.numberOfPages = MAX_PAGES;
	}
	
	/* Sets the paper tray level to initial value */

	/**
	 *
	 * @param numberOfPages
	 */
	public OutputAssembly(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public void emptyTray () {
		 
		 numberOfPages = 0;
		 
		/*for (int i = numberOfPages; i >= 0; i--)
			if (i == 0)
				System.out.println("Tray is empty");
			else
				System.out.println("Tray is not empty");*/
	
	}

	@Override
	public void activate() throws AssemblyException {
		activated = true;
		throw new AssemblyException(AssemblyException.PrinterIssue.GENERAL,this);
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
}
