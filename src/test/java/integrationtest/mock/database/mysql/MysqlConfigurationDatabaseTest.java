package integrationtest.mock.database.mysql;

import org.junit.Assert;
import org.junit.Test;

public class MysqlConfigurationDatabaseTest {

	MysqlConfigurationDatabase config = new MysqlConfigurationDatabase();

	@Test
	public void test() {
		Assert.assertEquals(config.getUser(), "loic");
		Assert.assertEquals(config.getPassword(), "pincon");

	}

}
