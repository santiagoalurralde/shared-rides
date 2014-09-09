package com.shared.rides.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.shared.rides.domain.User;

public class EmailSender {
	
	private final Properties properties = new Properties();  
    
    private Session session;  
  
    private void init() {  
    	//En nuestro caso el usuario es el mismo mail.
        properties.put("mail.smtp.host", "smtp.gmail.com");  
        properties.put("mail.smtp.starttls.enable", "true");  
        properties.put("mail.smtp.port",25);  
        properties.put("mail.smtp.mail.sender", "shared.rides@gmail.com");  
        properties.put("mail.smtp.user", "shared.rides@gmail.com");  
        properties.put("mail.smtp.auth", "true");  
  
        session = Session.getDefaultInstance(properties);  
    }  
  
    /*
     * Recibe como parametros el mail de la persona que va a recibir el mail; asi como tambien si el mail es de una nueva peticion 
     * o si es una respuesta a una peticion. En el primer caso toma valor 0, en el segundo 1.
     */
    public void sendEmail(User receptorUser, User senderUser, int typeNotification){  
    	
        init(); 
        String mailContent;
        String mailSubject;
        try{  
        	if (typeNotification==0){
        		mailSubject = "SHARED RIDES - Nueva Peticion";
        		mailContent = "Buenos dias " + receptorUser.getName() + " " + receptorUser.getSurname() + ",\n" + 
        					"Usted ha recibido una nueva peticion de " + senderUser.getName() + " " + senderUser.getSurname() +
        					"\nPor favor, revise su cuenta."
        					+ "\nShared Rides.";
        	}
        	else{
        		mailSubject = "SHARED RIDES - Nueva Respuesta";
        		mailContent = "Buenos dias " + receptorUser.getName() + " " + receptorUser.getSurname() + ",\n" + 
    					" Usted ha recibido una respuesta de peticion por parte de " + senderUser.getName() + " " + senderUser.getSurname() +
    					"\nPor favor, revise su cuenta."
    					+ "\nShared Rides.";
        	}
        	
            MimeMessage message = new MimeMessage(session);  
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));  
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptorUser.getEmail()));  
            message.setSubject(mailSubject);  
            message.setText(mailContent);  
            Transport t = session.getTransport("smtp");  
            t.connect((String)properties.get("mail.smtp.user"), "sharedrides");  
            t.sendMessage(message, message.getAllRecipients());  
            t.close();  
        }catch (MessagingException me){  
        			System.out.println(me);
            return;  
        }  
          
    }  

}
