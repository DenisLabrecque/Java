public class OutputAssembly extends AssemblyUnit implements ISimAssembly {
	
    private final static int MAX_PAGES = 500;
	 private int numberOfPages;
	 
	 /* Default constructor */
	public OutputAssembly(LaserPrinter laserPrinter) {
		this.numberOfPages = MAX_PAGES;
	}
	
	/* Sets the paper tray level to initial value */
	public OutputAssembly(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	public void emptyTray (int numberOfPages) {
		//if (numberOfPages
		
	}

	@Override
	public void activate() throws AssemblyException {

	}

	@Override
	public void deactivate() throws AssemblyException {

	}

	@Override
	public void setValue(int newValue) {

	}

	@Override
	public int getValue() {
		return 0;
	}
}
