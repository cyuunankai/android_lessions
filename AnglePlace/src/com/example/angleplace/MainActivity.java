package com.example.angleplace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private static int RESULT_LOAD_IMAGE = 1;
	Bitmap b;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View arg0) {
                 
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            try {
				b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
				ImageView imageView = (ImageView) findViewById(R.id.imgView);
				imageView.setImageBitmap(b);
            } catch (FileNotFoundException e) {
			} catch (IOException e) {
			} 
        }
    }
	
	public void saveImage(View v){
		saveToInternalStorage(b);
	}
	
	public void saveToInternalStorage(Bitmap outputImage){

        File storagePath = new File(getApplicationContext().getFilesDir() + "/MyPhotos/"); 
        storagePath.mkdirs(); 

        File myImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".jpg");

        try { 
            FileOutputStream out = new FileOutputStream(myImage); 
            outputImage.compress(Bitmap.CompressFormat.JPEG, 80, out); 
            out.flush();    
            out.close();
        } catch (Exception e) { 
            e.printStackTrace(); 
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
}
