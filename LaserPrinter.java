public class LaserPrinter {
	private boolean isOn = false;
	private PaperAssembly  paperTray;
	private TonerAssembly tonerCartridge;
	private FuserAssembly fuser;
	private PrintAssembly printAssembly;
	private OutputAssembly output;
	private DisplayAssembly display;
	private PrinterQueue queue;
	
	public LaserPrinter() {
		paperTray = new PaperAssembly(300);
	}
	
	// Body of logic

	public void powerOn() {
		if(isOn) {
			System.out.println("Printer is already on.");
		} else {
			System.out.println("Laser Printer - Starting up.");

			try {
				paperTray.activate();
			} catch (Exception e) {
				System.out.println("Exception was thrown while powering on the printer.  " + e);
			}
		
			if(paperTray.isActive()) {
				isOn = true;
				System.out.println("Laser Printer successfully powered up.");
			}
		}
	}

	public void powerOff() {
		if(isOn) {
			System.out.println("Laser Printer - Shutting down.");

			try {
				paperTray.deactivate();
			} catch (Exception e) {
				System.out.println("Exception was thrown while powering off the printer.  " + e);
			}

			if(!paperTray.isActive()) {
				isOn = false;
				System.out.println("Laser Printer successfully shutdown.");
			}
		} else {
			System.out.println("Printer is already off.");
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
	 * Remove this document from the print queue. If it is currently being printed, it is already out of the queue;
	 * cancel instead.
	 * @param document
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

	public void testPaperTray() {
		if(paperTray.isError())
			display.addException(paperTray.exception());
	}

	public void testTonerCartridge() {
		if(tonerCartridge.isError())
			display.addException(tonerCartridge.exception());
	}

	public void testFuser() {
		if(fuser.isError())
			display.addException(fuser.exception());
	}

	public void testPrintAssembly() {
		if(printAssembly.isError())
			display.addException(printAssembly.exception());
	}

	public void testOutput() {
		if(output.isError())
			display.addException(output.exception());
	}

	public void testQueue() {
		if(queue.isError())
			display.addException(queue.exception());
	}
}