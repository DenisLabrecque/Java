/**
 * Document that gets sent to print.
 */
public class Document {

	private int id; // Id
	private String name; // Name
	private int pageCount; // Page count

	/* Default constructor */
	public Document(){}

	/* Get the ID */
	public int getID() {
		return id;
	}

	/* Get the name */
	public String getName() {
		return name;
	}

	/* Get the page count */
	public int getPageCount() {
		return pageCount;
	}

	/* Set the id */
	public void setID(int newID) {
		this.id = newID;
	}

	/* Set the name */
	public void setName(String newName) {
		this.name = newName;
	}

	/* Set the page count */
	public void setPageCount(int newCount) {
		this.pageCount = newCount;
	}
	
}