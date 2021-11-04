package it.unisa.model;

public class ParticipationBean {

	public ParticipationBean() {
		this.username="";
		this.chatID=-1;
	}
	
	
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public int getChatID() {
		return chatID;
	}
	
	
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}


	public boolean isEmpty() {
		return this.username.compareTo("")==0;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.username.compareTo(((ParticipationBean)obj).getUsername())==0&&this.chatID==((ParticipationBean)obj).getChatID();
	}
	
	
	@Override
	public String toString() {
		return "ParticipationBean [username=" + username + ", chatID=" + chatID + "]";
	}


	private String username;
    private int chatID;
}
