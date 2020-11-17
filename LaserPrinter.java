import java.util.ArrayList;

public class LaserPrinter {

	private boolean isOn = false;
	private boolean isPowering = false; // Powering up or down
	ArrayList<AssemblyException> exceptions = new ArrayList<>();

	private DisplayAssembly display;
	private PaperAssembly  paperTray;
	private TonerAssembly tonerCartridge;
	private FuserAssembly fuser;
	private PrintAssembly printAssembly;
	private OutputAssembly outputTray;
	private PrinterQueue queue;

	/**
	 * Constructor. Creates all the assemblies the printer needs.
	 */
	public LaserPrinter() {
		display = new ConsoleDisplay(this);
		paperTray = new PaperAssembly(this, 300);
		tonerCartridge = new TonerAssembly(this);
		fuser = new FuserAssembly(this);
		printAssembly = new PrintAssembly(this);
		outputTray = new OutputAssembly(this);
		queue = new PrinterQueue(this);
	}

	// Properties
	public DisplayAssembly display() { return display; }
	public PaperAssembly paperTray() { return paperTray; }
	public TonerAssembly toner() { return tonerCartridge; }
	public FuserAssembly fuser() { return fuser; }
	public PrintAssembly printAssembly() { return printAssembly; }
	public OutputAssembly outputTray() { return outputTray; }
	public PrinterQueue queue() { return queue; }

	/**
	 * @return Whether the printer is on or off.
	 */
	public boolean isOn() { return isOn; }

	/**
	 * @return Whether the printer is in the process of turning on/off.
	 */
	public boolean isPowering() { return isPowering; }

	/**
	 * @return Whether the printer is currently experiencing one or many exceptions.
	 */
	public boolean isError() { return exceptions.size() > 0; }

	/**
	 * Searches all the applicable components for exceptions.
	 * @return A list of all component exceptions. The is empty if no exception is reported.
	 */
	public ArrayList<AssemblyException> exceptions() { return exceptions; }

	/**
	 * Turn on the printer. Does nothing if the printer is already on.
	 * Will block on any errors.
	 */
	public void powerOn() {
		if(!isOn) {
			// Power up
			isPowering = true;

			safelyActivateAssembly(display); // Activate the display first so it can output exceptions.
			safelyActivateAssembly(paperTray);
			safelyActivateAssembly(tonerCartridge);
			safelyActivateAssembly(fuser);
			safelyActivateAssembly(printAssembly);
			safelyActivateAssembly(outputTray);

			// On
			isPowering = false;
			isOn = true;
			display.refresh();
		}
	}

	/**
	 * Turn off the printer. Does nothing if the printer is already off.
	 * Will not block on errors.
	 */
	public void powerOff() {
		if(isOn) {
			// Power down
			isOn = false;
			isPowering = true;

			safelyDeactivateAssembly(paperTray);
			safelyDeactivateAssembly(tonerCartridge);
			safelyDeactivateAssembly(fuser);
			safelyDeactivateAssembly(printAssembly);
			safelyDeactivateAssembly(outputTray);
			safelyDeactivateAssembly(display); // Deactivate the display last

			// Off
			isPowering = false;
			display().refresh();
		}
	}

	/**
	 * Raise a blocking exception that prevents the printer from continuing.
	 * @param exception The problem that occurred, with the message the user will see. Should not be null.
	 */
	public void raiseException(AssemblyException exception) {
		if(exception != null)
			exceptions.add(exception);
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

	/**
	 * Stop printing this document. Does nothing if this document is not currently being printed.
	 * @param document
	 */
	public void cancel(Document document) {
		// TODO
	}

	/**
	 * @param document Remove this document from the print queue. If it is currently being printed, it is already out of
	 * the queue; cancel instead.
	 */
	public void remove(Document document) {
		queue.remove(document);
	}

	/**
	 * Ask the screen to display the printer's status. Does nothing if the printer is off.
	 */
	public void reportStatus() {
		if(isOn)
			display.reportStatus();
	}

	/**
	 * Activate the assembly; catch any exceptions and report them to the display.
	 */
	public void safelyActivateAssembly(AssemblyUnit assembly) {
		try {
			assembly.activate();
		} catch(AssemblyException e) {

		}
	}

	/**
	 * Deactivate the assembly; catch any exceptions and report them to the display.
	 */
	private void safelyDeactivateAssembly(AssemblyUnit assembly) {
		try {
			assembly.deactivate();
		} catch(AssemblyException e) {
			display.refresh();
		}
	}

	/**
	 * Add a message to the display.
	 * @param message Information to show the user. This should be general complimentary information about the operation
	 *                of the printer. For an exception, add an exception instead.
	 */
	public void push(String message) {
		display.pushMessage(message);
	}

	/**
	 * Display information about the queue.
	 */
	public void reportQueue() {
		display.reportQueue();
	}

	public void printJob() {
		// TODO follow steps for printing
	}

	public void cancelJob(String name) {
		// TODO stop, remove from queue, etc.
	}

	public void addJob(String name, int pageCount) {
		// TODO add to queue
	}

	public boolean isPrinting() {
		return false; // TODO
	}
}