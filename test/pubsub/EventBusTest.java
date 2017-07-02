package pubsub;

import org.doomday.server.eventbus.EventBus;
import org.doomday.server.eventbus.IEventBus;
import org.doomday.server.eventbus.Reciever;
import org.junit.Test;

public class EventBusTest {
	class Sub{
		@Reciever
		public void reciever(EventClass e){
			System.out.println("EventClass recieved");
		}
	}
	
	@Test
	public void test(){
		IEventBus bus = new EventBus();
		RecieverClass rec = new RecieverClass();
		bus.subscribe(rec);
		bus.subscribe(new Sub());
		bus.emit("", new EventClass(), false);		
	}
}
