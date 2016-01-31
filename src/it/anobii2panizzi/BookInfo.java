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
	private String availabilityHref;
	private String library;
	private String inventory;
	private String availability;
	private int availStatus;
	private String year;
	
	public String getHref() {
		return href;
	}
	public String getHrefSubPosition() {
		String[] splittedHref = this.href.split(",");
		return splittedHref[splittedHref.length - 2];
	}
	
	public String getRecordForAvailabilityHref() {
		return this.availabilityHref.substring(this.availabilityHref.lastIndexOf("RECORD=") + 7);
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
		return "BookInfo [href=" + href + ", position=" + position + ", availabilityHref=" + availabilityHref
				+ ", library=" + library + ", inventory=" + inventory + ", availability=" + availability
				+ ", availStatus=" + availStatus + ", year=" + year + "]";
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
	public String getAvailabilityHref() {
		return availabilityHref;
	}
	public void setAvailabilityHref(String availabilityHref) {
		this.availabilityHref = availabilityHref;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
}
