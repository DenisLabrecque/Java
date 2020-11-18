public class Document {
	private int id;
	private String name;
	private int pageCount;
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	
	public void setID(int newID) {
		this.id = newID;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public void setPageCount(int newCount) {
		this.pageCount = newCount;
	}
	
}