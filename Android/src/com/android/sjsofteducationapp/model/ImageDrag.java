package com.android.sjsofteducationapp.model;

import android.graphics.Bitmap;

import com.meg7.widget.SvgImageView;

public class ImageDrag {
	private SvgImageView imageView;
	private SvgImageView dragImageView;
	private Bitmap bitmap;
	private int idRawDrag;
	private int position;
	private boolean drag;
	public ImageDrag(SvgImageView imageView, SvgImageView dragImageView, Bitmap bitmap,
			int idRawDrag, int position) {
		this.imageView = imageView;
		this.dragImageView = dragImageView;
		this.bitmap = bitmap;
		this.idRawDrag = idRawDrag;
		this.position = position;
		this.drag = false;
	}
	public SvgImageView getImageView() {
		return imageView;
	}
	public void setImageView(SvgImageView imageView) {
		this.imageView = imageView;
	}
	public SvgImageView getDragImageView() {
		return dragImageView;
	}
	public void setDragImageView(SvgImageView dragImageView) {
		this.dragImageView = dragImageView;
	}
	public int getIdRawDrag() {
		return idRawDrag;
	}
	public void setIdRawDrag(int idRawDrag) {
		this.idRawDrag = idRawDrag;
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
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
}
