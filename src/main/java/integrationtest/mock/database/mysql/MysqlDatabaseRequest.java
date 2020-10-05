package integrationtest.mock.database.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import lombok.Builder;

@Builder
public class MysqlDatabaseRequest {

	private Connection conn;

	public MysqlDatabaseRequestPrepare select(String sql) throws Exception {
		try {
			return MysqlDatabaseRequestPrepare.builder().typeOf("S").params(conn.prepareStatement(sql)).build();
		} catch (SQLException e) {
			throw new Exception("Probleme dans la requete : " + e.getMessage());
		}
	}

	public MysqlDatabaseRequestPrepare update(String sql) throws Exception {
		try {
			return MysqlDatabaseRequestPrepare.builder().typeOf("U").params(conn.prepareStatement(sql)).build();
		} catch (SQLException e) {
			throw new Exception("Probleme dans la requete : " + e.getMessage());
		}
	}

	public MysqlDatabaseRequestPrepare delete(String sql) throws Exception {
		try {
			return MysqlDatabaseRequestPrepare.builder().typeOf("D").params(conn.prepareStatement(sql)).build();
		} catch (SQLException e) {
			throw new Exception("Probleme dans la requete : " + e.getMessage());
		}
	}

}
