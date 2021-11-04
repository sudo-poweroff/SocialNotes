package it.unisa.model;

public class DepartmentBean {
	public DepartmentBean() {
		this.nome="";
		this.denominazione="";
		this.descrizione="";
	}
	

	public String getNome() {
		return nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getDenominazione() {
		return denominazione;
	}
	
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	
	public String getDescrizione() {
		return descrizione;
	}
	
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public boolean isEmpty() {
		return this.denominazione.compareTo("")==0&&this.nome.compareTo("")==0;
	}


	 @Override
	public boolean equals(Object obj) {
		return (this.getDenominazione().compareTo(((DepartmentBean)obj).getDenominazione())==0&&this.getNome().compareTo(((DepartmentBean)obj).getNome())==0);
	}
	

	@Override
	public String toString() {
		return "DepartmentBean [nome=" + nome + ", denominazione=" + denominazione + ", descrizione=" + descrizione
				+ "]";
	}


	private String nome;
    private String denominazione;
    private String descrizione;
}
