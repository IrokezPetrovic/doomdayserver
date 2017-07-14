package org.doomday.emulator.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jsyntaxpane.syntaxkits.JavaScriptSyntaxKit;

public class MainForm extends JFrame implements IMainForm{
	public static class StartParams{
		private final String mcastIp;
		private final Integer mcastPort;
		private final Integer ipPort;
		public StartParams(String mcastIp, Integer mcastPort, Integer ipPort) {
			super();
			this.mcastIp = mcastIp;
			this.mcastPort = mcastPort;
			this.ipPort = ipPort;
		}
		public String getMcastIp() {
			return mcastIp;
		}
		public Integer getMcastPort() {
			return mcastPort;
		}
		public Integer getIpPort() {
			return ipPort;
		}				
	}
	private final JButton startBtn;
	private boolean started = false;
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#setStarted(boolean)
	 */
	@Override
	public void setStarted(boolean value){
		this.started = value;
		startBtn.setText(started?"STOP":"START");
		startBtn.setEnabled(true);
		codeEditor.setEnabled(!started);
		mcastIp.setEnabled(!started);
		mcastPort.setEnabled(!started);
		ipPort.setEnabled(!started);
		
	}
	
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#log(java.lang.String)
	 */
	@Override
	public void log(String line){
		console.setText(console.getText()+line+"\n");
		invalidate();
		JScrollBar verticalScrollBar = consoleScroll.getVerticalScrollBar();
		verticalScrollBar.setValue(verticalScrollBar.getMaximum());
	}
	private Consumer<StartParams> onStart;
	private Consumer<Boolean> onStop;
	private Consumer<Boolean> onDisconnect;
	
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#onStart(java.util.function.Consumer)
	 */
	@Override
	public void onStart(Consumer<StartParams> onStart) {
		this.onStart = onStart;		
	}	
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#onStop(java.util.function.Consumer)
	 */
	@Override
	public void onStop(Consumer<Boolean> onStop) {
		this.onStop = onStop;
	}
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#onDisconnect(java.util.function.Consumer)
	 */
	@Override
	public void onDisconnect(Consumer<Boolean> onDisconnect) {
		this.onDisconnect = onDisconnect;
	}
	
	private boolean connected = false;
	private JLabel connectStatus;
	private JButton disconnectBtn;
	
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#setConnected(boolean)
	 */
	@Override
	public void setConnected(boolean connected) {
		this.connected = connected;
		this.connectStatus.setText("CONECTION:"+String.valueOf(connected));
		this.connectStatus.setForeground(connected?Color.GREEN:Color.RED);
		disconnectBtn.setVisible(connected);
	}
	
	/* (non-Javadoc)
	 * @see org.doomday.emulator.ui.IMainForm#getScriptSource()
	 */
	@Override
	public String getScriptSource(){
		return codeEditor.getText();
	}
	
	public MainForm() {		
		super();
		setSize(700, 500);
		JPanel panel = new JPanel();
		add(panel);
		
		panel.setLayout(new BorderLayout());				
		codeEditor = new JEditorPane();
		JScrollPane scrPane = new JScrollPane(codeEditor);
		
		panel.add(scrPane,BorderLayout.CENTER);
		codeEditor.setEditorKit(new JavaScriptSyntaxKit());		
		
		
		JPanel sidePanel = new JPanel();
				
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));		
				
		sidePanel.setSize(100, 100);
		sidePanel.setPreferredSize(new Dimension(300, 300));
		JButton btnSave = new JButton("LOAD SCRIPT");
		sidePanel.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedReader br = new BufferedReader(new FileReader("/tmp/devscript.js"));
					String l = null;
					StringBuilder sb = new StringBuilder();
					while((l = br.readLine())!=null){
						sb.append(l);
						sb.append("\n");
					}
					br.close();
					codeEditor.setText(sb.toString());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton btnLoad = new JButton("SAVE SCRIPT");
		sidePanel.add(btnLoad);
		btnLoad.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (codeEditor.getText().trim().isEmpty()) return;
				try {
					FileWriter fw = new FileWriter("/tmp/devscript.js");
					fw.write(codeEditor.getText());
					fw.flush();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		mcastIp = new JTextField();						
		mcastIp.setPreferredSize(new Dimension(100, 20));
		mcastIp.setText("239.141.12.12");
		mcastPort = new JTextField();
		mcastPort.setText("27015");
		ipPort = new JTextField();		
		ipPort.setText("27015");
				
		
		sidePanel.add(new JLabel("MCAST GROUP:"));
		sidePanel.add(mcastIp);
		
		sidePanel.add(new JLabel("MCAST PORT:"));
		sidePanel.add(mcastPort);
		
		sidePanel.add(new JLabel("IP PORT:"));
		sidePanel.add(ipPort);
		
				
		connectStatus = new JLabel("CONNECTED:"+String.valueOf(connected));
		connectStatus.setForeground(Color.RED);
		sidePanel.add(connectStatus);
		
		startBtn = new JButton("START");
		startBtn.addActionListener(new ActionListener() {
			Pattern addrPattern = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}");
			Pattern intPattern = Pattern.compile("[0-9]{2,5}");
			@Override
			public void actionPerformed(ActionEvent e) {				
				if (!started){
					if (!addrPattern.matcher(mcastIp.getText()).matches()){
						log("Incorrect MCAST GROUP:"+mcastIp.getText());
						return;
					}
					
					if (!intPattern.matcher(mcastPort.getText()).matches()){
						log("Incorrect MCAST PORT:"+mcastPort.getText());
						return;
					}
					
					if (!intPattern.matcher(ipPort.getText()).matches()){
						log("Incorrect IP PORT:"+ipPort.getText());
						return;
					}
					
					if (onStart!=null){
						startBtn.setEnabled(false);
						onStart.accept(new StartParams(mcastIp.getText(), Integer.valueOf(mcastPort.getText()),Integer.valueOf(ipPort.getText())));												
					}
				} else {
					if (onStop!=null){
						startBtn.setEnabled(false);
						onStop.accept(true);
						
					}
				}				
			}
		});
		
		disconnectBtn = new JButton("DISCONNECT");
		disconnectBtn.setVisible(false);
		disconnectBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (onDisconnect!=null)
					onDisconnect.accept(true);
			}
		});
		
		sidePanel.add(disconnectBtn);		
		sidePanel.add(startBtn);
		panel.add(sidePanel,BorderLayout.EAST);
		
		console = new JTextArea();
		
		console.setEditable(false);
		consoleScroll = new JScrollPane(console);
		consoleScroll.setAutoscrolls(true);
		consoleScroll.setPreferredSize(new Dimension(100, 100));
		panel.add(consoleScroll,BorderLayout.SOUTH);		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	
	private static final long serialVersionUID = -6611246684160395406L;
	private JEditorPane codeEditor;
	private JTextArea console;
	private JTextField mcastIp;
	private JTextField mcastPort;
	private JTextField ipPort;
	private JScrollPane consoleScroll;
	
	

}
