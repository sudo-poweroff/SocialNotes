package it.unisa.model;

public class CourseBean {
	public CourseBean() {
		this.codiceCorso=-1;
		this.nome="";
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
	
	
	public boolean equals(Object obj) {
		return this.getCodiceCorso()==((CourseBean)obj).getCodiceCorso();
	}

	
	@Override
	public String toString() {
		return "CourseBean [codiceCorso=" + codiceCorso+ ", nome=" + nome +"]";
	}


	private int codiceCorso;
    private String nome;
}
