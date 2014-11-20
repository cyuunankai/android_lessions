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

	public void init(ViewGroup parent, int rootImageSrc, int[][] itemImageSrcArr, Context context) {
		addImage(parent, rootImageSrc, itemImageSrcArr, context);
		addAnimator();
		registeRootImageClickListener();
		hideItemImageText();
	}

	/**
	 * add image to parent view
	 */
	public void addImage(ViewGroup parent, int rootImageSrc, int[][] itemImageSrcArr, Context context) {
		
		// root image
		rootImage = createImage(rootImageSrc, context);
		setImageBtnPosition(rootImage, 50);

		for (int[] itemImageSrc : itemImageSrcArr) {
			int imageBtnSrc = itemImageSrc[0];
			int imageTextSrc = itemImageSrc[1];
			
			// item button
			ImageView itemBtnImage = createImage(imageBtnSrc, context);
			setImageBtnPosition(itemBtnImage, 50);

			parent.addView(itemBtnImage);
			itemBtnImageList.add(itemBtnImage);
			
			// item text
			ImageView itemTextImage = createImage(imageTextSrc, context);
			setImageTextPosition(itemTextImage, 90);

			parent.addView(itemTextImage);
			itemTextImageList.add(itemTextImage);
		}
		
		// top z-index
		parent.addView(rootImage);
	}

	/**
	 * add animator for image
	 */
	public void addAnimator() {
		// root image animator
		setRootImageSlideInAnimation(rootImage);
		setRootImageSlideOutAnimation(rootImage);

		// item button animator
		float slideInY = -30f;
		for (ImageView imageView : itemBtnImageList) {
			// slide in
			ObjectAnimator animator = getItemImageBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			animator = getItemImageBtnSlideOutAnimation(imageView, slideInY);
			// slide out
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - 30f;
		}
		
		// item text animator
		slideInY = -30f;
		for (ImageView imageView : itemTextImageList) {
			// slide in
			ObjectAnimator animator = getItemImageTextBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			// slide out
			animator = getItemImageTextSlideOutAnimation(imageView, slideInY);
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - 30f;
		}

	}

	/**
	 * registe root image click listener
	 */
	public void registeRootImageClickListener() {
		rootImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				if (isItemShow) {
					// item is shown
					isItemShow = false;
					hideItemImageText();
					
					// root image slide out animate
					rootImageSlideOutAnimator.start();
					
					// item image slide out animate
					for (ObjectAnimator animator : slideOutAnimatorList) {
						animator.start();
					}
				} else {
					// item is not shown
					isItemShow = true;
					showItemImageText();
					
					// root image slide in animate
					rootImageSlideInAnimator.start();
					
					// item image slide in animate
					for (ObjectAnimator animator : slideInAnimatorList) {
						animator.start();
					}
				}

			}
		});
	}

	/**
	 * create image
	 */
	public ImageView createImage(int src, Context context) {
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(src);

		return imageView;
	}

	/**
	 * set root image and item image position 
	 */
	public void setImageBtnPosition(ImageView imageView, int rightMargin) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.bottomMargin = 50;
		params.rightMargin = rightMargin;
		imageView.setLayoutParams(params);
	}

	/**
	 * set item text position
	 */
	public void setImageTextPosition(ImageView imageView, int rightMargin) {
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

	public ObjectAnimator getItemImageBtnSlideInAnimation(ImageView imageView, float y) {
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, y);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(400);
		animator.setInterpolator(new OvershootInterpolator());

		return animator;
	}

	public ObjectAnimator getItemImageTextBtnSlideInAnimation(ImageView imageView, float y) {
		return getItemImageBtnSlideInAnimation(imageView, y);
	}

	public ObjectAnimator getItemImageBtnSlideOutAnimation(ImageView imageView, float y) {
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", y, 0f);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(100);
		animator.setInterpolator(new LinearInterpolator());

		return animator;
	}

	public ObjectAnimator getItemImageTextSlideOutAnimation(ImageView imageView, float y) {
		return getItemImageBtnSlideOutAnimation(imageView, y);
	}

	public void hideItemImageText() {
		for (ImageView imageView : itemTextImageList) {
			imageView.setVisibility(View.INVISIBLE);
		}
	}

	public void showItemImageText() {
		for (ImageView imageView : itemTextImageList) {
			imageView.setVisibility(View.VISIBLE);
		}
	}

}
