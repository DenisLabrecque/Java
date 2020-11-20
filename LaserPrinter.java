import java.util.HashMap;
import java.util.Map;

/**
 * Represents a laser printer with all its components, giving it methods to start up, detect problems, and solve them.
 * Denis Labrecque, November 2020
 */
public class LaserPrinter {

	private boolean isOn = false;
	private boolean isPowering = false; // Powering up or down
	private boolean isPrinting = false;
	// This is a list because when the printer turns on, there could be multiple issues to resolve.
	HashMap<AssemblyException.PrinterIssue, AssemblyException> exceptions = new HashMap<>();

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

	/**
	 * Property.
	 * @return This printer's DisplayAssembly.
	 */
	public DisplayAssembly display() { return display; }

	/**
	 * Property.
	 * @return This printer's PaperAssembly.
	 */
	public PaperAssembly paperTray() { return paperTray; }

	/**
	 * Property.
	 * @return This printer's TonerAssembly.
	 */
	public TonerAssembly toner() { return tonerCartridge; }

	/**
	 * Property.
	 * @return This printer's FuserAssembly.
	 */
	public FuserAssembly fuser() { return fuser; }

	/**
	 * Property.
	 * @return This printer's PrintAssembly.
	 */
	public PrintAssembly printAssembly() { return printAssembly; }

	/**
	 * Property.
	 * @return This printer's OutputAssembly.
	 */
	public OutputAssembly outputTray() { return outputTray; }

	/**
	 * Property.
	 * @return This printer's PrinterQueue.
	 */
	public PrinterQueue queue() { return queue; }

	/**
	 * Property.
	 * @return Whether the printer is on or off.
	 */
	public boolean isOn() { return isOn; }

	/**
	 * Property.
	 * @return Whether the printer is on or still in the process of turning on/off.
	 */
	public boolean isOnOrPowering() { return isOn || isPowering; }

	/**
	 * Property.
	 * @return Whether each assembly has been succesfully turned on.
	 */
	private boolean allAssembliesOn() {
		return display.isActive() && paperTray.isActive() && tonerCartridge.isActive() &&
				fuser.isActive() && printAssembly.isActive() && outputTray().isActive();
	}

	/**
	 * Property.
	 * @return Whether the printer is in the process of turning on/off.
	 */
	public boolean isPowering() { return isPowering; }

	/**
	 * Property.
	 * @return Whether the printer is currently experiencing one or many exceptions.
	 */
	public boolean isError() { return exceptions.size() > 0; }

	/**
	 * Property.
	 * @return Whether the printer is currently printing something.
	 */
	public boolean isPrinting() { return isPrinting; }

	/**
	 * Searches all the applicable components for exceptions.
	 * @return A list of all component exceptions. The is empty if no exception is reported.
	 */
	public HashMap<AssemblyException.PrinterIssue, AssemblyException> exceptions() { return exceptions; }

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

			// Set whether is on
			if(allAssembliesOn()) { // No error when turning on assemblies
				isPowering = false;
				isOn = true;
				display.refresh();
			}
			else
				display().refresh(); // Final tally
		}
	}

	/**
	 * Turn off the printer. Does nothing if the printer is already off.
	 * Will not block on errors.
	 */
	public void powerOff() {
		if(isOn || isPowering) {
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
		if(exception != null) {
			exceptions.put(exception.issue(), exception);
			display.refresh();
		}
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
	 * @param id Remove this document from the print queue. If it is currently being printed, it is already out of
	 * the queue; cancel instead.
	 */
	public void remove(int id) {
		queue.remove(id);
	}

	/**
	 * Ask the screen to display the printer's status. Does nothing if the printer is off.
	 */
	public void reportStatus() {
		if(isOnOrPowering())
			display.reportStatus();
	}

	/**
	 * Activate the assembly; catch any exceptions and report them to the display.
	 */
	public void safelyActivateAssembly(AssemblyUnit assembly) {
		try {
			assembly.activate();
		} catch(AssemblyException e) {
			raiseException(e);
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
		// TODO if in error, we cannot print
	}

	public void cancelJob(String name) {
		// TODO stop, remove from queue, etc.
	}

	public void addJob(String name, int pageCount) {
		// TODO add to queue
	}

	public AssemblyException containsErrorFor(AssemblyUnit assembly) {
		for(Map.Entry<AssemblyException.PrinterIssue, AssemblyException> entry : exceptions.entrySet()) {
			if(entry.getValue().cause() == null)
				continue;
			else if(entry.getValue().cause().getClass() == assembly.getClass())
				return entry.getValue();
		}

		return null;
	}
}