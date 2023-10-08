package profilo;

public class InteresseBean {
    private String username;
    private int codiceCorso;


    public InteresseBean(){
        this.username="";
        this.codiceCorso=-1;
    }
    public InteresseBean(String username, int codiceCorso) {
        this.username = username;
        this.codiceCorso = codiceCorso;
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
}
