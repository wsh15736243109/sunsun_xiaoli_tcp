package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.pobing.uilibs.feature.view.XTextView;

public class XIconFontTextView extends XTextView {

	private static Typeface sIconfont;

	private static int sReference = 0;

	public XIconFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public XIconFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XIconFontTextView(Context context) {
		super(context);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (null == sIconfont) {
			try {
				sIconfont = Typeface.createFromAsset(getContext().getAssets(),
						"iconfont.ttf");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.setTypeface(sIconfont);
		this.setIncludeFontPadding(false);
		sReference++;
	}

	@Override
	protected void onDetachedFromWindow() {
		this.setTypeface(null);
		sReference--;
		if (0 == sReference) {
			sIconfont = null;
		}
		super.onDetachedFromWindow();
	}

	@Override
	public Typeface getTypeface() {
		if (null == sIconfont) {
			try {
				sIconfont = Typeface.createFromAsset(getContext().getAssets(),
						"uilibs_iconfont.ttf");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return sIconfont;
	}

}
