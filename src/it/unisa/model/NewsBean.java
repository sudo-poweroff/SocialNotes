package it.unisa.model;

import java.sql.Date;

public class NewsBean {

	public NewsBean() {
		this.codiceNews=-1;
		this.titolo="";
		this.argomento="";
		this.contenuto="";
		this.username="";
	}
	
	
	public int getCodiceNews() {
		return codiceNews;
	}

	
	public void setCodiceNews(int codiceNews) {
		this.codiceNews = codiceNews;
	}
	
	
	public String getTitolo() {
		return titolo;
	}
	
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	
	public String getArgomento() {
		return argomento;
	}
	
	
	public void setArgomento(String argomento) {
		this.argomento = argomento;
	}
	
	
	public String getContenuto() {
		return contenuto;
	}
	
	
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}


	public boolean isEmpty() {
		return this.codiceNews==-1;
	}

	
	@Override
	public boolean equals(Object obj) {
		return this.codiceNews==((NewsBean)obj).getCodiceNews();
	}

	
	@Override
	public String toString() {
		return "NewsBean [codiceNews=" + codiceNews + ", titolo=" + titolo + ", argomento=" + argomento + ", contenuto="
				+ contenuto + ", username=" + username + "]";
	}

	public Date getDataCaricamento() {
		return dataCaricamento;
	}


	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	
	private Date dataCaricamento;
	private int codiceNews;
    private String titolo;
    private String argomento;
    private String contenuto;
    private String username;
}
