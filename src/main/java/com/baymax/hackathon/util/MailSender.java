package com.baymax.hackathon.util;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.baymax.hackathon.model.Booking;
import com.baymax.hackathon.model.Publisher;

@Component
public class MailSender {

	@Value("${gmail.user.name}")
	private String userName;

	@Value("${gmail.user.password}")
	private String password;
	
	@Value("${mail.smtp.auth}")
	private String smtpAuth;
	@Value("${mail.smtp.starttls.enable}")
	private String startTTLsEnable;
	@Value("${mail.smtp.host}")
	private String smtpHost;
	@Value("${mail.smtp.port}")
	private String smtpPort;
	
	public void sendMail(Booking booking, String toEmail, Publisher publisher) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", smtpAuth);
		props.put("mail.smtp.starttls.enable", startTTLsEnable);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		  });

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userName));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(toEmail));
			message.setSubject("Food " + booking.getBookingStatus() +" at " + publisher.getName());
			message.setText(booking.getDescription());
			Transport.send(message);
			
			System.out.println("Mail sent to: " + toEmail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}