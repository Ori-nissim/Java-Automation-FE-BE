package models;

/**
 * This class holds the information of a linkedin connection
 */
public class ConnectionData {
	private String fullName;
	private String occupation;
	private String connectionDuration;
	
	public ConnectionData() {}
	
	@Override
	public String toString() {
		return "ConnectionData [fullName=" + fullName + ", occupation=" + occupation + ", connectionDuration="
				+ connectionDuration + "]";
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getConnectionDuration() {
		return connectionDuration;
	}
	public void setConnectionDuration(String connectionDuration) {
		this.connectionDuration = connectionDuration;
	}
	
}
