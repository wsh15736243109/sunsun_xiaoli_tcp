package com.pobing.uilibs.extend.component.search;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pobing.uilibs.extend.R;


public class XSearchView extends LinearLayout{

	
	private EditText mEditText;
    private Button   mSearchButton;
    private ImageButton mEditDelButton;
    private OnSearchListener mOnSearchListener;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public XSearchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		initAttr(context, attrs, defStyle);
	}

	public XSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		initAttr(context, attrs, 0);
	}

	public XSearchView(Context context) {
		super(context);
		init();
	}
	
	private void initAttr(Context context, AttributeSet attrs, int defStyle) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.XSearchView, defStyle, 0);
		if (a != null) {
		    boolean showSearchButton  = a.getBoolean(R.styleable.XSearchView_uik_showSearchButton, true);
		    boolean editable =  a.getBoolean(R.styleable.XSearchView_uik_editable, true);
		    String text = a.getString(R.styleable.XSearchView_uik_text);
		    String hit = a.getString(R.styleable.XSearchView_uik_hit);
		    setShowSearchButton(showSearchButton);
		    mEditText.setEnabled(editable);
		    if (text != null) {
				 mEditText.setText(text);
			}
		    if (hit != null) {
				mEditText.setHint(hit);
			}
		    
			a.recycle();
		}
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.uilibs_search_view, this);
	    mEditText = (EditText) this.findViewById(R.id.uikit_searchedit);
	    mSearchButton = (Button)this.findViewById(R.id.uikit_searchbtn);
	    mEditDelButton = (ImageButton) this.findViewById(R.id.uikit_edit_del_btn);
	    mEditText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
				    doSearchAction();
		            return true;
		        }
				return false;
			}
		});
	    
	    mEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!mEditText.isEnabled()) {
					return;
				}
                String keyword = mEditText.getText().toString();
                if (keyword.length() > 0) {
                	mEditDelButton.setVisibility(View.VISIBLE);
                } else {
                	mEditDelButton.setVisibility(View.GONE);
                }
                if (mOnSearchListener != null) {
					mOnSearchListener.onTextChanged(keyword);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
	    
	    mSearchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doSearchAction();
			}
		});
	    
	    mEditDelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEditText.setText("");
			}
		});
	}
	
	 private void doSearchAction(){
		 String keyword = mEditText.getText().toString().trim();
		 if (TextUtils.isEmpty(keyword)) {
			 Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.uikit_shake);
             mEditText.startAnimation(shake);
             return;
         }
         InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
         imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
		 if (mOnSearchListener != null) {
			 mOnSearchListener.onSearch(keyword);
		 }
	 }
	 
	 public void setSearchButtonText(String searchButtonText){
		     mSearchButton.setText(searchButtonText);
	 }
	 
	 public void setShowSearchButton(boolean show){
	    if (show) {
			findViewById(R.id.uikit_searchbtn).setVisibility(View.VISIBLE);
		}else {
			findViewById(R.id.uikit_searchbtn).setVisibility(View.GONE);
		}
     }
	 
	 
	 public void setEditText(String editText){
	        mEditText.setText(editText);
     }
	 
	 public EditText getEditText(){
	      return  mEditText;
     }
	 
	 public void setEditTextHint(String editTextHint){
		     mEditText.setHint(editTextHint);
	 }
	 
	 
	 public void setOnSearchListener(OnSearchListener onSearchListener){
		   this.mOnSearchListener = onSearchListener ;
	 }

	
	 public static interface  OnSearchListener{
		 public void onSearch(String keyword);
		 
		 public void onTextChanged(String keyword);
	 }
}
