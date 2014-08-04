package com.shared.rides.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	private final Properties properties = new Properties();  
    
    private Session session;  
  
    private void init(String senderMail) {  
    	//En nuestro caso el usuario es el mismo mail.
        properties.put("mail.smtp.host", "smtp.gmail.com");  
        properties.put("mail.smtp.starttls.enable", "true");  
        properties.put("mail.smtp.port",25);  
        properties.put("mail.smtp.mail.sender", senderMail);  
        properties.put("mail.smtp.user", senderMail);  
        properties.put("mail.smtp.auth", "true");  
  
        session = Session.getDefaultInstance(properties);  
    }  
  
    public void sendEmail(String receptorMail, String senderMail, String senderPw){  
  
        init(senderMail);  
        try{  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptorMail));  
            message.setSubject("Shared - Rides - Nueva Peticion");  
            message.setText("Ha recibido una nueva peticion");  
            Transport t = session.getTransport("smtp");  
            t.connect((String)properties.get("mail.smtp.user"), senderPw);  
            t.sendMessage(message, message.getAllRecipients());  
            t.close();  
        }catch (MessagingException me){  
        			System.out.println(me);
            return;  
        }  
          
    }  

}
