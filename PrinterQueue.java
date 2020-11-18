import java.util.LinkedList;
import java.util.List;

public class PrinterQueue implements ISimAssembly {

	private List<Document> printerQueue;

	/**
	 * Constructor.
	 * 
	 * @param laserPrinter Reference back to the printer for sending messages,
	 *                     warnings, and exceptions.
	 */
	public PrinterQueue(LaserPrinter laserPrinter) {
		printerQueue = new LinkedList<Document>();
	}

	// Adds a document to the queue
	public void add(Document document) {
		printerQueue.add(document);
	}

	// Returns the queue list
	public List<Document> returnList() {
		return printerQueue;
	}

	// Returns the next item in queue
	public Document nextQueue() {
		return printerQueue.get(0);
	}

	public void clearQueue() {
		printerQueue.clear();
	}

	// Removes a document from queue
	public void remove(Document document) {

	}

	/**
	 * @return Current exception object (if an exception has occurred).
	 */
	public AssemblyException exception() {
		return null;
	}

	@Override
	public void setValue(int newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setError(int newValue) {
		// TODO Auto-generated method stub

	}
}
