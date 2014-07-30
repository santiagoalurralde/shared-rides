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
    
    private String password;  
  
    private Session session;  
  
    private void init() {  
    	//TODO: Falta que los datos sean pasados, que no esten en duro.
        properties.put("mail.smtp.host", "smtp.gmail.com");  
        properties.put("mail.smtp.starttls.enable", "true");  
        properties.put("mail.smtp.port",25);  
        properties.put("mail.smtp.mail.sender","leandro.bagur@gmail.com");  
        properties.put("mail.smtp.user", "leandro.bagur");  
        properties.put("mail.smtp.auth", "true");  
  
        session = Session.getDefaultInstance(properties);  
    }  
  
    public void sendEmail(){  
  
        init();  
        try{  
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("leandro.bagur@gmail.com"));  
            message.setSubject("Prueba");  
            message.setText("Hola que tal");  
            Transport t = session.getTransport("smtp");  
            t.connect((String)properties.get("mail.smtp.user"), "SiSt3maS!");  
            t.sendMessage(message, message.getAllRecipients());  
            t.close();  
        }catch (MessagingException me){  
        			System.out.println(me);
            return;  
        }  
          
    }  

}
