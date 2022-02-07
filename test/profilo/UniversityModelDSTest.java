package profilo;

import static org.junit.Assert.*;

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

import chat.MessageModelDS;

public class UniversityModelDSTest extends DataSourceBasedDBTestCase{
	
	private DataSource ds;
	private UniversityModelDS university;
	
	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Universita.sql'");
        dataSource.setUser("supo-poweroff");
        dataSource.setPassword("09134");
        return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Universita.xml"));
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
		university=new UniversityModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveAll() throws Exception{
		Collection<UniversityBean> result= university.doRetrieveAll();
		ArrayList<UniversityBean> rs = new ArrayList<>(result);
		ArrayList<UniversityBean> expected = new ArrayList<>();
		UniversityBean b1 = new UniversityBean();
		b1.setDenominazione("Politecnico di Milano");
		b1.setDescrizione("Univeristà della Lombardia");
		b1.setEmail("rettore@polimi.it");
		b1.setIndirizzo("Piazza Leonardo da Vinci, 32 - 20133 Milano");
		b1.setTelefono("0223991");
		UniversityBean b2 = new UniversityBean();
		b2.setDenominazione("Università Degli Studi Di Salerno");
		b2.setDescrizione("Univeristà degli studi di Salerno");
		b2.setEmail("unisa@unisa.it");
		b2.setIndirizzo("Via Giovanni Paolo 2");
		b2.setTelefono("08148651");
		
		expected.add(b1);
		expected.add(b2);
		
		for(int i=0;i<result.size();i++) {
			assertEquals(rs.get(i).getDenominazione(),expected.get(i).getDenominazione());
			assertEquals(rs.get(i).getDescrizione(),expected.get(i).getDescrizione());
			assertEquals(rs.get(i).getEmail(),expected.get(i).getEmail());
			assertEquals(rs.get(i).getIndirizzo(),expected.get(i).getIndirizzo());
			assertEquals(rs.get(i).getTelefono(),expected.get(i).getTelefono());
		}
	}
	
	@Test
	public void testdoRetrieveAllNotPresent() throws SQLException{
		ds.getConnection().prepareStatement("DELETE FROM Universita WHERE Denominazione='Politecnico di Milano'").execute();
		ds.getConnection().prepareStatement("DELETE FROM Universita WHERE Denominazione='Università Degli Studi Di Salerno'").execute();
		
		Collection<UniversityBean> result= university.doRetrieveAll();
		assertEquals(result.size(),0);
	}
	

}
