package integrationtest.mock;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import integrationtest.mock.mailing.EmailMessage;
import integrationtest.mock.mailing.SMTPServer;

public class TestStarter {
	public static void main(String[] args) throws Exception {
		SMTPServer server = new SMTPServer();
		server.startServer();
		send();
		List<EmailMessage> mailsSortant = server.getEmailSortantSFromAdresse("web@gmail.com");
		System.out.println(mailsSortant.size());
		List<EmailMessage> mailsentrants = server.getEmailEntrantFromAdresse("abcd@gmail.com");
		System.out.println(mailsentrants.size());
	}

	public static void send() {
		// Recipient's email ID needs to be mentioned.
		String to = "abcd@gmail.com";
		String toE = "abcdE@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "2525");


		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toE));

			message.addRecipient(Message.RecipientType.CC, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
