package com.example.animationtest3;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private boolean isItemShow = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		final ImageView image2 = (ImageView) findViewById(R.id.imageView2);
		final ImageView image3 = (ImageView) findViewById(R.id.imageView3);
		final ImageView image4 = (ImageView) findViewById(R.id.imageView4);
		
		PropertyValuesHolder image1ShowItemHolderR = PropertyValuesHolder.ofFloat("rotation", 0f, 215f);
		final ObjectAnimator image1ShowItemAnimator = ObjectAnimator.ofPropertyValuesHolder(image1, image1ShowItemHolderR);
		image1ShowItemAnimator.setDuration(200);
		image1ShowItemAnimator.setInterpolator(new OvershootInterpolator());

		PropertyValuesHolder image2ShowHolderY = PropertyValuesHolder.ofFloat("translationY", 0f, -30f);
		final ObjectAnimator image2ShowAnimator = ObjectAnimator.ofPropertyValuesHolder(image2, image2ShowHolderY);
		image2ShowAnimator.setDuration(400);
		image2ShowAnimator.setInterpolator(new OvershootInterpolator());
		
		PropertyValuesHolder image3ShowHolderY = PropertyValuesHolder.ofFloat("translationY", 0f, -60f);
		final ObjectAnimator image3ShowAnimator = ObjectAnimator.ofPropertyValuesHolder(image3, image3ShowHolderY);
		image3ShowAnimator.setDuration(400);
		image3ShowAnimator.setInterpolator(new OvershootInterpolator());
		
		PropertyValuesHolder image4ShowHolderY = PropertyValuesHolder.ofFloat("translationY", 0f, -90f);
		final ObjectAnimator image4ShowAnimator = ObjectAnimator.ofPropertyValuesHolder(image4, image4ShowHolderY);
		image4ShowAnimator.setDuration(400);
		image4ShowAnimator.setInterpolator(new OvershootInterpolator());
		
		
		PropertyValuesHolder image1HideItemHolderR = PropertyValuesHolder.ofFloat("rotation", 215f, 0f);
		final ObjectAnimator image1HideItemAnimator = ObjectAnimator.ofPropertyValuesHolder(image1, image1HideItemHolderR);
		image1HideItemAnimator.setDuration(200);
		image1HideItemAnimator.setInterpolator(new OvershootInterpolator());
		
		PropertyValuesHolder image2HideHolderY = PropertyValuesHolder.ofFloat("translationY", -30f, 0f);
		final ObjectAnimator image2HideAnimator = ObjectAnimator.ofPropertyValuesHolder(image2, image2HideHolderY);
		image2HideAnimator.setDuration(100);
		image2HideAnimator.setInterpolator(new LinearInterpolator());
		
		PropertyValuesHolder image3HideHolderY = PropertyValuesHolder.ofFloat("translationY", -60f, 0f);
		final ObjectAnimator image3HideAnimator = ObjectAnimator.ofPropertyValuesHolder(image3, image3HideHolderY);
		image3HideAnimator.setDuration(100);
		image3HideAnimator.setInterpolator(new LinearInterpolator());
		
		PropertyValuesHolder image4HideHolderY = PropertyValuesHolder.ofFloat("translationY", -90f, 0f);
		final ObjectAnimator image4HideAnimator = ObjectAnimator.ofPropertyValuesHolder(image4, image4HideHolderY);
		image4HideAnimator.setDuration(100);
		image4HideAnimator.setInterpolator(new LinearInterpolator());
		
		
		image1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isItemShow){
					isItemShow = false;
					image1HideItemAnimator.start();
					image2HideAnimator.start();
					image3HideAnimator.start();
					image4HideAnimator.start();
				}else{
					isItemShow = true;
					image1ShowItemAnimator.start();
					image2ShowAnimator.start();
					image3ShowAnimator.start();
					image4ShowAnimator.start();
				}
				
				
			}
		});
		
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
