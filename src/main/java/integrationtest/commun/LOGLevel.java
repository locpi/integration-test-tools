package integrationtest.commun;

import java.util.logging.Level;

public class LOGLevel extends Level {

	private static final String defaultBundle = "sun.util.logging.resources.logging";

	/**
	 * 
	 */
	private static final long serialVersionUID = -3181875093663182769L;

	public static final Level PERF = new LOGLevel("PERF", 800, defaultBundle);

	protected LOGLevel(String name, int value, String s) {
		super(name, value, s);
	}

}
