package com.android.sjsofteducationapp.model;

public class Home {
	int id, url_image;
	private String title;

	// subject
	private boolean success;

	public Home(int id, String title, int url_image) {
		this.id = id;
		this.title = title;
		this.url_image = url_image;
	}

	public Home(int id, String title, int url_image, boolean success) {
		this.id = id;
		this.title = title;
		this.url_image = url_image;
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUrl_image() {
		return url_image;
	}

	public void setUrl_image(int url_image) {
		this.url_image = url_image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
