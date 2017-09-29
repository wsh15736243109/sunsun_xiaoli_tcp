package com.pobing.uilibs.extend.feature.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.pobing.uilibs.extend.R;
import com.pobing.uilibs.feature.view.XTextView;

import java.text.DecimalFormat;

public class XPriceTextView extends XTextView {
	private final static int TEXT_COLOR = 0xffff5000;
	private final static float DECIMAL_TEXT_FONT_RATIO = 0.75f;
	private boolean mIsTenThousand = false;
	public XPriceTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	public XPriceTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs,0);
	}

	public XPriceTextView(Context context) {
		super(context);
		init(null,0);
	}

	private void init(AttributeSet attrs, int defStyle) {
		
		this.setTextColor(TEXT_COLOR);
		 if (null != attrs)
	        {
	            TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.XPriceTextView);
	            if (null != a)
	            {
	            	float price = a.getFloat(R.styleable.XPriceTextView_uik_price, 0.0f);
	            	this.setPrice(price);
	                a.recycle();
	            }
	        }
	}

	public void setPrice(float price) {
		String str = price + "";
		
		int index = str.indexOf('.');
		mIsTenThousand = false;
		// > 1w
		if(index > 4 ){
			str = str.substring(0,index - 4) + "." + str.substring(index - 4,index) ;		
			mIsTenThousand = true;
			price = Float.parseFloat(str);
		}
		
		
		DecimalFormat df = mIsTenThousand?new DecimalFormat("#0.0"):new DecimalFormat("#0.00");
		String format = "¥" + df.format(price);
		if (format.endsWith("00")) {
			format = format.substring(0, format.length() - 3);
			this.setText(format);
		}else if(format.endsWith("0") & mIsTenThousand){
			format = format.substring(0, format.length() - 2);
			this.setText(format + "万");
		}else {
			this.setText(mIsTenThousand?format + "万":format);
			setSpan();
		}

	}
	private void setSpan(){		
		SpannableString str = new SpannableString(this.getText());
		str.setSpan(new RelativeSizeSpan(DECIMAL_TEXT_FONT_RATIO),mIsTenThousand?str.length() - 3:str.length() - 2, mIsTenThousand?str.length()- 1:str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
		this.setText(str);
	}
	@Override
	public void setTextSize(int unit, float size) {
		super.setTextSize(unit, size);
		setSpan();
	}

}
