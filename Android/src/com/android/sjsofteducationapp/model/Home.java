package com.android.sjsofteducationapp.model;

import java.io.Serializable;

public class Home implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	int id;
	private String title, icon, content_image, bg_image, success;

	// subject

	public Home(int id, String title, String icon, String bg_image) {
		this.id = id;
		this.title = title;
		this.icon = icon;
		this.bg_image = bg_image;
	}

	public Home(int id, String title, String icon, String content_image, String bg_image, String success) {
		this.id = id;
		this.title = title;
		this.icon = icon;
		this.content_image = content_image;
		this.bg_image = bg_image;
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContent_image() {
		return content_image;
	}

	public void setContent_image(String content_image) {
		this.content_image = content_image;
	}

	public String getBg_image() {
		return bg_image;
	}

	public void setBg_image(String bg_image) {
		this.bg_image = bg_image;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

}
