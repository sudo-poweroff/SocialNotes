package it.unisa.model;

import java.sql.Date;

public class FriendsBean {

	public FriendsBean() {
		this.username1="";
		this.username2="";
		this.dataInizio=null;
	}
	
	
	public String getUsername1() {
		return username1;
	}

	
	public void setUsername1(String username1) {
		this.username1 = username1;
	}
	
	
	public String getUsername2() {
		return username2;
	}
	
	
	public void setUsername2(String username2) {
		this.username2 = username2;
	}
	
	
	public Date getDataInizio() {
		return dataInizio;
	}
	
	
	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}


	public boolean isEmpty() {
		return this.username1.compareTo("")==0&&this.username2.compareTo("")==0;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.username1.compareTo(((FriendsBean)obj).getUsername1())==0&&this.username2.compareTo(((FriendsBean)obj).getUsername2())==0;
	}
	

	@Override
	public String toString() {
		return "FriendsBean [username1=" + username1 + ", username2=" + username2 + ", dataInizio=" + dataInizio + "]";
	}


	private String username1;
    private String username2;
    private Date dataInizio;
	
}
