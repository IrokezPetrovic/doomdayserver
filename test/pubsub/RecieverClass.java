package pubsub;

import org.doomday.server.eventbus.Reciever;

public class RecieverClass {

	@Reciever("/device/*")
	public void recieve(String arg){
		
	}
		
	@Reciever("/key")
	public void recieve(EventClass e){
		System.out.println("Recieved!");
	}
		
	@Reciever("/key/")
	public void recieve(EventSubclass e){
		
	}
}
