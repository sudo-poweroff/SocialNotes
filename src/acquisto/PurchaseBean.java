package acquisto;

import java.sql.Date;

public class PurchaseBean {
	private String username;
	private int codiceMateriale;
	private Date dataAcquisto;
	
	
	public PurchaseBean(String username, int codiceMateriale, Date dataAcquisto) {
		super();
		this.username = username;
		this.codiceMateriale = codiceMateriale;
		this.dataAcquisto = dataAcquisto;
	}
	
	
	public PurchaseBean() {
		super();
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public int getCodiceMateriale() {
		return codiceMateriale;
	}
	public void setCodiceMateriale(int codiceMateriale) {
		this.codiceMateriale = codiceMateriale;
	}
	public Date getDataAcquisto() {
		return dataAcquisto;
	}
	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	
}
