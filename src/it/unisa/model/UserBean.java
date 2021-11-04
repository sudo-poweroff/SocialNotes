package it.unisa.model;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;




public class UserBean {

	public UserBean() {
		this.username="";
		this.nome="";
		this.cognome="";
		this.img=null;
		this.email="";
		this.pass="";
		this.dataNascita=null;
		this.matricola="";
		this.ultimoAccesso=null;
		this.coin=0;
		this.ban=false;
		this.denominazione="";
		this.dipName="";
	}
	
	
	public String getUsername() {
		return username;
	}

	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getNome() {
		return nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	public String getCognome() {
		return cognome;
	}
	
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	
	public Blob getImg() {
		return img;
	}
	
	
	public void setImg(Blob img) {
		this.img = img;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getPass() {
		return pass;
	}
	
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	public Date getDataNascita() {
		return dataNascita;
	}
	
	
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	
	public String getMatricola() {
		return matricola;
	}
	
	
	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}
	
	
	public Timestamp getUltimoAccesso() {
		return ultimoAccesso;
	}
	
	
	public void setUltimoAccesso(Timestamp ultimoAccesso) {
		this.ultimoAccesso = ultimoAccesso;
	}
	
	
	public int getCoin() {
		return coin;
	}
	
	
	public void setCoin(int coin) {
		this.coin = coin;
	}
	
	
	public boolean getBan() {
		return ban;
	}
	
	
	public void setBan(boolean ban) {
		this.ban = ban;
	}
	
	
	public String getDenominazione() {
		return denominazione;
	}
	
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	
	public String getDipName() {
		return dipName;
	}
	
	
	public void setDipName(String dipName) {
		this.dipName = dipName;
	}

	
	public boolean isEmpty() {
		return (this.username.compareTo(""))==0;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.getUsername().compareTo(((UserBean)obj).getUsername())==0;
	}


	@Override
	public String toString() {
		return "UserBean [username=" + username + ", nome=" + nome + ", cognome=" + cognome + ", img=" + img
				+ ", email=" + email + ", pass=" + pass + ", dataNascita=" + dataNascita + ", matricola=" + matricola
				+ ", ultimoAccesso=" + ultimoAccesso + ", coin=" + coin + ", ban=" + ban + ", denominazione="
				+ denominazione + ", dipName=" + dipName + "]";
	}


	private String username;
    private String nome;
    private String cognome;
    private Blob img;
    private String email;
    private String pass;
    private Date dataNascita;
    private String matricola;
    private Timestamp ultimoAccesso;
    private int coin;
    private boolean ban;
    private String denominazione;
    private String dipName;
}
