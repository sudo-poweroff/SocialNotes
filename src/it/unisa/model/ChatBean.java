package it.unisa.model;

public class ChatBean {

	public ChatBean() {
		this.chatID=-1;
		this.titolo="";
	}
	

	public int getChatID() {
		return chatID;
	}

	
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	
	
	public String getTitolo() {
		return titolo;
	}
	
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}


	public boolean isEmpty() {
		return this.chatID==-1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.chatID==((ChatBean)obj).getChatID();
	}
	

	@Override
	public String toString() {
		return "ChatBean [chatID=" + chatID + ", titolo=" + titolo + "]";
	}


	private int chatID;
	private String titolo;
}
