package pooky.kart.batterylevel;



import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
TextView button;
double level=-1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	button=(TextView) findViewById(R.id.textView1);
	getBatteryLevel();
	
	}

	private void getBatteryLevel() {
		BroadcastReceiver bc= new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				context.unregisterReceiver(this);
				float batteryLevel=intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				float batteryCapacity=intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				if(batteryLevel>0 && batteryCapacity>0 ){
	level=(int) (batteryLevel*100)/  batteryCapacity;
	button.setText("The battery level of the device is :"+level+" %");
				}
			
			
			
			
			    if(level<=35)
			    {
				NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//		        NotificationCompat.Builder notif=new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher).setContentTitle("Battery notification").setContentText(button.getText().toString());
//				notificationManager.notify(1000, notif.build());
				
				Notification notif  = new Notification.Builder(getApplicationContext())
		        .setContentTitle("Battery Level low. Use Chargeup?")
		        .setSmallIcon(R.drawable.ic_launcher).build();
				
				notif.defaults |=notif.DEFAULT_VIBRATE;
				notificationManager.notify(1000, notif);
			    }
			}
			};
		IntentFilter intentFilter =new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(bc, intentFilter);
}
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
