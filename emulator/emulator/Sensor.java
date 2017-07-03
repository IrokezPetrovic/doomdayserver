package emulator;

import org.doomday.server.beans.device.sensor.BoolSensorMeta;
import org.doomday.server.beans.device.sensor.FlagSensorMeta;
import org.doomday.server.beans.device.sensor.FloatSensorMeta;
import org.doomday.server.beans.device.sensor.IntSensorMeta;
import org.doomday.server.beans.device.sensor.SensorMeta;
import org.doomday.server.beans.device.sensor.StrSensorMeta;
import org.doomday.server.beans.device.sensor.ValSensorMeta;

public class Sensor {
	private final SensorMeta sensor;

	public Sensor(SensorMeta sensor) {
		super();
		this.sensor = sensor;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("SENSOR ");
		if (sensor.getClass().equals(IntSensorMeta.class)){
			IntSensorMeta s = (IntSensorMeta) sensor;
			sb.append("INT ");
			sb.append(s.getName());
			sb.append(" (");
			sb.append(s.getMin());
			sb.append(",");
			sb.append(s.getMax());
			sb.append(")");
		}
		if (sensor.getClass().equals(FloatSensorMeta.class)){
			FloatSensorMeta s = (FloatSensorMeta) sensor;
			sb.append("FLOAT ");
			sb.append(s.getName());
			sb.append(" (");
			sb.append(s.getMin());
			sb.append(",");
			sb.append(s.getMax());
			sb.append(")");
		}
		if (sensor.getClass().equals(StrSensorMeta.class)){			
			sb.append("STR ");
			sb.append(sensor.getName());			
		}
		if (sensor.getClass().equals(BoolSensorMeta.class)){			
			sb.append("BOOL ");
			sb.append(sensor.getName());			
		}
		if (sensor.getClass().equals(ValSensorMeta.class)){
			ValSensorMeta s = (ValSensorMeta) sensor;
			sb.append("VAL ");
			sb.append(sensor.getName());
			sb.append(" (");
			sb.append(String.join(",", s.getOptions()));
			sb.append(")");
		}
		if (sensor.getClass().equals(FlagSensorMeta.class)){
			FlagSensorMeta s = (FlagSensorMeta) sensor;
			sb.append("FLAG ");
			sb.append(sensor.getName());
			sb.append(" (");			
			sb.append(String.join(",", s.getFlags()));
			sb.append(")");
		}				
		return sb.toString();
	}
}
