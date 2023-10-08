package materiale;


import java.io.ByteArrayInputStream;
import java.sql.SQLException;

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




public class CourseModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private CourseModelDS course;

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Corso.sql'");
		dataSource.setUser("supo-poweroff");
		dataSource.setPassword("09134");
		return dataSource;
	}

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/init/Corso.xml"));
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
		course=new CourseModelDS(ds);
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testDoRetrieveByKeyPresent() throws Exception {
		CourseBean cBean = course.doRetrieveByKey("6");
		assertEquals(cBean.getCodiceCorso(),6);
		assertEquals(cBean.getNome(),"cddc");
		assertEquals(cBean.getNomeDipartimento(),"Dipartimento di Informatica");
		assertEquals(cBean.getDenominazione(),"Universita' Degli Studi Di Salerno");
	}

	@Test
	public void testDoRetrieveByKeyNotPresent() throws Exception {
		CourseBean cBean = course.doRetrieveByKey("7");
		assertEquals(cBean,null);
	}

	@Test
	public void testDoRetrieveByVoidKey() throws Exception {
		boolean flag = false;
		try {
			course.doRetrieveByKey("");

		} catch (NullPointerException e) {
			flag = true;

		}
		assertEquals(flag, true);
	}

	@Test
	public void testDoRetrieveByKeyNull() throws Exception {
		boolean flag = false;
		try {
			course.doRetrieveByKey(null);

		} catch (NullPointerException e) {
			flag = true;

		}
		assertEquals(flag, true);
	}

	@Test
	public void testDoRetrieveByNamePresent() throws Exception  {
		int codiceCorso = course.doRetrieveByName("programmazione1","Dipartimento di Informatica","Universita' Degli Studi Di Salerno");
		assertEquals(codiceCorso, 3);

	}

	@Test
	public void testDoRetrieveByNameNotPresent() throws Exception  {
		int codiceCorso = course.doRetrieveByName("ADE3","Dipartimento di Informatica","Universita' Degli Studi Di Salerno");
		assertEquals(codiceCorso, 1);
	}

	@Test
	public void testDoRetrieveByVoidName() throws Exception  {
		boolean flag = false;
		try {
			course.doRetrieveByName("","Dipartimento di Informatica","Universita' Degli Studi Di Salerno");
		}catch (NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
	}

	@Test
	public void testDoRetrieveByNameNull() throws Exception  {
		boolean flag = false;
		try {
			course.doRetrieveByName(null,"Dipartimento di Informatica","Universita' Degli Studi Di Salerno");
		}catch (NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
	}

	
	@Test
	public void testDoSave() throws Exception{
		CourseBean bean=new CourseBean();
		bean.setCodiceCorso(2);
		bean.setNome("Glottologia");
		bean.setNomeDipartimento("Dipartimento di \'Lettere Lingue Arti\'. Italianistica e culture comparate");
		bean.setDenominazione("Politecnico di Milano");
		course.doSave(bean);
		ITable expected =new FlatXmlDataSetBuilder().build(this.getClass().getClassLoader().getResourceAsStream("db/expected/CorsoExpected.xml")).getTable("Corso");
		ITable actual=this.getConnection().createDataSet().getTable("Corso");
		Assertion.assertEquals(new SortedTable(expected),new SortedTable(actual));

	}
	
	
	@Test
	public void testDoSaveNull() throws SQLException {
		boolean flag=false;
		try {
			course.doSave(null);
		}catch(NullPointerException e) {
			flag=true;
		}
		assertTrue(flag);
	}

}
