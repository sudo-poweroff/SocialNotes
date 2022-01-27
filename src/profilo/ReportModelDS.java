package profilo;

import java.util.Collection;

import javax.sql.DataSource;

public class ReportModelDS {
	private DataSource ds;

	public ReportModelDS(DataSource ds) {
		this.ds = ds;
	}
	
	public Collection<ReportBean> doRetrieveAll(){
		//TODO
		return null;
	}
	
	public void doSave (ReportBean item) {
		//TODO
	}
	
	public void removeReport(int codiceSegnalazione) {
		//TODO
	}
	
	public void archiveReport(int codiceSegnalazione) {
		//TODO
	}
	
}
