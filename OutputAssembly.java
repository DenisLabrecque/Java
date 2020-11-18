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
		
		for (int i = numberOfPages; i >= 0; i--)
			if (i == 0)
				System.out.println("Tray is empty");
			else
				System.out.println("Tray is not empty");
	
	}

	@Override
	public void activate() throws AssemblyException {
		activated = true;
	}

	@Override
	public void deactivate() throws AssemblyException {
		activated = false;
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
