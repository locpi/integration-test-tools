package integrationtest.mock.database.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import integrationtest.commun.Logger;
import integrationtest.commun.PropertiesLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MysqlConfigurationDatabase {

	private final static Logger LOG = Logger.getInstance(MysqlConfigurationDatabase.class);

	private EmbeddedMysql mysql;

	private MysqldConfig config;

	private TimeZone timezone;

	private String user;

	private String password;

	private Integer port;

	private List<DatabaseBuidler> databaseBuilders = new ArrayList<DatabaseBuidler>();

	public MysqlConfigurationDatabase() {
		LOG.debug("------------------- INIT DATABASE------------------------");
		PropertiesLoader properties = new PropertiesLoader();
		this.user = properties.getValue("integrationtools.database.mysql.user");
		this.password = properties.getValue("integrationtools.database.mysql.password");
		this.port = Integer.valueOf(properties.getValue("integrationtools.database.mysql.port"));
		this.timezone = TimeZone
				.getTimeZone(ZoneId.of(properties.getValue("integrationtools.database.mysql.timezone")));
		config = MysqldConfig
				.aMysqldConfig(Version.valueOf(properties.getValue("integrationtools.database.mysql.version")))
				.withPort(this.port).withTimeZone(timezone).withUser(user, password).build();
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
		return MysqlDatabaseRequest.builder().conn(DriverManager.getConnection("jdbc:mysql://localhost:"
				+ config.getPort() + "/" + database
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
				this.user, this.password)).build();
	}

}
