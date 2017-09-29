package com.pobing.uilibs.extend.component.posts.model;

public class ImageSendItem {
	public static int TYPE_IMAGE = 0,TYPE_ADD_BTN = 1,TYPE_HINT = 2;
	
	private int type = TYPE_IMAGE;
	private String imagePath;
	private String hintText;
	
	private ImageSendItem(int type){
		this.type = type;
	}
	
	public static ImageSendItem createImage(String imagePath){
		ImageSendItem item = new ImageSendItem(TYPE_IMAGE);
		item.imagePath = imagePath;
		return item;
	}
	
	public static ImageSendItem createAddButton(){
		ImageSendItem item = new ImageSendItem(TYPE_ADD_BTN);
		return item;
	}
	
	public static ImageSendItem createHint(){
		ImageSendItem item = new ImageSendItem(TYPE_HINT);
		return item;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getHintText() {
		return hintText;
	}
	public void setHintText(String hintText) {
		this.hintText = hintText;
	}
	
	
}
