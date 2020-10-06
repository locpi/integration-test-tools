package integrationtest.commun;

import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

public class Logger {

	private static java.util.logging.Logger LOGGER;

	private final static PropertiesLoader PROPERTIES = new PropertiesLoader();

	private final static String LEVEL_KEY = "integrationtools.logger.root.level";

	private final static String PERF_ACTIVE_KEY = "integrationtools.logger.perf.active";

	private final static String PATTERN_KEY = "integrationtools.logger.pattern";

	private final static Integer LEVEL_INFO = 1;
	private final static Integer LEVEL_DEBUG = 2;
	private final static Integer LEVEL_TRACE = 3;
	private static Integer LEVEL = 1;
	private static Boolean PERF_ACTIVE = false;

	private Class<?> source;

	private Logger(Class<?> s) {
		this.source = s;
		LOGGER = java.util.logging.Logger.getLogger(source.getName());
		configure();
		String levelTmp = PROPERTIES.getValue(LEVEL_KEY);
		if (levelTmp.equalsIgnoreCase("INFO")) {
			LEVEL = 1;
		} else if (levelTmp.equalsIgnoreCase("DEBUG")) {
			LEVEL = 2;
		} else if (levelTmp.equalsIgnoreCase("TRACE")) {
			LEVEL = 3;
		} else {
			info("Ce niveau de log est inconnu : " + levelTmp);
		}

		String value = PROPERTIES.getValue(PERF_ACTIVE_KEY);
		if (value != null && Boolean.valueOf(value)) {
			PERF_ACTIVE = true;
		}
	}

	public void info(String message) {
		if (LEVEL_INFO <= LEVEL) {
			LOGGER.info(message);
		}
	}

	public void debug(String message) {
		if (LEVEL_DEBUG <= LEVEL) {
			LOGGER.fine(message);
		}
	}

	public void trace(String message) {
		if (LEVEL_TRACE <= LEVEL) {
			LOGGER.finer(message);
		}
	}

	public void perf(String message) {
		if (PERF_ACTIVE) {
			LOGGER.log(LOGLevel.PERF, message);
		}
	}

	public static Logger getInstance(Class<?> classe) {
		return new Logger(classe);
	}

	private void configure() {
		LOGGER.setUseParentHandlers(false);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter() {
			private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$-7s - %4$s %n";

			@Override
			public synchronized String format(LogRecord lr) {
				return String.format(format, new Date(lr.getMillis()), lr.getLevel().getLocalizedName(),
						lr.getLoggerName(), lr.getMessage());
			}
		});
		LOGGER.addHandler(handler);
	}
}
