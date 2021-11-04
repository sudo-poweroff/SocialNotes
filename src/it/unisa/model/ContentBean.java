package it.unisa.model;

public class ContentBean {

	public ContentBean() {
		this.codiceNews=-1;
		this.fileName="";
	}
	
	
	public int getCodiceNews() {
		return codiceNews;
	}

	
	public void setCodiceNews(int codiceNews) {
		this.codiceNews = codiceNews;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public boolean isEmpty() {
		return this.codiceNews==-1;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return this.codiceNews==((ContentBean)obj).getCodiceNews()&&this.fileName.compareTo(((ContentBean)obj).getFileName())==0;
	}

	
	@Override
	public String toString() {
		return "ContentBean [codiceNews=" + codiceNews + ", fileName=" + fileName + "]";
	}


	private int codiceNews;
	private String fileName;
}
