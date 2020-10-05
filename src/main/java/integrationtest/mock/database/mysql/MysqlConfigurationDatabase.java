package integrationtest.mock.database.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MysqlConfigurationDatabase {

	private EmbeddedMysql mysql;

	private MysqldConfig config;

	private List<String> databases;

	private TimeZone timezone;

	private String user;

	private String password;

	@Builder.Default
	private List<DatabaseBuidler> databaseBuilders = new ArrayList<DatabaseBuidler>();

	/**
	 * Uses custom builder class
	 */
	public static MysqlConfigurationDatabaseBuilder builder() {
		return new CustomPMysqlConfigurationDatabaseBuilder();
	}

	/**
	 * Cusotm builder class
	 */
	private static class CustomPMysqlConfigurationDatabaseBuilder extends MysqlConfigurationDatabaseBuilder {
		@Override
		public MysqlConfigurationDatabase build() {
			super.config = MysqldConfig.aMysqldConfig(Version.v5_7_19).withPort(8889).withTimeZone(super.timezone)
					.withUser(super.user, super.password).build();
			return super.build();
		}
	}

	public void start() {
		com.wix.mysql.EmbeddedMysql.Builder mysqlB = EmbeddedMysql.anEmbeddedMysql(config);
		for (DatabaseBuidler dbb : this.databaseBuilders) {
			mysqlB.addSchema(dbb.getName(), dbb.getScripts());
		}
		mysql = mysqlB.start();

	}

	public DatabaseBuidler createDatabase(String name) {
		DatabaseBuidler tmp = new DatabaseBuidler(name);
		this.databaseBuilders.add(tmp);
		return tmp;
	}

	public MysqlDatabaseRequest getConnection(String database) throws SQLException, ClassNotFoundException {
		return MysqlDatabaseRequest.builder().conn(DriverManager
				.getConnection("jdbc:mysql://localhost:" + config.getPort() + "/" + database, this.user, this.password))
				.build();
	}

}
