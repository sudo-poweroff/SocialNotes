package materiale;

public class CourseBean {
	public CourseBean() {
		this.codiceCorso=-1;
		this.nome="";
		this.nomeDipartimento="";
		this.denominazione="";
	}
	
	
	public int getCodiceCorso() {
		return codiceCorso;
	}

	
	public void setCodiceCorso(int codiceCorso) {
		this.codiceCorso = codiceCorso;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeDipartimento() {
		return nomeDipartimento;
	}

	public void setNomeDipartimento(String nomeDipartimento) {
		this.nomeDipartimento = nomeDipartimento;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public boolean equals(Object obj) {
		return this.getCodiceCorso()==((CourseBean)obj).getCodiceCorso();
	}


	@Override
	public String toString() {
		return "CourseBean{" +
				"codiceCorso=" + codiceCorso +
				", nome='" + nome + '\'' +
				", nomeDipartimento='" + nomeDipartimento + '\'' +
				", denominazione='" + denominazione + '\'' +
				'}';
	}

	private int codiceCorso;
    private String nome;
	private String nomeDipartimento;
	private String denominazione;
}
