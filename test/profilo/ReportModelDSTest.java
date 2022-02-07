package profilo;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import chat.ChatBean;
import chat.MessageBean;
import chat.MessageModelDS;
import chat.ParticipationBean;

public class ReportModelDSTest extends DataSourceBasedDBTestCase{
	
	private DataSource ds;
	private ReportModelDS report;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Segnalazione.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Segnalazione.xml"));
	}
	
	@Override
	protected DatabaseOperation getSetUpOperation(){
		return DatabaseOperation.REFRESH;
	}
	
	@Override
	protected DatabaseOperation getTearDownOperation() {
		return DatabaseOperation.DELETE_ALL;
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{}
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ds=this.getDataSource();
		report=new ReportModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testRemoveReport()throws Exception {
		report.removeReport("fry");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/SegnalazioneExpectedRemoved.xml")).getTable("Segnalazione");
		ITable actual=this.getConnection().createDataSet().getTable("Segnalazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testRemoveReportNotPresent()throws Exception {
		report.removeReport("califano87");
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Segnalazione.xml")).getTable("Segnalazione");
		ITable actual=this.getConnection().createDataSet().getTable("Segnalazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testRemoveReportVuoto() throws Exception{
		boolean flag = false;
		try {
			report.removeReport("");
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testRemoveReportNull() throws Exception{
		boolean flag = false;
		try {
			report.removeReport(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testArchiveReport()throws Exception {
		report.archiveReport(4);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/SegnalazioneExpectedArchived.xml")).getTable("Segnalazione");
		ITable actual=this.getConnection().createDataSet().getTable("Segnalazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testArchiveReportNotValid()throws Exception {
		boolean flag = false;
		try {
			report.archiveReport(-1);;
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testArchiveReportNotPresent()throws Exception {
		report.archiveReport(55);;
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Segnalazione.xml")).getTable("Segnalazione");
		ITable actual=this.getConnection().createDataSet().getTable("Segnalazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoRetrieveArchived() throws SQLException{
		Collection<ReportBean> result= report.doRetrieveArchived();
		ArrayList<ReportBean> rs = new ArrayList<>(result);
		ArrayList<ReportBean> expected = new ArrayList<>();
		ReportBean b1 = new ReportBean();
		b1.setCodiceSegnalazione(2);
		b1.setStato(1);
		b1.setMotivo("materiale non congruo");
		b1.setUsername("fry");
		expected.add(b1);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getCodiceSegnalazione(),expected.get(i).getCodiceSegnalazione());
			assertEquals(rs.get(i).getStato(),expected.get(i).getStato());
			assertEquals(rs.get(i).getMotivo(),expected.get(i).getMotivo());
			assertEquals(rs.get(i).getUsername(),expected.get(i).getUsername());
		}
		
	}
	
	@Test
	public void testDoRetrieveArchivedNotPresent()throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM Segnalazione WHERE CodiceSegnalazione=2").execute();
		
		Collection<ReportBean> result= report.doRetrieveArchived();
		assertEquals(result.size(),0);
	}
	
	@Test
	public void testDoRetrieveNotArchived() throws SQLException{
		Collection<ReportBean> result= report.doRetrieveNotArchived();
		ArrayList<ReportBean> rs = new ArrayList<>(result);
		ArrayList<ReportBean> expected = new ArrayList<>();
		ReportBean b1 = new ReportBean();
		b1.setCodiceSegnalazione(4);
		b1.setStato(0);
		b1.setMotivo("immagine non valida");
		b1.setUsername("fry");
		ReportBean b2 = new ReportBean();
		b2.setCodiceSegnalazione(3);
		b2.setStato(0);
		b2.setMotivo("materiale non corrisponde a preview");
		b2.setUsername("sime00");		
		expected.add(b2);
		expected.add(b1);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getCodiceSegnalazione(),expected.get(i).getCodiceSegnalazione());
			assertEquals(rs.get(i).getStato(),expected.get(i).getStato());
			assertEquals(rs.get(i).getMotivo(),expected.get(i).getMotivo());
			assertEquals(rs.get(i).getUsername(),expected.get(i).getUsername());
		}
		
	}
	
	@Test
	public void doRetrieveNotArchivedNotPresent() throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM Segnalazione WHERE CodiceSegnalazione=3").execute();
		ds.getConnection().prepareStatement("DELETE FROM Segnalazione WHERE CodiceSegnalazione=4").execute();
		
		Collection<ReportBean> result= report.doRetrieveArchived();
		assertEquals(result.size(),0);
	}
	
	@Test
	public void testDoSave() throws Exception{
		ReportBean bean = new ReportBean();
		bean.setCodiceSegnalazione(1);
		bean.setStato(0);
		bean.setMotivo("descrizione non presente");
		bean.setUsername("sime00");
		report.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/SegnalazioneExpectedSaved.xml")).getTable("Segnalazione");
		ITable actual=this.getConnection().createDataSet().getTable("Segnalazione");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));
	}
	
	@Test
	public void testDoSaveNull() throws Exception{
		boolean flag = false;
		try {
			report.doSave(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}

}
