package emulator;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestEmulator {

	@Test
	public void test() throws InterruptedException{
		Emulator emu = new Emulator("12345", "TEST", "235.49.49.49",27015,27015);
		emu.begin();
		TimeUnit.SECONDS.sleep(4);
	}
}
