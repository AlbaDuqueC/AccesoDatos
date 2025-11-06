package clases;

public class Main {

	public static void main(String[] args) {
		
		if(AccesoBD.conectar()) {
			System.out.println("Ole ole");
		}else {
			System.out.println("no ole ole");
		} 

	}

}
