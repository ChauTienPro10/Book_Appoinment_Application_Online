package com.app.repositories;



//Interface
public interface EmailService {

 // Method
 // To send a simple email
 String sendSimpleMail(com.app.entites.EmailDetails details);

 // Method
 // To send an email with attachment
 String sendMailWithAttachment(com.app.entites.EmailDetails details);
}