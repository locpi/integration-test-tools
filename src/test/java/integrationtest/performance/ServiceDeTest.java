package integrationtest.performance;

public class ServiceDeTest {

	public Object waitin(int duree) {
		try {
			Thread.sleep(duree);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
