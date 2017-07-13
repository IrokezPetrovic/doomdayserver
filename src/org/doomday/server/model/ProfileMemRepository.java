package org.doomday.server.model;

import java.util.HashMap;
import java.util.Map;

import org.doomday.server.beans.device.DeviceProfile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMemRepository implements IProfileRepository{

	private Map<String, DeviceProfile> profiles = new HashMap<>();
	
	@Override
	public DeviceProfile getProfile(String name) {
		return profiles.get(name);
	}

	@Override
	public void addProfile(String name, DeviceProfile profile) {
		profiles.put(name, profile);
		
	}

	@Override
	public boolean hasProfile(String devClass) {
		return profiles.containsKey(devClass);
	}

}
