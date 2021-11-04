package it.unisa.utils;

public class Utility {

	public static void print(String... messages) {
		String message="";
		for(String s:messages)
			message+=s+"\n";
		System.out.printf("%s",message);
	}   
	
	
	public static void print(Exception exception ) {
		Utility.print("Exception:"+exception.getMessage());
		exception.printStackTrace();
	}
}
