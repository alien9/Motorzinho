import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Loop {
	public static void main(String[] args){
		LCD.drawString("inicio", 0, 1);
		Pisk p1=new Pisk();
		p1.start();
		Receptor re=new Receptor();
		re.start();
		Behavior b2=new Killa();
		Behavior[] bs={b2};
		Arbitrator a=new Arbitrator(bs);
		a.start();
	}
}
class Pisk extends Thread{
	int state=0;
	String[] tiks={"/", "-", "\\", "|"};
	public Pisk(){
		super();
	}
	public void run(){
		while(!Button.ESCAPE.isPressed()){
			LCD.drawString(tiks[state],0,0);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			state++;
			if(state>=tiks.length) state=0;
		}
	}
}
class Killa implements Behavior{
	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return Button.ESCAPE.isPressed();
	}

	@Override
	public void action() {
		System.exit(0);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
}
class Receptor extends Thread{

	public Receptor(){
		super();
	}
	public void run(){
		LCD.drawString("Waiting...", 0, 3);
		try{
			USBConnection c=USB.waitForConnection(1000, 0);
			LCD.clear();
			if(c.available()>0){
				LCD.drawString("Connected.", 0, 3);
				DataOutputStream dO=c.openDataOutputStream();
				DataInputStream dI=c.openDataInputStream();
				char b;
				while(!Button.ESCAPE.isPressed()){
					try {
						b=dI.readChar();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						break;
					}
					LCD.drawChar(b, 0, 4);
				}
			}
		}catch(Exception e){
				LCD.drawString("Not OK.", 0, 3);
		}
		BTConnection b = Bluetooth.waitForConnection();
		DataOutputStream out=b.openDataOutputStream();
		DataInputStream in=b.openDataInputStream();
		LCD.drawString("Connecticut.", 0, 3);
		byte[] bytes = null;
		while(!Button.ESCAPE.isPressed()){
			int u = 0;
			try {
				u = in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LCD.drawInt(u, 3, 10);
		}

	}
	
}