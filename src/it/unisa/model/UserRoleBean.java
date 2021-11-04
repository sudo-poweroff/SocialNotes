package it.unisa.model;

public class UserRoleBean {

	public UserRoleBean() {
		this.username="";
		this.idRuolo=-1;
	}
	
	
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public int getIdRuolo() {
		return idRuolo;
	}
	
	
	public void setIdRuolo(int idRuolo) {
		this.idRuolo = idRuolo;
	}


	public boolean isEmpty() {
		return this.username.compareTo("")==0&&this.idRuolo==-1;
	}

	
	@Override
	public boolean equals(Object obj) {
		return this.username.compareTo(((UserRoleBean)obj).getUsername())==0&&this.idRuolo==((UserRoleBean)obj).getIdRuolo();
	}
	
	
	@Override
	public String toString() {
		return "UserRoleBean [username=" + username + ", idRuolo=" + idRuolo + "]";
	}


	private String username;
	private int idRuolo;
}
