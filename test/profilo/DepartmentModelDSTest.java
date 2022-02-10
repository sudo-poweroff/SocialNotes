package profilo;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


public class DepartmentModelDSTest extends DataSourceBasedDBTestCase{
	
	
	private DataSource ds;
	private DepartmentModelDS department;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Dipartimento.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Dipartimento.xml"));
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
		department=new DepartmentModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveAll() throws Exception{
		Collection<DepartmentBean> result= department.doRetrieveAll();
		ArrayList<DepartmentBean> rs = new ArrayList<>(result);
		ArrayList<DepartmentBean> expected = new ArrayList<>();
		DepartmentBean b1 = new DepartmentBean();
		b1.setNome("Dipartimento di Biologia");
		b1.setDenominazione("Politecnico di Milano");
		b1.setDescrizione("http://www.uniba.it/ricerca/dipartimenti/biologia");
		DepartmentBean b2 = new DepartmentBean();
		b2.setNome("Dipartimento di Chimica");
		b2.setDenominazione("Politecnico di Milano");
		b2.setDescrizione("http://www.chimica.uniba.it/");
		DepartmentBean b3 = new DepartmentBean();
		b3.setNome("Dipartimento di Giurisprudenza");
		b3.setDenominazione("Universita' degli Studi del Sannio");
		b3.setDescrizione("http://www.uniba.it/ricerca/dipartimenti/lex");
		
		
		expected.add(b1);
		expected.add(b2);
		expected.add(b3);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getNome(),expected.get(i).getNome());
			assertEquals(rs.get(i).getDescrizione(),expected.get(i).getDescrizione());
			assertEquals(rs.get(i).getDenominazione(),expected.get(i).getDenominazione());
		}
		
	}
	
	@Test
	public void testdoRetrieveAllNotPresent() throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM Dipartimento WHERE Nome='Dipartimento di Biologia'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Dipartimento WHERE Nome='Dipartimento di Chimica'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Dipartimento WHERE Nome='Dipartimento di Giurisprudenza'").execute();
		
		Collection<DepartmentBean> result= department.doRetrieveAll();
		assertEquals(result.size(),0);
	}
	
	@Test
	public void testDoRetrieveByDenominazione() throws Exception{
		Collection<DepartmentBean> result= department.doRetrieveByDenominazione("Politecnico di Milano");
		ArrayList<DepartmentBean> rs = new ArrayList<>(result);
		ArrayList<DepartmentBean> expected = new ArrayList<>();
		DepartmentBean b1 = new DepartmentBean();
		b1.setNome("Dipartimento di Biologia");
		b1.setDenominazione("Politecnico di Milano");
		b1.setDescrizione("http://www.uniba.it/ricerca/dipartimenti/biologia");
		DepartmentBean b2 = new DepartmentBean();
		b2.setNome("Dipartimento di Chimica");
		b2.setDenominazione("Politecnico di Milano");
		b2.setDescrizione("http://www.chimica.uniba.it/");
	
		expected.add(b1);
		expected.add(b2);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getNome(),expected.get(i).getNome());
			assertEquals(rs.get(i).getDescrizione(),expected.get(i).getDescrizione());
			assertEquals(rs.get(i).getDenominazione(),expected.get(i).getDenominazione());
		}
		
	}
	
	@Test
	public void testDoRetrieveByDenominazioneNonPresente() throws Exception{
		Collection<DepartmentBean> result=new ArrayList<>();
		result=department.doRetrieveByDenominazione("Universit� degli studi di Salerno");
		assertEquals(result.size(), 0);		
	}
	
	@Test
	public void testDoRetrieveByDenominazioneVuoto() throws SQLException{
		boolean flag = false;
		try {
			department.doRetrieveByDenominazione("");
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
	
	@Test
	public void testDoRetrieveByDenominazioneNull() throws SQLException{
		boolean flag = false;
		try {
			department.doRetrieveByDenominazione(null);
		}catch(NullPointerException e) {
			flag=true;	
		}
		assertEquals(true,flag);
	}
		

}
