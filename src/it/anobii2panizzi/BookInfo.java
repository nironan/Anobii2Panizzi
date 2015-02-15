package it.anobii2panizzi;

public class BookInfo extends Book implements Cloneable {

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	private String href;
	private String position;
	private String library;
	private String inventory;
	private int availStatus;
	private String year;
	
	public String getHref() {
		return href;
	}
	public String getHrefSubPosition() {
		String[] splittedHref = this.href.split(",");
		return splittedHref[splittedHref.length - 2];
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getAvailStatus() {
		return availStatus;
	}
	public void setAvailStatus(int availStatus) {
		this.availStatus = availStatus;
	}
	
	public String getLibrary() {
		return library;
	}
	public void setLibrary(String library) {
		this.library = library;
	}
	@Override
	public String toString() {
		return "BookInfo [href=" + href + ", position=" + position
				+ ", library=" + library + ", availStatus=" + availStatus
				+ ", getName()=" + getName() + ", getAuthor()=" + getAuthor() + ", getInventory()=" + getInventory()
				+ ", getBarCode()=" + getBarCode() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
}
