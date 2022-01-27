package profilo;

public class ReportBean {
	private int codiceSegnalazione;
	private int stato;
	private String motivo;
	private String username;
	
	public ReportBean(int codiceSegnalazione, int stato, String motivo, String username) {
		super();
		this.codiceSegnalazione = codiceSegnalazione;
		this.stato = stato;
		this.motivo = motivo;
		this.username = username;
	}

	public ReportBean() {
		super();
	}

	public int getCodiceSegnalazione() {
		return codiceSegnalazione;
	}

	public void setCodiceSegnalazione(int codiceSegnalazione) {
		this.codiceSegnalazione = codiceSegnalazione;
	}

	public int getStato() {
		return stato;
	}

	public void setStato(int stato) {
		this.stato = stato;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
