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

	// Adds a document to the print queue
	public void add(Document document) {
		printerQueue.add(document);
	}

	// Returns the print queue
	public List<Document> returnList() {
		return printerQueue;
	}
	
	// Prints the print queue
	public void printList() {
		for(int i = 0; i < printerQueue.size(); i++)
		{
			System.out.println(printerQueue.get(i).getID());
			System.out.println(printerQueue.get(i).getName());
			System.out.println(printerQueue.get(i).getPageCount());
		}
	}

	// Returns the next item in queue
	public Document nextQueue() {
		return printerQueue.get(0);
	}

	// Clears the print queue
	public void clearQueue() {
		printerQueue.clear();
	}

	// Removes a document from print queue
	public void remove(int id) {
		for(int i = 0; i < printerQueue.size(); i++)
		{
			if (printerQueue.get(i).getID() == id) {
				printerQueue.remove(i);
			}
		}
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
