package com.pobing.uilibs.feature.view;

import android.graphics.Canvas;
import android.view.View;

public interface ViewGroupHelper {
	public void measureChild(View child, int parentWidthMeasureSpec,
			int parentHeightMeasureSpec, int reserve);

	public boolean drawChild(Canvas canvas, View child, long drawingTime,
			int reserve);
}
