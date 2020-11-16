public class LaserPrinter {
	private boolean isOn = false;
	private boolean isPoweringUp = false;
	private DisplayAssembly display;
	private PaperAssembly  paperTray;
	private TonerAssembly tonerCartridge;
	private FuserAssembly fuser;
	private PrintAssembly printAssembly;
	private OutputAssembly output;
	private PrinterQueue queue;

	/**
	 * Constructor. Creates all the assemblies the printer needs.
	 */
	public LaserPrinter() {
		display = new ConsoleDisplay(this);
		paperTray = new PaperAssembly(300);
		tonerCartridge = new TonerAssembly();
		fuser = new FuserAssembly(this);
		printAssembly = new PrintAssembly();
		output = new OutputAssembly();
		queue = new PrinterQueue();
	}

	/**
	 * @return Whether the printer is on or off.
	 */
	public boolean isOn() {
		return isOn;
	}

	/**
	 * @return Whether the printer is in the process of turning on.
	 */
	public boolean isPoweringUp() {
		return isPoweringUp;
	}

	/**
	 * Turn on the printer. Does nothing if the printer is already on.
	 */
	public void powerOn() {
		if(isOn)
			return;

		safelyActivateAssembly(display); // Activate the display first so it can output exceptions.
		safelyActivateAssembly(paperTray);
		safelyActivateAssembly(tonerCartridge);
		safelyActivateAssembly(fuser);
		safelyActivateAssembly(printAssembly);
		safelyActivateAssembly(output);

		// Finally, this printer itself is on if the sum of its parts is
		isOn = true;
	}

	public void powerOff() {
		safelyDeactivateAssembly(paperTray);
		safelyDeactivateAssembly(display); // Deactivate the display last
	}

	public void loadPaper(int sheets) {
		paperTray.addPaper(sheets);
	}

	public void loadToner(float cyan, float magenta, float yellow, float black) {
		tonerCartridge.addToner(cyan, magenta, yellow, black);
	}

	public void replaceDrum() {
		printAssembly.replaceDrum();
	}

	public void addToQueue(Document document) {
		queue.add(document);
	}

	public void print(Document document) {
		// TODO follow the steps of printing
	}

	/**
	 * Stop printing this document. Does nothing if this document is not currently being printed.
	 * @param document
	 */
	public void cancel(Document document) {

	}

	/**
	 * @param document Remove this document from the print queue. If it is currently being printed, it is already out of
	 * the queue; cancel instead.
	 */
	public void remove(Document document) {
		queue.remove(document);
	}
	
	public void reportStatus() {
		if(isOn) {
			System.out.println("--- Printer Status Report ---");
			System.out.println("          Paper Level: " + paperTray.getValue());
			System.out.println("--- Printer Status Report Complete ---");
		} else {
			System.out.println("Printer is off.");
		}
	}

	/**
	 * Activate the assembly; catch any exceptions and report them to the display.
	 */
	public void safelyActivateAssembly(AssemblyUnit assembly) {
		try {
			assembly.activate();
		} catch(AssemblyException e) {
			display.addException(e);
		}
	}

	/**
	 * Deactivate the assembly; catch any exceptions and report them to the display.
	 */
	private void safelyDeactivateAssembly(AssemblyUnit assembly) {
		try {
			assembly.deactivate();
		} catch(AssemblyException e) {
			display.addException(e);
		}
	}

	/**
	 * Add a message to the display.
	 * @param message Information to show the user. This should be general complimentary information about the operation
	 *                of the printer. For an exception, add an exception instead.
	 */
	public void push(String message) {
		display.push(message);
	}

	/**
	 * Halt the printer and display an exception warning that must be resolved to the user.
	 * @param exception The error that has occurred. The message property of the exception will be printed for the user
	 *                  to see.
	 */
	public void addException(AssemblyException exception) {
		display.addException(exception);
	}
}