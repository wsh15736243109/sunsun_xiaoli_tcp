package com.pobing.uilibs.extend.component.posts.model;

public class ImageSnapshot {

	private String thumbnails;
	private String path;
	
	
	public String getThumbnails() {
		return thumbnails;
	}
	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String toString(){
		return String.valueOf(path) + ":" + String.valueOf(thumbnails);
	}
}
