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
import android.view.View.OnClickListener;

/**
 * �����֤��view
 * 
 * @date 2016/01/17
 * @author Iceberg
 * 
 */
public class RandomValidateCode extends View {

	// �ı�
	private String mTitleText = "1234";
	// �ı���ɫ
	private int mTitleTextColor = Color.RED;
	// �ı���С
	private int mTitleTextSize = 16;
	// ����ʱ�����ı����Ʒ�Χ
	private Rect mBound;
	// ����
	private Paint mPaint;

	// ����¼�������
	private ValidateCodeOnClickListener mOnClickListener;

	public RandomValidateCode(Context context) {
		this(context, null);
	}

	public RandomValidateCode(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// ��ȡ�Զ��������ֵ
	public RandomValidateCode(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ��ȡָ�����Զ�����ʽ����
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RandomValidateCode, defStyle, 0);
		// ��ȡ�Զ������Ը���
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			// ��ȡ�Զ����ı�
			case R.styleable.RandomValidateCode_titleText:
				mTitleText = a.getString(attr);
				break;
			// ��ȡ�Զ�����ɫ
			case R.styleable.RandomValidateCode_titleColor:
				mTitleTextColor = a.getColor(attr, Color.BLACK);
				break;
			// ��ȡ�Զ����ı���С
			case R.styleable.RandomValidateCode_titleTextSize:
				// ����Ĭ��Ϊ16sp,TypeValue���Խ�spת��Ϊpx
				mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
				break;
			}
		}
		// ������Դ
		a.recycle();
		// ��ʼ������
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		mBound = new Rect();
		// �����������ھ��Σ���ֵ��mBound��
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
		// ���õ���¼�
		
			this.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mOnClickListener != null) {
						mOnClickListener.onClick(v);
					}
				}
			});
	}

	/**
	 * ����view�Ĵ�С
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// ��ȡ���ģʽ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		// ��ȡ�߶�ģʽ
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		// �߶�
		int height;
		// ���
		int width;
		// ���ģʽΪ׼ȷֵ��match_parentʱ
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			// ������view���ʱ�����textֵ���ȷ����仯ʱ��Ҫ���²���
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float textWidth = mBound.width();
			int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
			width = desired;
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			// ������view���ʱ�����textֵ���ȷ����仯ʱ��Ҫ���²���
			mPaint.setTextSize(mTitleTextSize);
			mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
			float textHeight = mBound.height();
			int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
			height = desired;
		}
		setMeasuredDimension(width, height);
	}

	/**
	 * ����view
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		mPaint.setColor(Color.YELLOW);
		// ���Ʊ���
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		mPaint.setColor(mTitleTextColor);
		// �����ַ����
		mPaint.setAntiAlias(true);
		// ��������
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}

	/**
	 * ��ȡ��ǰ��֤��
	 * 
	 * @return
	 */
	public String getCurrentValidateCode() {
		return this.mTitleText;
	}

	public interface ValidateCodeOnClickListener {
		public void onClick(View view);
	}

	/**
	 * ���õ����¼�������
	 * 
	 * @param listener
	 */
	public void setValidateCodeOnClickListener(ValidateCodeOnClickListener listener) {
		this.mOnClickListener = listener;
	}

	/**
	 * ������֤��view����
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.mTitleText = text;
	}

	/**
	 * ������֤��������ɫ
	 * 
	 * @param color
	 */
	public void setTextColor(int color) {
		this.mTitleTextColor = color;
	}

	/**
	 * ������֤�����ִ�С
	 * 
	 * @param textSize
	 */
	public void setTextSize(int textSize) {
		this.mTitleTextSize = textSize;
	}

}
