package profilo;

import java.sql.Date;

public class InteresseBean {
    private String username;
    private int codiceCorso;
    private Date dataInserimento;


    public InteresseBean(){
        this.username="";
        this.codiceCorso=-1;
        this.dataInserimento=null;
    }
    public InteresseBean(String username, int codiceCorso, Date dataInserimento) {
        this.username = username;
        this.codiceCorso = codiceCorso;
        this.dataInserimento=dataInserimento;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCodiceCorso() {
        return codiceCorso;
    }

    public void setCodiceCorso(int codiceCorso) {
        this.codiceCorso = codiceCorso;
    }

    public Date getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {
        this.dataInserimento = dataInserimento;
    }
}
