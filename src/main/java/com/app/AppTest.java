package com.app;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.app.entites.Confirm;
import com.app.services.ConfirmService;

public class AppTest {
	
	public static void main(String[] args) {
		
		String dateString = "2024-02-26T17:00:00.000+00:00"; 
        String pattern = "yyyy-MM-dd"; 

        DateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date date = dateFormat.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
        String timeString = "10:30:00"; 
        String pattern1 = "HH:mm:ss"; 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern1);
        LocalTime time = LocalTime.parse(timeString, formatter);
        System.out.println(time);
	}
}
