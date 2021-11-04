package it.unisa.model;

public class FormedBean {

	public FormedBean() {
		this.nome="";
		this.denominazione="";
		this.codiceCorso=-1;
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
	
	
	public int getCodiceCorso() {
		return codiceCorso;
	}
	
	
	public void setCodiceCorso(int codiceCorso) {
		this.codiceCorso = codiceCorso;
	}


	public boolean isEmpty() {
		return this.codiceCorso==-1;
	}
	

	@Override
	public boolean equals(Object obj) {
		return this.nome.compareTo(((FormedBean)obj).getNome())==0&&this.denominazione.compareTo(((FormedBean)obj).getDenominazione())==0&&this.codiceCorso==((FormedBean)obj).getCodiceCorso();
	}
	
	
	@Override
	public String toString() {
		return "FormedBean [nome=" + nome + ", denominazione=" + denominazione + ", codiceCorso=" + codiceCorso + "]";
	}


	private String nome;
    private String denominazione;
    private int codiceCorso;
}
