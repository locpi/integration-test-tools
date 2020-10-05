package integrationtest.mock.database.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.Builder;

@Builder
public class MysqlDatabaseRequestPrepare {

	private String typeOf;

	private PreparedStatement params;

	public MysqlDatabaseRequestPrepare addParam(int position, Object value) throws Exception {
		params.setObject(position, value);
		return this;
	}

	public MysqlDatabaseRequestRes execute() throws Exception {
		try {
			if (typeOf.equals("S")) {
				return MysqlDatabaseRequestRes.builder().result(params.executeQuery()).build();
			} else {
				return MysqlDatabaseRequestRes.builder().nbeRow(params.executeUpdate()).build();
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		}
	}

	public static class TypeChampHelper {

		public static Date stringToDate(String date, String pattern) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			java.util.Date parsed;
			try {
				parsed = format.parse(date);
				return new java.sql.Date(parsed.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

}
