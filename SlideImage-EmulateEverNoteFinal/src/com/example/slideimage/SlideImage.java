package com.example.slideimage;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressLint("NewApi")
public class SlideImage {
	
	private ImageView rootImage = null;
	private ObjectAnimator rootImageSlideInAnimator = null;
	private ObjectAnimator rootImageSlideOutAnimator = null;
	
	private List<ImageView> itemBtnImageList = new ArrayList<ImageView>();
	private List<ImageView> itemTextImageList = new ArrayList<ImageView>();
	
	private List<ObjectAnimator> slideInAnimatorList = new ArrayList<ObjectAnimator>();
	private List<ObjectAnimator> slideOutAnimatorList = new ArrayList<ObjectAnimator>();
	
	private boolean isItemShow = false;
	
	public void init(ViewGroup parent, int rootImageSrc, int [][] itemImageSrcArr, Context context){
		addImage(parent, rootImageSrc, itemImageSrcArr, context);
		addAnimator();
		registeRootImageClickListener();
		hideItemImageText();
	}
	
	public void addImage(ViewGroup parent, int rootImageSrc, int [][] itemImageSrcArr, Context context){
		rootImage = createImage(rootImageSrc, context);
		setImageBtnPosition(rootImage, 50);
		
		for(int [] itemImageSrc : itemImageSrcArr){
			int imageBtnSrc = itemImageSrc[0];
			int imageTextSrc = itemImageSrc[1];

			ImageView itemBtnImage = createImage(imageBtnSrc, context);
			setImageBtnPosition(itemBtnImage, 50);
			
			parent.addView(itemBtnImage);
			itemBtnImageList.add(itemBtnImage);
			
			
			ImageView itemTextImage = createImage(imageTextSrc, context);
			setImageTextPosition(itemTextImage, 90);
			
			parent.addView(itemTextImage);
			itemTextImageList.add(itemTextImage);
		}

		parent.addView(rootImage);
	}
	
	public void addAnimator(){
		setRootImageSlideInAnimation(rootImage);
		setRootImageSlideOutAnimation(rootImage);
		
		float slideInY = -30f;
		for(ImageView imageView : itemBtnImageList){
			ObjectAnimator animator = getItemImageBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			animator = getItemImageBtnSlideOutAnimation(imageView, slideInY);
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - 30f;
		}
		
		slideInY = -30f;
		for(ImageView imageView : itemTextImageList){
			ObjectAnimator animator = getItemImageTextBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			animator = getItemImageTextSlideOutAnimation(imageView, slideInY);
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - 30f;
		}
		
		
	}
	
	public void registeRootImageClickListener(){
		rootImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(isItemShow){
					isItemShow = false;
					hideItemImageText();
					rootImageSlideOutAnimator.start();
					for(ObjectAnimator animator : slideOutAnimatorList){
						animator.start();
					}
				}else{
					isItemShow = true;
					showItemImageText();
					rootImageSlideInAnimator.start();
					for(ObjectAnimator animator : slideInAnimatorList){
						animator.start();
					}
				}
				
				
			}
		});
	}
	
	
	
	public ImageView createImage(int src, Context context){
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(src);
		
		return imageView;
	}
	
	public void setImageBtnPosition(ImageView imageView, int rightMargin){
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.bottomMargin = 50;
		params.rightMargin = rightMargin;
		imageView.setLayoutParams(params);
	}
	
	public void setImageTextPosition(ImageView imageView, int rightMargin){
		setImageBtnPosition(imageView, rightMargin);
	}
	
	public void setRootImageSlideInAnimation(ImageView rootImageView) {
		PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", 0f, 215f);
		rootImageSlideInAnimator = ObjectAnimator.ofPropertyValuesHolder(rootImageView, holderR);
		rootImageSlideInAnimator.setDuration(200);
		rootImageSlideInAnimator.setInterpolator(new OvershootInterpolator());
		
	}
	
	public void setRootImageSlideOutAnimation(ImageView rootImageView) {
		PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", 215f, 0f);
		rootImageSlideOutAnimator = ObjectAnimator.ofPropertyValuesHolder(rootImageView, holderR);
		rootImageSlideOutAnimator.setDuration(200);
		rootImageSlideOutAnimator.setInterpolator(new OvershootInterpolator());
	}
	
	
	public ObjectAnimator getItemImageBtnSlideInAnimation(ImageView imageView, float y){
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, y);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(400);
		animator.setInterpolator(new OvershootInterpolator());
		
		return animator;
	}
	
	public ObjectAnimator getItemImageTextBtnSlideInAnimation(ImageView imageView, float y){
		return getItemImageBtnSlideInAnimation(imageView, y);
	}
	
	public ObjectAnimator getItemImageBtnSlideOutAnimation(ImageView imageView, float y){
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", y, 0f);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(100);
		animator.setInterpolator(new LinearInterpolator());
		
		return animator;
	}
	
	public ObjectAnimator getItemImageTextSlideOutAnimation(ImageView imageView, float y){
		return getItemImageBtnSlideOutAnimation(imageView,y);
	}
	
	public void hideItemImageText(){
		for(ImageView imageView : itemTextImageList){
			imageView.setVisibility(View.INVISIBLE);
		}
	}
	
	public void showItemImageText(){
		for(ImageView imageView : itemTextImageList){
			imageView.setVisibility(View.VISIBLE);
		}
	}

}
