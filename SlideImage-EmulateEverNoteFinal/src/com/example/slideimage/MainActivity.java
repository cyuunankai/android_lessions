package com.example.slideimage;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

    private final static int IMAGE = R.drawable.ic_launcher;
    private final static int ROOT_IMAGE = R.drawable.ic_launcher;
    private String tag = "MainActivity";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(tag, "onCreate");
		
		ViewGroup parent = (ViewGroup) findViewById(R.id.parent);
		List<ImageSrcIntent> itemImageSrcIntentList = getInitialItemImageSrcAndIntentList();
		SlideImage slideImage = new SlideImage();
		slideImage.init(parent, ROOT_IMAGE, itemImageSrcIntentList, this);
	
		
		List<ImageIntentBean> itemBtnImageIntentList = slideImage.getItemBtnImageList();
		addItemClickListener(itemBtnImageIntentList);
		List<ImageIntentBean> itemTextImageIntentList = slideImage.getItemTextImageList();
		addItemClickListener(itemTextImageIntentList);
		
	}
	
	/**
	 * get initial item image src and intent
	 * @return
	 */
	private List<ImageSrcIntent> getInitialItemImageSrcAndIntentList(){
		List<ImageSrcIntent> imageSrcIntentList = new ArrayList<ImageSrcIntent>();
		
		ImageSrcIntent imageSrcIntent = new ImageSrcIntent();
		imageSrcIntent.itemButtonSrc = IMAGE;
		imageSrcIntent.itemTextSrc = IMAGE;
		imageSrcIntent.intentClass = MyNewActivity.class;
		imageSrcIntentList.add(imageSrcIntent);
		
		imageSrcIntent = new ImageSrcIntent();
		imageSrcIntent.itemButtonSrc = IMAGE;
		imageSrcIntent.itemTextSrc = IMAGE;
		imageSrcIntent.intentClass = MyEditActivity.class;
		imageSrcIntentList.add(imageSrcIntent);
		
		imageSrcIntent = new ImageSrcIntent();
		imageSrcIntent.itemButtonSrc = IMAGE;
		imageSrcIntent.itemTextSrc = IMAGE;
		imageSrcIntent.intentClass = MyDetailActivity.class;
		imageSrcIntentList.add(imageSrcIntent);
		
		return imageSrcIntentList;
	}
	
	/**
	 * add item click listener
	 */
	private void addItemClickListener(List<ImageIntentBean> imageIntentList){
		for(ImageIntentBean imageIntent : imageIntentList){
			ImageView imageView = imageIntent.imageView;
			final Class intentClass = imageIntent.intentClass;
			imageView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(getApplicationContext(), intentClass);
					startActivity(intent);
				}
			});
		}
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
	
	
	@Override
	public void onStart(){
	super.onStart();
	Log.i(tag, "onStart");
	}

	@Override
	public void onPause(){
	super.onPause();
	Log.i(tag, "onPause");
	}

	@Override
	public void onResume(){
	super.onResume();
	Log.i(tag, "onResume");
	}

	@Override
	public void onStop(){
	super.onStop();
	Log.i(tag, "onStop");
	}

	@Override
	public void onDestroy(){
	super.onDestroy();
	Log.i(tag, "onDestroy");
	}
	

}
