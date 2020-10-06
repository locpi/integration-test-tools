package integrationtest.performance;

import org.junit.Assert;

import integrationtest.commun.Logger;
import lombok.Builder;
import lombok.Setter;

/**
 * 
 * @author loicpincon
 *
 */
@Builder
@Setter
public class PerformanceCompteur implements Runnable {

	private Class<?> classe;

	private Runnable exec;

	private long timeMax;

	@Override
	public void run() {
		Logger logger = Logger.getInstance(classe);
		long debut = System.currentTimeMillis();
		exec.run();
		long fin = System.currentTimeMillis();
		logger.perf("Temps d'éxécution pour le block name : " + (fin - debut) + " miliseconds");
		Assert.assertTrue("Le delai n'est pas respecté", (fin - debut) <= timeMax);
	}

	public PerformanceCompteur setTimeMax(long time) {
		this.timeMax = time;
		return this;
	}

}
