package com.android.sjsofteducationapp.extras;

import android.content.Context;
import android.util.AttributeSet;

import com.meg7.widget.SvgImageView;

public class SquareSvgImageView extends SvgImageView {

	public SquareSvgImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SquareSvgImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareSvgImageView(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		setMeasuredDimension(height, height);
	}

	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw,
			final int oldh) {
		super.onSizeChanged(h, h, oldw, oldh);
	}
}
