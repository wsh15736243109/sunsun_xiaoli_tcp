package com.pobing.uilibs.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.pobing.uilibs.feature.features.AbsFeature;

public interface IFeatureList<T extends View> {

	boolean addFeature(AbsFeature<? super T> feature);

	AbsFeature<? super T> findFeature(Class<? extends AbsFeature<? super T>> clazz);

	boolean removeFeature(Class<? extends AbsFeature<? super T>> clazz);

	void clearFeatures();

	void init(Context context, AttributeSet attrs, int defStyle);
}
