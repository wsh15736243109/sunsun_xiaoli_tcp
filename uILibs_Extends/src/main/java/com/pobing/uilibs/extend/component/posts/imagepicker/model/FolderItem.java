package com.pobing.uilibs.extend.component.posts.imagepicker.model;


public class FolderItem {

	private String name;
	private String icon;
	private String path;
	private int itemCount;
	
	public FolderItem(String name, String icon, String path) {
		this.name = name;
		this.icon = icon;
		this.path = path;
		itemCount = 1;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void addItem(){
		itemCount++;
	}
	public int getItemCount(){
		return itemCount;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
