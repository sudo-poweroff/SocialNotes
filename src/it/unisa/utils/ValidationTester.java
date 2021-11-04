package it.unisa.utils;

public class ValidationTester {

	public static void main(String[] args) {
		String n = "pipp8";
		String n1 = "lbonucci19";
		String n2 = "lnardo12";
		String n3 = "Gbffuno";
		
		String p ="Slatre123@1";
		String p1 ="cc##122A";
		String p2 ="@1QQWEee";
		String p3 ="bbbbAA1@";
		
		String m = "pipp8@gmail.com";
		String m1 = "ltorre123@gmail.com";
		String m2 = "giorgio.chiellini@gmail.com";
		String m3 = "ciao@ciao.it";
		
		String z ="Michele";
		String z1 ="Francesco";
		String z2 ="Paolo";
		String z3 ="Giuliano";
		System.out.println(""+Validation.validateUsername(n)
					+"\n"+Validation.validateUsername(n1)+"\n"+
					Validation.validateUsername(n2)+"\n"+
					Validation.validateUsername(n3)+"\n"+
					Validation.validateEmail(m)+"\n"+
					Validation.validateEmail(m1)+"\n"+
					Validation.validateEmail(m2)+"\n"+
					Validation.validateEmail(m3)+"\n"+
					Validation.validatePassword(p)+"\n"+
					Validation.validatePassword(p1)+"\n"+
					Validation.validatePassword(p2)+"\n"+
					Validation.validatePassword(p3)+"\n"+
					Validation.validateName(z)+"\n"+
					Validation.validateName(z1)+"\n"+
					Validation.validateName(z2)+"\n"+
					Validation.validateName(z3)+"\n"+
					Validation.validateYear(2005)+"\n"+
					Validation.validateYear(1231)+"\n"+
					Validation.validateYear(2021)+"\n"
					);
		

	}

}
