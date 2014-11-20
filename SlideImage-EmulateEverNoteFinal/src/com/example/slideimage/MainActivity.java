package com.example.slideimage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ViewGroup mRootView;
    private FrameLayout mFloatLayout;
    private ImageView mHoverView = null;
    private final static int IMAGE = R.drawable.ic_launcher;
    private final static int ROOT_IMAGE = R.drawable.ic_launcher;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
		int [][] itemImageSrcArr = {{IMAGE, IMAGE}, {IMAGE, IMAGE}, {IMAGE, IMAGE}};
		SlideImage slideImage = new SlideImage();
		slideImage.init(parent, ROOT_IMAGE, itemImageSrcArr, this);

		
		
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
