import junit.framework.TestCase;


public class Mediator_test extends TestCase{

	public Mediator_test(String name) {
		super(name);
	}
	
	protected void setUp() {

		GUI gg = new GUI();
		gg.nimic();
	}
	
	public void test_ceva() throws InterruptedException{		
		Thread.sleep(5000);
	}
}
