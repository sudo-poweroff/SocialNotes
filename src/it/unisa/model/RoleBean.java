package it.unisa.model;

public class RoleBean {
	
	public RoleBean() {
		this.idRuolo=-1;
		this.ruolo="";
	}
	
	
	public int getIdRuolo() {
		return idRuolo;
	}
		
		
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}
	
	
	public String getRuolo() {
		return ruolo;
	}
	
	
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	
	public boolean isEmpty() {
		return idRuolo==-1;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getIdRuolo()==((RoleBean)obj).getIdRuolo();
	}
	
	
	@Override
	public String toString() {
		return "RoleBean [idRuolo=" + idRuolo + ", ruolo=" + ruolo + "]";
	}
	
	
	private int idRuolo;
	private String ruolo;
}
