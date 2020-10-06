package integrationtest.mock.mailing;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SMTPHelperTest {

	static SMTPHelper helper;

	static SMTPServer server;

	@BeforeClass
	public static void init() {
		server = new SMTPServer().startServer();
		helper = server.getHelper();
	}

	@Test
	public void test() {
		new SendMailService().send("coucou");
	}

	@AfterClass
	public static void end() {
		server.stop();
	}

}
