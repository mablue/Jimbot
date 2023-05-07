package com.mablue.jimbot;

import android.app.*;
import android.database.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import java.text.*;
import java.util.*;
import android.widget.*;
import com.mablue.jimbot.CircleView;
import android.graphics.*;
import android.content.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		logCallLog(this);
		
	}

	private void logCallLog(Context context)
	{
		final Map<String, List<Float>> callHoursForContacts = new HashMap<>();
		
		// TODO: Implement this method
		Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		
		while (cursor.moveToNext())
		{
			String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
			String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
			
			//String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
			int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));
		    long datetime = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm");
			String dateString = formatter.format(new Date(datetime));
			Date d = new Date(datetime);
			Float h =(float) d.getHours();
			Float m = (float) d.getMinutes()/60;
			Float hm = h+m;
			if (type == CallLog.Calls.INCOMING_TYPE & name != null)
				if (callHoursForContacts.containsKey(name))
					callHoursForContacts.get(name).add(hm);
				else
					callHoursForContacts.put(name, new ArrayList<Float>());
					//callHoursForContacts.get(name).add(h);
		}
		cursor.close();
		
		
		//sorting
		
		//virtualization
		RadioGroup radioGroup = findViewById(R.id.mainRadioGroup);
		
		for (Map.Entry<String,List<Float>> entry : callHoursForContacts.entrySet()) {
            if (entry.getValue().size()<1)
				continue;
			RadioButton radioButton = new RadioButton(this);
			int rc = getRandomColor(entry.getValue().size());
			radioButton.setText(entry.getKey());
			
			radioButton.setTextColor(rc);
			
			radioGroup.addView(radioButton);
			
			radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						RadioButton radioButton = findViewById(checkedId);
						int contactColor = radioButton.getCurrentTextColor();
						String contactName = radioButton.getText().toString();
						UpdateCircle((ArrayList<Float>) callHoursForContacts.get(contactName),contactColor);
						
					}

					
				});
			
		}
		//lv.setAdapter(adapter);
	
	}
	private void UpdateCircle(List<Float> markerValues,int markerColor){
		CircleView mainCircleView = findViewById(R.id.mainCircleView);
		//mainCircleView.AddMarkers(color,callHours);
		Color color = Color.valueOf(markerColor);
		color.alpha(10);
		mainCircleView.addMarker(markerValues,markerColor);//color.hashCode());
		
			
	}

	private int getRandomColor(int devider) {
		Random rand = new Random();
		int a = 256/devider;
		int r = rand.nextInt(256);
		int g = rand.nextInt(256);
		int b = rand.nextInt(256);
		return Color.argb(a, r, g, b);
	}
	

}
