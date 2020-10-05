package integrationtest.mock.database.mysql;

import static com.wix.mysql.ScriptResolver.classPathScript;

import java.util.ArrayList;
import java.util.List;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.SqlScriptSource;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.SchemaConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseBuidler {

	private String name;

	private List<SqlScriptSource> scripts = new ArrayList<>();

	public DatabaseBuidler(String name) {
		this.name = name;
	}

	public DatabaseBuidler addSqlScriptFromResource(String path) {
		this.scripts.add(classPathScript(path));
		return this;
	}

	public void createIn(EmbeddedMysql mysql) {
		mysql.addSchema(
				SchemaConfig.aSchemaConfig(this.name).withCharset(Charset.UTF8).withScripts(this.scripts).build());
	}

}
