package control;

import static java.lang.Thread.sleep;

import java.io.IOException;

public class NotifyAlive extends Thread{

	private String email;
	private StreamClient streamClient;
	
	public NotifyAlive(String email, StreamClient streamClient) {
		this.email = email;
		this.streamClient = streamClient;
	}
	
	public void run(){
		
		try {
			while(true) {
				sleep(142111);
				this.streamClient.sendMessage("F");
				this.streamClient.sendMessage(email);
			}
		} catch (InterruptedException e) {
			System.err.println("ERRO ENVIO NotifyAlive" + e);
		} catch (IOException e) {
			System.err.println("ERRO ENVIO NotifyAlive" + e);
		}
		
	}
	
	
}
