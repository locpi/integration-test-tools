package integrationtest.mock.mailing;

import java.io.IOException;

import com.dumbster.smtp.SimpleSmtpServer;

public class SMTPServer {

	private SimpleSmtpServer server;

	private SMTPHelper helper;

	public SMTPServer startServer() {
		try {
			server = SimpleSmtpServer.start(2525);
			return this;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public SMTPHelper getHelper() {
		helper = SMTPHelper.builder().server(server).build();
		return helper;
	}

	public void stop() {
		helper.stopPrintMailWhenIsComming();
		server.stop();
	}

}
