package it.unisa.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	public static boolean validateEmail(String email) {
		//String regex = "^(.+)@(.+)$";
		String regex= "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public static boolean validateUsername(String uname) {
		String regex ="^[A-Za-z0-9]{3,15}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(uname);
		return matcher.matches();
	}
	
	public static boolean validatePassword(String pwd) {
		String regex="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,12}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pwd);
		return matcher.matches();
	}
	
	public static boolean validateName(String name) {
		String regex="^[A-Za-z\ss]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}

	public static boolean validateMonth(int month) {
		if (month<=0||month>12)
			return false;
		else
			return true;
	}
	
	public static boolean validateDay(int day) {
		if (day<=0||day>31)
			return false;
		else
			return true;
	}
	/**@param Valida un anno di nascita */
	public static boolean validateYear(int year) {
		if (year<=1900||year>Calendar.getInstance().get(Calendar.YEAR))
			return false;
		else
			return true;
	}
	
	public static boolean validateCardNumber(String cardNumber) {
		if (cardNumber.length()==16)
			return true;
		else
			return false;
	}
	
}
