package integrationtest.performance;

import org.junit.Test;

/**
 * 
 * @author loicpincon
 *
 */
public class PerformanceCompteurTest {

	private ServiceDeTest service = new ServiceDeTest();

	@Test
	public void test() {
		PerformanceCompteur.builder().classe(PerformanceCompteurTest.class).exec(() -> {
			service.waitin(2000);
		}).build().setTimeMax(4000).run();
	}

}
