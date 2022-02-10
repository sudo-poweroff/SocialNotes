package materiale;


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




public class CourseModelDSTest extends DataSourceBasedDBTestCase{

	private DataSource ds;
	private CourseModelDS course;

	@Override
	protected DataSource getDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:db/init/Corso.sql'");
		dataSource.setUser("Ackermann32");
		dataSource.setPassword("alfonso");
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
		int codiceCorso = course.doRetrieveByName("programmazione1");
		assertEquals(codiceCorso, 3);
	}

	@Test
	public void testDoRetrieveByNameNotPresent() throws Exception  {
		int codiceCorso = course.doRetrieveByName("ADE3");
		assertEquals(codiceCorso, 1);
	}

	@Test
	public void testDoRetrieveByVoidName() throws Exception  {
		boolean flag = false;
		try {
			course.doRetrieveByName("");
		}catch (NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
	}

	@Test
	public void testDoRetrieveByNameNull() throws Exception  {
		boolean flag = false;
		try {
			course.doRetrieveByName(null);
		}catch (NullPointerException e) {
			flag = true;
		}
		assertEquals(flag, true);
	}



}
