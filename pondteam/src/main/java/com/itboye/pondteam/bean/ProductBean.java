package com.itboye.pondteam.bean;

import java.io.Serializable;
import java.util.List;

public class ProductBean {
	
	List<HomeListBean> list;
	private int count;
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<HomeListBean> getList() {
		return list;
	}

	public void setList(List<HomeListBean> list) {
		this.list = list;
	}

	public static class HomeListBean implements Serializable{

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getIcon_url() {
			return icon_url;
		}

		public void setIcon_url(String icon_url) {
			this.icon_url = icon_url;
		}

		private int id;
		private long post_date;
		private String post_title;
		private String main_img;

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		private int level;

		public long getPost_date() {
			return post_date;
		}

		public void setPost_date(long post_date) {
			this.post_date = post_date;
		}

		public String getPost_title() {
			return post_title;
		}

		public void setPost_title(String post_title) {
			this.post_title = post_title;
		}

		public String getMain_img() {
			return main_img;
		}

		public void setMain_img(String main_img) {
			this.main_img = main_img;
		}

		private String name;
		private String icon_url;

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		private String notes;

	}
}
