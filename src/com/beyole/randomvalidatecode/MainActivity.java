package com.beyole.randomvalidatecode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beyole.view.RandomValidateCode;
import com.beyole.view.RandomValidateCode.ValidateCodeOnClickListener;

public class MainActivity extends Activity {

	private RandomValidateCode mValidateCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mValidateCode = (RandomValidateCode) findViewById(R.id.id_validate_code);
	}

	@Override
	protected void onResume() {
		super.onResume();
		initEvent();
	}

	private void initEvent() {
		mValidateCode.setValidateCodeOnClickListener(new ValidateCodeOnClickListener() {

			@Override
			public void onClick(View view) {
				mValidateCode.setText(randomText());
				mValidateCode.postInvalidate();
				Toast.makeText(MainActivity.this, "点击事件", Toast.LENGTH_LONG).show();
			}
		});
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
