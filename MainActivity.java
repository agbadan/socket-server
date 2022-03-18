package com.mycompany.SocketServer;
import android.annotation.*;
import android.app.*;
import android.content.*;
import android.icu.text.*;
import android.net.wifi.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.util.*;



@SuppressLint("SetTextI18n")
public class MainActivity extends Activity {
	ServerSocket serverSocket;
	Thread Thread1 = null;
	TextView tvIP, tvPort;
	TextView tvMessages;
	EditText etMessage;
	Button btnSend;
	public static String SERVER_IP = "";
	public static final int SERVER_PORT = 8080;
	String message;
	String mess;
	String Date ;
public	String pourcentage;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tvIP = findViewById(R.id.tvIP);
		tvPort = findViewById(R.id.tvPort);
		tvMessages = findViewById(R.id.tvMessages);
		etMessage = findViewById(R.id.etMessage);
		btnSend = findViewById(R.id.btnSend);
		
		Calendar c = Calendar.getInstance();
		
		
		int Hour= c.get(Calendar.HOUR);
		
		
		Calendar calendar=Calendar.getInstance();
		
		calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);



        Timer time = new Timer(); // Instantiate Timer Object

        // Start running the task on Monday at 15:40:00, period is set to 8 hours
        // if you want to run the task immediately, set the 2nd parameter to 0
        time.schedule(new CustomTask(), calendar.getTime(), TimeUnit.HOURS.toMillis(8));
	
		
		//Pour recuperer le temps du debut de l'ouverture de l'application
		//Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String DebutDate = sdf.format(c.getTime());
		//Pour recuperer le tempsdu debut de l'ouverture de l'application
		
		
		
	
		
		//Lancement du service background
		startService(new Intent(getApplicationContext(),MyServices.class));
		//Lancement du service background
		
		
		try {
			SERVER_IP = getLocalIpAddress();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		Thread1 = new Thread(new Thread1());
		Thread1.start();
		btnSend.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//Pour recuperer le temps
					Calendar c = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String strDate = sdf.format(c.getTime());
					//Pour recuperer le temps
					
					


					//Pour recuperer l'heure
					int Hour= c.get(Calendar.HOUR);
					//Pour recuperer l'heure
					
					//Module Pourcentage de battery
					BatteryManager bm = (BatteryManager)getSystemService(BATTERY_SERVICE);
					int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
					//Pourcentage du telephone
					pourcentage=String.valueOf(percentage);
					//Pourcentage du telephone
					//Module Pourcentage de battery
					
					
					
					message = etMessage.getText().toString().trim();
					mess = etMessage.getText().toString();
					
					message=pourcentage+" "+Hour;
					if (!message.isEmpty()) {
						new Thread(new Thread3(message)).start();
						
					
					}
					
					
					
					if (message.isEmpty()) {
						new Thread(new Thread3(message)).start();
						//finish();
						//startService(new Intent(getApplicationContext(),MyServices.class));
					
					//	android.os.Process.killProcess(android.os.Process.myPid());
						
							output.flush();
						output.close();
						
						
						
						
						//startService(new Intent(getApplicationContext(),MyServices.class));
						
					
					}
					
					
					
				}
			});
			
		
			
	}
	private String getLocalIpAddress() throws UnknownHostException {
		WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
		assert wifiManager != null;
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipInt = wifiInfo.getIpAddress();
		return InetAddress.getByAddress(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array()).getHostAddress();
	}
	private PrintWriter output;
	private BufferedReader input;
	class Thread1 implements Runnable {
		@Override
		public void run() {
			Socket socket;
			try {
				serverSocket = new ServerSocket(SERVER_PORT);
				runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tvMessages.setText("Not connected");
							tvIP.setText("IP: " + SERVER_IP);
							tvPort.setText("Port: " + String.valueOf(SERVER_PORT));
						}
					});
				try {
					socket = serverSocket.accept();
					
					
					output = new PrintWriter(socket.getOutputStream());
					input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					runOnUiThread(new Runnable() {
							@Override
							public void run() {
								tvMessages.setText("Connected\n");
							}
						});
					new Thread(new Thread2()).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private class Thread2 implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					final String message = input.readLine();
					if (message != null) {
						runOnUiThread(new Runnable() {
								@Override
								public void run() {
									tvMessages.append("client:" + message + "\n");
								}
							});
					} else {
						Thread1 = new Thread(new Thread1());
						Thread1.start();
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}  
		}
	}
	class Thread3 implements Runnable {
		private String message;
		Thread3(String message) {
			this.message = message;
		}
		@Override
		public void run() {
			output.write(message);
			output.flush();
			output.close();
			
			runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tvMessages.append("server: " + message + "\n");
						etMessage.setText("");
						
						
						
					}
				});
		}
	}}
