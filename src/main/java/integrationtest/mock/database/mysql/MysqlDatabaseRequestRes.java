package integrationtest.mock.database.mysql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import lombok.Builder;

@Builder
public class MysqlDatabaseRequestRes {

	private ResultSet result;

	private int nbeRow;

	public int count() throws Exception {
		try {
			int size = 0;
			while (result.next()) {
				size++;
			}
			return size;
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	public void print() throws SQLException {
		ResultSetMetaData rsmd = result.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(",  ");
				String columnValue = result.getString(i);
				System.out.print(columnValue + " " + rsmd.getColumnName(i));
			}
			System.out.println("");
		}
	}

	public int state() {
		return nbeRow;
	}

}
