package it.unisa.model;

import java.sql.Date;

public class PaymentMethodBean {

	public PaymentMethodBean() {
		this.numeroCarta="";
		this.dataScadenza=null;
		this.nomeIntestatario="";
		this.cognomeIntestatario="";
		this.username="";
	}
	
	
	public String getNumeroCarta() {
		return numeroCarta;
	}

	
	public void setNumeroCarta(String numeroCarta) {
		this.numeroCarta = numeroCarta;
	}
	
	
	public Date getDataScadenza() {
		return dataScadenza;
	}
	
	
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	
	public String getNomeIntestatario() {
		return nomeIntestatario;
	}
	
	
	public void setNomeIntestatario(String nomeIntestatario) {
		this.nomeIntestatario = nomeIntestatario;
	}
	
	
	public String getCognomeIntestatario() {
		return cognomeIntestatario;
	}
	
	
	public void setCognomeIntestatario(String cognomeIntestatario) {
		this.cognomeIntestatario = cognomeIntestatario;
	}
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public boolean isEmpty() {
		return this.numeroCarta.compareTo("")==0;
	}

	
	@Override
	public boolean equals(Object obj) {
		return this.numeroCarta.compareTo(((PaymentMethodBean)obj).getNumeroCarta())==0;
	}


	@Override
	public String toString() {
		return "PaymentMethodBean [numeroCarta=" + numeroCarta + ", dataScadenza=" + dataScadenza
				+ ", nomeIntestatario=" + nomeIntestatario + ", cognomeIntestatario=" + cognomeIntestatario
				+ ", username=" + username + "]";
	}


	private String numeroCarta;
    private Date dataScadenza;
    private String nomeIntestatario;
    private String cognomeIntestatario;
    private String username;
}
