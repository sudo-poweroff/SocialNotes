package it.unisa.model;

import java.sql.Date;
import java.sql.Timestamp;

public class FeedbackBean {

	public FeedbackBean() {
		this.codiceMateriale=-1;
		this.username="";
		this.dataFeed=null;
		this.commento="";
		this.valutazione=-1;
	}
	
	
	public int getCodiceMateriale() {
		return codiceMateriale;
	}

	
	public void setCodiceMateriale(int codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public Timestamp getDataFeed() {
		return dataFeed;
	}
	
	
	public void setDataFeed(Timestamp dataFeed) {
		this.dataFeed = dataFeed;
	}
	
	
	public String getCommento() {
		return commento;
	}
	
	
	public void setCommento(String commento) {
		this.commento = commento;
	}
	
	
	public int getValutazione() {
		return valutazione;
	}
	
	
	public void setValutazione(int valutazione) {
		this.valutazione = valutazione;
	}


	public boolean isEmpty() {
		return this.codiceMateriale==-1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.codiceMateriale==((FeedbackBean)obj).getCodiceMateriale()&&this.username.compareTo(((FeedbackBean)obj).getUsername())==0&&this.dataFeed.equals(((FeedbackBean)obj).getDataFeed());     
	}
	

	@Override
	public String toString() {
		return "FeedbackBean [codiceMateriale=" + codiceMateriale + ", username=" + username + ", dataFeed=" + dataFeed
				+ ", commento=" + commento + ", valutazione=" + valutazione + "]";
	}


	private int codiceMateriale;
    private String username;
    private Timestamp dataFeed;
    private String commento;
    private int valutazione;
}
