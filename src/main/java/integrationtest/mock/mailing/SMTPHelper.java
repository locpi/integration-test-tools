package integrationtest.mock.mailing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import integrationtest.commun.Logger;
import integrationtest.commun.PropertiesLoader;
import lombok.Builder;

public class SMTPHelper {

	private final static Logger LOG = Logger.getInstance(SMTPHelper.class);

	private final PropertiesLoader PROP = new PropertiesLoader();

	private static Boolean LEVEL_LOG_SESSION = false;

	private static Thread CURRENT_LOG_MAIL_SESSION_THREAD;

	private static Boolean CURRENT_LOG_MAIL_SESSION = false;

	private SimpleSmtpServer server;

	@Builder
	public SMTPHelper(SimpleSmtpServer server) {
		this.server = server;
		LOG.info("blablabla");
		LEVEL_LOG_SESSION = Boolean.valueOf(PROP.getValue("integrationtools.mail.logger.session.auto", "false"));
		LOG.perf(LEVEL_LOG_SESSION + "");

		if (LEVEL_LOG_SESSION) {
			this.startPrintMailWhenIsComming();
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

	public List<EmailMessage> getAllMails() {
		List<EmailMessage> mails = new ArrayList<>();
		for (SmtpMessage o : server.getReceivedEmails()) {
			mails.add(EmailMessage.builder().headers(null).body(o.getBody()).build());
		}
		return mails;
	}

	public List<EmailMessage> getEmailEntrantFromAdresse(String adresse) {
		List<EmailMessage> mails = new ArrayList<>();
		for (SmtpMessage o : server.getReceivedEmails()) {
			Iterator<String> set = o.getHeaderNames().iterator();
			while (set.hasNext()) {
				String next = set.next();
				if (("To".equals(next) || "Cc".equals(next)) && o.getHeaderValue(next).equals(adresse)) {
					mails.add(EmailMessage.builder().build());
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
					mails.add(EmailMessage.builder().build());
				}
			}
		}
		return mails;
	}

	public void cleanAllMsgBoxes() {
		server.reset();
	}

	public void startPrintMailWhenIsComming() {
		if (!CURRENT_LOG_MAIL_SESSION) {
			LOG.info("start current session mail");
			CURRENT_LOG_MAIL_SESSION_THREAD = new Thread(() -> {
				while (true) {
					List<EmailMessage> mails = this.getAllMails();
					mails.stream().forEach(m -> {
						System.out.println(m.getBody());
					});
				}
			});
			CURRENT_LOG_MAIL_SESSION_THREAD.start();
		}

	}

	public void stopPrintMailWhenIsComming() {
		if (CURRENT_LOG_MAIL_SESSION) {
			CURRENT_LOG_MAIL_SESSION_THREAD.interrupt();
			CURRENT_LOG_MAIL_SESSION = false;
		}
	}

}
