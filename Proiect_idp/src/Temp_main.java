import java.awt.EventQueue;


public class Temp_main {

	public static void main(String []args) throws InterruptedException{
				
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transfer_gui window = new Transfer_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		System.out.println("> Final!");
	}
}
