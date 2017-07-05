package org.doomday.emulator.ui;

import java.util.function.Consumer;

import org.doomday.emulator.ui.MainForm.StartParams;

public interface IMainForm {

	void setStarted(boolean value);

	void log(String line);

	void onStart(Consumer<StartParams> onStart);

	void onStop(Consumer<Boolean> onStop);

	void onDisconnect(Consumer<Boolean> onDisconnect);

	void setConnected(boolean connected);

	void setVisible(boolean visible);
	String getScriptSource();

}