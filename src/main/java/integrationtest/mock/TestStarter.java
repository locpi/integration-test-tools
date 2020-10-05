package integrationtest.mock;

import java.time.ZoneId;
import java.util.TimeZone;

import integrationtest.mock.database.mysql.MysqlConfigurationDatabase;
import integrationtest.mock.database.mysql.MysqlDatabaseRequest;

public class TestStarter {
	public static void main(String[] args) throws Exception {
		MysqlConfigurationDatabase conf = MysqlConfigurationDatabase.builder().user("loic").password("pincon")
				.timezone(TimeZone.getTimeZone(ZoneId.of("UTC"))).build();

		conf.createDatabase("test").addSqlScriptFromResource("table.sql").addSqlScriptFromResource("insert.sql");

		conf.start();

		MysqlDatabaseRequest test = conf.getConnection("test");

		System.out.println(test.select("select * from persons").execute().count());

	}

}
