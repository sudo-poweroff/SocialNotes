package it.unisa.model;

import java.io.InputStream;
import java.sql.Date;



public class MaterialBean {
	public MaterialBean() {
		this.codiceMateriale=-1;
		this.dataCaricamento=null;
		this.keywords="";
		this.costo=-1;
		this.descrizione="";
		this.hidden=true;
		this.codiceCorso=-1;
		this.username="";
		this.fileName="";
		this.anteprima=null;
	}
	
	
	public InputStream getAnteprima() {
		return anteprima;
	}


	public void setAnteprima(InputStream anteprima) {
		this.anteprima = anteprima;
	}


	public int getCodiceMateriale() {
		return codiceMateriale;
	}

	
	public void setCodiceMateriale(int codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
	}
	
	
	public Date getDataCaricamento() {
		return dataCaricamento;
	}
	
	
	public void setDataCaricamento(Date dataCaricamento) {
		this.dataCaricamento = dataCaricamento;
	}
	
	
	public String getKeywords() {
		return keywords;
	}
	
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	
	public int getCosto() {
		return costo;
	}
	
	
	public void setCosto(int costo) {
		this.costo = costo;
	}
	
	
	public String getDescrizione() {
		return descrizione;
	}
	
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	public boolean isHidden() {
		return hidden;
	}
	
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	
	public int getCodiceCorso() {
		return codiceCorso;
	}
	
	
	public void setCodiceCorso(int codiceCorso) {
		this.codiceCorso = codiceCorso;
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


	public boolean isEmpty() {
		return this.codiceMateriale==-1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.codiceMateriale==((MaterialBean)obj).getCodiceCorso();
	}
	
	
	@Override
	public String toString() {
		return "MaterialBean [codiceMateriale=" + codiceMateriale + ", dataCaricamento=" + dataCaricamento
				+ ", keywords=" + keywords + ", costo=" + costo + ", descrizione=" + descrizione + ", hidden=" + hidden
				+ ", codiceCorso=" + codiceCorso + ", username=" + username + ", fileName=" + fileName +"]";
	}


	private int codiceMateriale;
    private Date dataCaricamento;
    private String keywords;
    private int costo;
    private String descrizione;
    private boolean hidden;
    private int codiceCorso;
    private String username;
    private String fileName;
    private InputStream anteprima;
}
