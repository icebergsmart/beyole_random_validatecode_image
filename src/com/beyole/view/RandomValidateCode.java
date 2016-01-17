package com.beyole.view;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.beyole.randomvalidatecode.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 随机验证码view
 * 
 * @date 2016/01/17
 * @author Iceberg
 * 
 */
public class RandomValidateCode extends View {

	// 文本
	private String mTitleText;
	// 文本颜色
	private int mTitleTextColor;
	// 文本大小
	private int mTitleTextSize;
	// 绘制时控制文本绘制范围
	private Rect mBound;
	// 画笔
	private Paint mPaint;

	public RandomValidateCode(Context context) {
		this(context, null);
	}

	public RandomValidateCode(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// 获取自定义的属性值
	public RandomValidateCode(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// 获取指定的自定义样式属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RandomValidateCode, defStyle, 0);
		// 获取自定义属性个数
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			// 获取自定义文本
			case R.styleable.RandomValidateCode_titleText:
				mTitleText = a.getString(attr);
				break;
			// 获取自定义颜色
			case R.styleable.RandomValidateCode_titleColor:
				mTitleTextColor = a.getColor(attr, Color.BLACK);
				break;
			// 获取自定义文本大小
			case R.styleable.RandomValidateCode_titleTextSize:
				// 设置默认为16sp,TypeValue可以将sp转换为px
				mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			}
		}
		// 回收资源
		a.recycle();
		// 初始化画笔
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		mBound = new Rect();
		// 计算文字所在矩形，赋值到mBound中
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
		// 设置点击事件
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mTitleText = randomText();
				// 在非主线程中重绘view
				postInvalidate();
			}
		});
	}

	/**
	 * 测量view的大小
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 获取宽度模式
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// 获取高度模式
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// 高度
		int height;
		// 宽度
		int width;
		// 宽度模式为准确值或match_parent时
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			// 当设置view点击时，如果text值长度发生变化时需要重新测量
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float textWidth = mBound.width();
			int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
			width = desired;
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			// 当设置view点击时，如果text值长度发生变化时需要重新测量
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float textHeight = mBound.height();
			int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
			height = desired;
		}
		setMeasuredDimension(width, height);
	}

	/**
	 * 绘制view
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		mPaint.setColor(Color.YELLOW);
		// 绘制背景
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(mTitleTextColor);
		// 消除字符锯齿
		mPaint.setAntiAlias(true);
		// 绘制文字
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}

	/**
	 * 生成随机四位数
	 * 
	 * @return
	 */
	private String randomText() {
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < 4) {
			int randomInt = random.nextInt(10);
			set.add(randomInt);
		}
		StringBuilder builder = new StringBuilder();
		for (Integer integer : set) {
			builder.append("" + integer);
		}
		return builder.toString();
	}

}
