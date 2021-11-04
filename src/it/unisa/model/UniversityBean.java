package it.unisa.model;


public class UniversityBean {

	public UniversityBean() {
		this.denominazione="";
		this.indirizzo="";
		this.telefono="";
		this.logo=null;
		this.descrizione="";
	}
	
	
	public String getDenominazione() {
		return denominazione;
	}
	
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	
	public String getIndirizzo() {
		return indirizzo;
	}
	
	
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	
	
	public String getTelefono() {
		return telefono;
	}
	
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public java.sql.Blob getLogo() {
		return logo;
	}
	
	
	public void setLogo(java.sql.Blob blob) {
		this.logo = blob;
	}
	
	
	public String getDescrizione() {
		return descrizione;
	}
	
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	public boolean isEmpty() {
		return denominazione.compareTo("")==0;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getDenominazione().compareTo(((UniversityBean)obj).getDenominazione()))==0;
	}
	
	
	@Override
	public String toString() {
		return "UniversityBean [denominazione=" + denominazione + ", indirizzo=" + indirizzo + ", telefono=" + telefono
				+ ", email=" + email + ", logo=" + logo + ", descrizione=" + descrizione + "]";
	}


	private String denominazione;
    private String indirizzo;
    private String telefono; 
    private String email; 
    private java.sql.Blob logo;
    private String descrizione;
}
