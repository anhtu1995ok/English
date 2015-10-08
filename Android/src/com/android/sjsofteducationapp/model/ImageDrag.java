package com.android.sjsofteducationapp.model;

import java.io.File;

import android.widget.ImageView;

public class ImageDrag {
	private ImageView imageView;
	private ImageView dragImageView;
	private File file;
	private int idDrawable;
	private int idDrawableDrag;
	private int position;
	private boolean drag;

	public ImageDrag(ImageView imageView, ImageView dragImageView, File file,
			int idDrawableDrag, int position) {
		this.imageView = imageView;
		this.dragImageView = dragImageView;
		this.file = file;
		this.idDrawableDrag = idDrawableDrag;
		this.position = position;
		this.drag = false;
	}

	public ImageDrag(ImageView imageView, ImageView dragImageView,
			int idDrawable, int idDrawableDrag, int position) {
		this.imageView = imageView;
		this.dragImageView = dragImageView;
		this.idDrawable = idDrawable;
		this.idDrawableDrag = idDrawableDrag;
		this.position = position;
		this.drag = false;
	}

	public ImageView getImageView() {
		return imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public ImageView getDragImageView() {
		return dragImageView;
	}

	public void setDragImageView(ImageView dragImageView) {
		this.dragImageView = dragImageView;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getIdDrawableDrag() {
		return idDrawableDrag;
	}

	public void setIdDrawableDrag(int idDrawableDrag) {
		this.idDrawableDrag = idDrawableDrag;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public int getIdDrawable() {
		return idDrawable;
	}

	public void setIdDrawable(int idDrawable) {
		this.idDrawable = idDrawable;
	}

	
}
