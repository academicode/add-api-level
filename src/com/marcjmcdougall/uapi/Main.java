package com.marcjmcdougall.uapi;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


@SuppressLint("NewApi")
public class Main extends Activity {

	// Static boolean value that allows us to "turn off" the workaround functionality for 
	// demonstrative purposes.
	private static final boolean USE_WORKAROUND = true;
	
	// References to the Views that we will be using.
	private ImageView iv;
	private Button b;

	// Class variables representing the y-coordinates of the ImageView.
	private float yPos1;
	private float yPos2;	

	// Class variable used to distinguish the current state of the ImageView.
	private boolean translated;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// Mandatory method calls.
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// Linking our View objects to the XML code.
		iv = (ImageView) findViewById(R.id.imageView1);
		b = (Button) findViewById(R.id.button1);

		// Initializing our ImageView y-coordinates.
		yPos1 = 200.0f;
		yPos2 = 100.0f;

		// Initializing our state controller.
		translated = false;
		
		// Translate the ImageView to its starting position.
		translateView(iv, yPos1);
		
		// Assign a click listener to the Button object.
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// If our view is not already translated...
				if(!translated){
					
					// Translate the View!
					translateView(iv, yPos2);
					
					// Update the translation state of the View.
					translated = true;

				}else{
					
					// Otherwise, translate the view to the first position.
					translateView(iv, yPos1);

					// Update the translation state of the View.
					translated = false;
				}
			}
		});
	}

	/**
	 * This method controls the translation of the ImageView in our sample application.  
	 * 
	 * It handles the translation of the View regardless of API level, and takes advantage of
	 * more advanced methods in higher API levels (setY(float)).
	 * 
	 * @param view The View we wish to translate.
	 * @param yPos The yPosition that we wish to translate it to.
	 */
	private void translateView(View view, float yPos) {
		
		// If the device that we will run this code on is running API 11 or greater.
		if(usingAPI11()){
			
			// Simply set the y-coordinate of the ImageView using the setY(float) method.
			view.setY(yPos);

		}else{
			
			// Otherwise, we implement a workaround that "translates" the view using layout parameters.  Don't
			// worry about the implementation of this code block for now, just know that it is a workaround to
			// achieve the same translation functionality in API levels less than 11 where we don't have the 
			// setY(float) method.
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			params.topMargin = (int) yPos;
			params.leftMargin = view.getLeft();
			view.setLayoutParams(params);
		}
	}

	/**
	 * This method simply returns true if we are using a device that is running Honeycomb or 
	 * later (API Level 11).  We use this to determine when we need to rely on workarounds
	 * or not.
	 * 
	 * @return A boolean that specifies if we are using an API Level greater than or equal to 11.
	 */
	private boolean usingAPI11() {
		
		// If we are using this workaround, then return the real boolean value.
		if(USE_WORKAROUND){
			
			return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;
		}
		
		// Otherwise, always return true.  Note that this will force a crash on devices running
		// any API level less than 11.
		return true;
	}
}
