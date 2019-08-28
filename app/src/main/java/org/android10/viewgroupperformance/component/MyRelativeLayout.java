/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package org.android10.viewgroupperformance.component;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import org.android10.gintonic.annotation.DebugTrace;

/**
 *
 */
public class MyRelativeLayout extends RelativeLayout {
  @DebugTrace
  public MyRelativeLayout(Context context) {
    super(context);
  }

  @DebugTrace
  public MyRelativeLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @DebugTrace
  public MyRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @DebugTrace
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  @DebugTrace
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
  }

  @DebugTrace
  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
  }
}
