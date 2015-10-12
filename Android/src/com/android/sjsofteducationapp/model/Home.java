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
	int id, url_img;
	private String title, url_image, content_image, bg_image, success;

	// subject

	public Home(int id, String title, int url_img) {
		this.id = id;
		this.title = title;
		this.url_img = url_img;
	}

	public Home(int id, String title, int url_img, String success) {
		this.id = id;
		this.title = title;
		this.url_img = url_img;
		this.success = success;
	}

	public Home(int id, String title, String url_image, String content_image, String bg_image, String success) {
		this.id = id;
		this.title = title;
		this.url_image = url_image;
		this.content_image = content_image;
		this.bg_image = bg_image;
		this.success = success;
	}

	public String getContent_image() {
		return content_image;
	}

	public void setContent_image(String content_image) {
		this.content_image = content_image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUrl_img() {
		return url_img;
	}

	public void setUrl_img(int url_img) {
		this.url_img = url_img;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
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
