package emulator;

import java.util.stream.Stream;

import org.doomday.server.beans.device.trigger.TriggerMeta;
import org.doomday.server.beans.device.trigger.TriggerParam;

public class Trigger {
	private final TriggerMeta t;
		
	public Trigger(TriggerMeta t) {	
		this.t = t;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("TRIGGER ");
		sb.append(t.getName());
		sb.append(" ");
		Stream.of(t.getParams())
		.map(TriggerParam::toString)
		.forEach(sb::append);
		
		return sb.toString();
	};
}
