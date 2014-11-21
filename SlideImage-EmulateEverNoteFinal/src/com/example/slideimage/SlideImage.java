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

	private List<ImageIntentBean> itemBtnImageList = new ArrayList<ImageIntentBean>();
	private List<ImageIntentBean> itemTextImageList = new ArrayList<ImageIntentBean>();

	private List<ObjectAnimator> slideInAnimatorList = new ArrayList<ObjectAnimator>();
	private List<ObjectAnimator> slideOutAnimatorList = new ArrayList<ObjectAnimator>();

	private boolean isItemShow = false;
	
	private final static int BOTTOM_MARGIN = 50;
	private final static int ITEM_BUTTON_RIGHT_MARGIN = 50;
	private final static int ITEM_TEXT_RIGHT_MARGIN = 90;
	private final static float ROTATION = 215f;
	private final static float TRANSLATION_DISTANCE = 30f;
	
	private final static int ROOT_SLIDE_IN_DURATION = 200;
	private final static int ITEM_SLIDE_IN_DURATION = 400;
	private final static int ROOT_SLIDE_OUT_DURATION = 200;
	private final static int ITEM_SLIDE_OUT_DURATION = 100;

	public void init(ViewGroup parent, int rootImageSrc, List<ImageSrcIntent> imageSrcIntentList, Context context) {
		addImage(parent, rootImageSrc, imageSrcIntentList, context);
		hideItemImageText();
		addAnimator();
		registeRootImageClickListener();
	}

	/**
	 * add image to parent view
	 */
	public void addImage(ViewGroup parent, int rootImageSrc, List<ImageSrcIntent> imageSrcIntentList, final Context context) {
		
		// root image
		rootImage = createImage(rootImageSrc, context);
		setImageBtnPosition(rootImage, ITEM_BUTTON_RIGHT_MARGIN);

		for (ImageSrcIntent imageSrcIntent : imageSrcIntentList) {
			int imageBtnSrc = imageSrcIntent.itemButtonSrc;
			int imageTextSrc = imageSrcIntent.itemTextSrc;
			Class intentClass = imageSrcIntent.intentClass;
			
			// item button
			ImageView itemBtnImage = createImage(imageBtnSrc, context);
			setImageBtnPosition(itemBtnImage, 50);

			parent.addView(itemBtnImage);
			
			ImageIntentBean imageIntent = new ImageIntentBean();
			imageIntent.imageView = itemBtnImage;
			imageIntent.intentClass = intentClass;
			itemBtnImageList.add(imageIntent);
			
			
			// item text
			ImageView itemTextImage = createImage(imageTextSrc, context);
			setImageTextPosition(itemTextImage, ITEM_TEXT_RIGHT_MARGIN);

			parent.addView(itemTextImage);
			
			imageIntent = new ImageIntentBean();
			imageIntent.imageView = itemTextImage;
			imageIntent.intentClass = intentClass;
			itemTextImageList.add(imageIntent);
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
		float slideInY = -TRANSLATION_DISTANCE;
		for (ImageIntentBean imageIntentBean : itemBtnImageList) {
			ImageView imageView = imageIntentBean.imageView;
			// slide in
			ObjectAnimator animator = getItemImageBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			animator = getItemImageBtnSlideOutAnimation(imageView, slideInY);
			// slide out
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - TRANSLATION_DISTANCE;
		}
		
		// item text animator
		slideInY = -TRANSLATION_DISTANCE;
		for (ImageIntentBean imageIntentBean : itemTextImageList) {
			ImageView imageView = imageIntentBean.imageView;
			// slide in
			ObjectAnimator animator = getItemImageTextBtnSlideInAnimation(imageView, slideInY);
			slideInAnimatorList.add(animator);
			// slide out
			animator = getItemImageTextSlideOutAnimation(imageView, slideInY);
			slideOutAnimatorList.add(animator);
			slideInY = slideInY - TRANSLATION_DISTANCE;
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
		params.bottomMargin = BOTTOM_MARGIN;
		params.rightMargin = rightMargin;
		imageView.setLayoutParams(params);
	}

	/**
	 * set item text position
	 */
	public void setImageTextPosition(ImageView imageView, int rightMargin) {
		setImageBtnPosition(imageView, rightMargin);
	}

	/**
	 * set root image slide in animation
	 */
	public void setRootImageSlideInAnimation(ImageView rootImageView) {
		PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", 0f, ROTATION);
		rootImageSlideInAnimator = ObjectAnimator.ofPropertyValuesHolder(rootImageView, holderR);
		rootImageSlideInAnimator.setDuration(ROOT_SLIDE_IN_DURATION);
		rootImageSlideInAnimator.setInterpolator(new OvershootInterpolator());

	}
	
	/**
	 * get item button slide in animation
	 */
	public ObjectAnimator getItemImageBtnSlideInAnimation(ImageView imageView, float y) {
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0f, y);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(ITEM_SLIDE_IN_DURATION);
		animator.setInterpolator(new OvershootInterpolator());

		return animator;
	}
	
	/**
	 * get item text slide in animation
	 */
	public ObjectAnimator getItemImageTextBtnSlideInAnimation(ImageView imageView, float y) {
		return getItemImageBtnSlideInAnimation(imageView, y);
	}

	/**
	 * set root image slide out animation
	 */
	public void setRootImageSlideOutAnimation(ImageView rootImageView) {
		PropertyValuesHolder holderR = PropertyValuesHolder.ofFloat("rotation", ROTATION, 0f);
		rootImageSlideOutAnimator = ObjectAnimator.ofPropertyValuesHolder(rootImageView, holderR);
		rootImageSlideOutAnimator.setDuration(ROOT_SLIDE_OUT_DURATION);
		rootImageSlideOutAnimator.setInterpolator(new OvershootInterpolator());
	}

	/**
	 * get item button slide out animation
	 */
	public ObjectAnimator getItemImageBtnSlideOutAnimation(ImageView imageView, float y) {
		PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", y, 0f);
		final ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(imageView, holderY);
		animator.setDuration(ITEM_SLIDE_OUT_DURATION);
		animator.setInterpolator(new LinearInterpolator());

		return animator;
	}

	/**
	 * get item text slide out animation
	 */
	public ObjectAnimator getItemImageTextSlideOutAnimation(ImageView imageView, float y) {
		return getItemImageBtnSlideOutAnimation(imageView, y);
	}

	/**
	 * hide item text
	 */
	public void hideItemImageText() {
		
		for (ImageIntentBean imageIntentBean : itemTextImageList) {
			ImageView imageView = imageIntentBean.imageView;
			imageView.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * show item text
	 */
	public void showItemImageText() {
		for (ImageIntentBean imageIntentBean : itemTextImageList) {
			ImageView imageView = imageIntentBean.imageView;
			imageView.setVisibility(View.VISIBLE);
		}
	}
	
	public List<ImageIntentBean> getItemBtnImageList() {
		return itemBtnImageList;
	}

	public void setItemBtnImageList(List<ImageIntentBean> itemBtnImageList) {
		this.itemBtnImageList = itemBtnImageList;
	}

	public List<ImageIntentBean> getItemTextImageList() {
		return itemTextImageList;
	}

	public void setItemTextImageList(List<ImageIntentBean> itemTextImageList) {
		this.itemTextImageList = itemTextImageList;
	}


}
