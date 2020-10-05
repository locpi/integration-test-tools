package integrationtest.commun;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesLoader {

	public static String pathToFile = "integrationtestTools.properties";

	public static String pathToFileS = "integrationtestToolsDefault.properties";

	private static Properties properties;

	public Object getValue(String key, Object defaultValue) {
		return getInstance().getOrDefault(key, defaultValue);
	}

	public String getValue(String key) {
		return (String) getInstance().get(key);
	}

	private Properties getInstance() {
		if (properties == null) {
			properties = loadProperties(PropertiesLoader.pathToFileS);
			Properties custom = loadProperties(PropertiesLoader.pathToFile);
			if (custom != null) {
				Iterator<Object> keys = custom.keySet().iterator();
				while (keys.hasNext()) {
					String key = (String) keys.next();
					properties.put(key, custom.get(key));
				}
			}

		}
		return properties;
	}

	private Properties loadProperties(String file) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(file);
		if (in == null) {
			return null;
		}
		try {
			Properties tmp = new Properties();
			tmp.load(in);
			return tmp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
