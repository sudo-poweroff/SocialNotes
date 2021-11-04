package it.unisa.model;

import java.sql.Timestamp;

public class MessageBean {

	public MessageBean() {
		this.idMessaggio=-1;
		this.testo="";
		this.dataInvio=null;
		this.username="";
		this.fileName="";
		this.chatID=-1;
	}
	
	
	public int getIdMessaggio() {
		return idMessaggio;
	}

	
	public void setIdMessaggio(int idMessaggio) {
		this.idMessaggio = idMessaggio;
	}
	
	
	public String getTesto() {
		return testo;
	}
	
	
	public void setTesto(String testo) {
		this.testo = testo;
	}
	
	
	public Timestamp getDataInvio() {
		return dataInvio;
	}
	
	
	public void setDataInvio(Timestamp dataInvio) {
		this.dataInvio = dataInvio;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	public int getChatID() {
		return chatID;
	}
	
	
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}


	public boolean isEmpty() {
		return this.idMessaggio==-1;
	}

	
	@Override
	public boolean equals(Object obj) {
		return this.idMessaggio==((MessageBean)obj).getIdMessaggio();
	}

	
	private int idMessaggio;
    private String testo;
    private Timestamp dataInvio;
    private String username;
    private String fileName;
    private int chatID;
}
