package integrationtest.mock.mailing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

public class SMTPServer {

	private SimpleSmtpServer server;

	public SMTPServer startServer() {
		try {
			server = SimpleSmtpServer.start(2525);
			return this;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void printMail() {
		for (SmtpMessage o : server.getReceivedEmails()) {

			Iterator<String> set = o.getHeaderNames().iterator();
			while (set.hasNext()) {
				String next = set.next();
				System.out.println(next + " :" + o.getHeaderValue(next));

			}

			System.out.println(o.getBody());
		}
	}

	public List<EmailMessage> getEmailEntrantFromAdresse(String adresse) {
		List<EmailMessage> mails = new ArrayList<>();
		for (SmtpMessage o : server.getReceivedEmails()) {
			Iterator<String> set = o.getHeaderNames().iterator();
			while (set.hasNext()) {
				String next = set.next();
				if (("To".equals(next) || "Cc".equals(next)) && o.getHeaderValue(next).equals(adresse)) {
					mails.add(new EmailMessage());
				}
			}
		}
		return mails;
	}

	public List<EmailMessage> getEmailSortantSFromAdresse(String adresse) {
		List<EmailMessage> mails = new ArrayList<>();
		for (SmtpMessage o : server.getReceivedEmails()) {
			Iterator<String> set = o.getHeaderNames().iterator();
			while (set.hasNext()) {
				String next = set.next();
				if ("From".equals(next) && o.getHeaderValue(next).equals(adresse)) {
					mails.add(new EmailMessage());
				}
			}
		}
		return mails;
	}

	public void cleanAllMsgBoxes() {
		server.reset();
	}

	public void stop() {
		server.stop();
	}

}
