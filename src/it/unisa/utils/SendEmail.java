package it.unisa.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
          
	
   private String from = "socialnotes2021@gmail.com";
   private  String pass = "Despacito21";
   private String[] to;
   private String subject;
   private String body;
   
   public SendEmail(String initFrom, String initPass, String[] initTo, String initSubject, String initBody) {
	   from = initFrom;
	   pass = initPass;
       to = initTo;
       subject = initSubject;
       body = initBody;
   }
   
   public void SendMail() {
	   
	   
	   Properties props = System.getProperties();
       String host = "smtp.gmail.com";
       props.put("mail.smtp.starttls.enable", "true");
       props.put("mail.smtp.host", host);
       props.put("mail.smtp.user", this.from);
       props.put("mail.smtp.password", this.pass);
       props.put("mail.smtp.port", "587");
       props.put("mail.smtp.auth", "true");

       Session session1 = Session.getDefaultInstance(props);
       MimeMessage message = new MimeMessage(session1);

       try {
           message.setFrom(new InternetAddress(from));
           InternetAddress[] toAddress = new InternetAddress[(this.to).length];

           // To get the array of addresses
           for( int i = 0; i < (this.to).length; i++ ) {
               toAddress[i] = new InternetAddress(this.to[i]);
           }

           for( int i = 0; i < toAddress.length; i++) {
               message.addRecipient(Message.RecipientType.TO, toAddress[i]);
           }

           message.setSubject(subject);
           message.setText(this.body);
           Transport transport = session1.getTransport("smtp");
           transport.connect(host, this.from, this.pass);
           transport.sendMessage(message, message.getAllRecipients());
           transport.close();
       }
       catch (AddressException ae) {
           ae.printStackTrace();
       }
       catch (MessagingException me) {
           me.printStackTrace();
       }
	
	
	
   }

public String getFrom() {
	return from;
}

public void setFrom(String from) {
	this.from = from;
}

public String getPass() {
	return pass;
}

public void setPass(String pass) {
	this.pass = pass;
}

public String[] getTo() {
	return to;
}

public void setTo(String[] to) {
	this.to = to;
}

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}

public String getBody() {
	return body;
}

public void setBody(String body) {
	this.body = body;
}
}
