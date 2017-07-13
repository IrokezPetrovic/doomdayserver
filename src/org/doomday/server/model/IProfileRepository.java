package org.doomday.server.model;

import org.doomday.server.beans.device.DeviceProfile;

public interface IProfileRepository {
	DeviceProfile getProfile(String name);
	void addProfile(String name, DeviceProfile profile);
	boolean hasProfile(String devClass);
}
