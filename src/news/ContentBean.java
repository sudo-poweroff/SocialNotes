package news;

public class ContentBean {

	public ContentBean() {
		this.codiceNews=-1;
		this.idFile=-1;
	}
	
	
	public int getCodiceNews() {
		return codiceNews;
	}

	
	public void setCodiceNews(int codiceNews) {
		this.codiceNews = codiceNews;
	}
	

	public int getIdFile() {
		return idFile;
	}


	public void setIdFile(int idFile) {
		this.idFile = idFile;
	}


	public boolean isEmpty() {
		return this.codiceNews==-1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.codiceNews==((ContentBean)obj).getCodiceNews()&&this.idFile==((ContentBean)obj).getIdFile();
	}

	
	@Override
	public String toString() {
		return "ContentBean [codiceNews=" + codiceNews + ", idFile=" + idFile + "]";
	}


	private int codiceNews;
	private int idFile;
}
