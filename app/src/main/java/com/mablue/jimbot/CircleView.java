package com.mablue.jimbot;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import java.util.*;

public class CircleView extends View
 {
    private Paint paint;
    private int centerX, centerY, radius;
    private List<Float> markers;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5f);

        markers = new ArrayList<>();
        //markers.add(1);
        //markers.add(3);
        //markers.add(5);
        //markers.add(7);
    }
	
	public void addMarker(List<Float> values, int color) {
		markers=values;
		paint.setColor(color);
		invalidate(); // Redraw the view
	}
	
	
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = Math.min(centerX, centerY) - 20;


        // Draw markers
        for (int i = 0; i < markers.size(); i++) {
            float value = markers.get(i);
            double angle = Math.toRadians(value * 15); // 360/24
            float markerX = centerX + (float) (radius * 0.7 * Math.sin(angle));
            float markerY = centerY - (float) (radius * 0.7 * Math.cos(angle));
            canvas.drawCircle(markerX, markerY, 50, paint);
			paint.setTextSize(30);
            canvas.drawText(String.valueOf((int)value), markerX-15, markerY+15, paint);
        }
		
		paint.setColor(Color.BLACK);
		// Draw circle
        canvas.drawCircle(centerX, centerY, radius, paint);

        // Divide circle into 24 parts
        for (int i = 0; i < 24; i++) {
            double angle = Math.toRadians(i * 15); // 360/24
            float startX = centerX + (float) (radius * Math.sin(angle));
            float startY = centerY - (float) (radius * Math.cos(angle));
            float endX = centerX + (float) (radius * 0.9 * Math.sin(angle));
            float endY = centerY - (float) (radius * 0.9 * Math.cos(angle));
            canvas.drawLine(startX, startY, endX, endY, paint);
        }
		
    }
}
