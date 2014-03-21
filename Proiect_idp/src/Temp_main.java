import java.awt.EventQueue;

// clasa temporara de test

public class Temp_main {

	public static void main(String []args) throws InterruptedException{
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI gg = new GUI();
					gg.nimic();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("> Final test!");
	}
}
