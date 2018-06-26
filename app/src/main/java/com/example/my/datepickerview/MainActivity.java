package com.example.my.datepickerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TimePickerView mDatePickerView;
    private TimePickerView mTimePickerView;

    private FrameLayout mFrameLayout1;
    private FrameLayout mFrameLayout2;

    private Date mDate;
    private Date mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrameLayout1 = findViewById(R.id.fl_data);
        mFrameLayout2 = findViewById(R.id.fl_time);

        initDatePickerView();
        mDatePickerView.show();

        initTimePickerView();
        mTimePickerView.show();

        Button btn = findViewById(R.id.btn_confirm);
        btn.setOnClickListener(this);
    }

    private void initDatePickerView() {

        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1995, 0, 1);

        //时间选择器
        mDatePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                mDate = date;
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})// 列数
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY) // 分割线颜色
                .setContentTextSize(20)
                .setDate(selectedDate) // 当前时间（默认是系统时间）
                .setRangDate(startDate, selectedDate)// 起始终止年月日设定
                .setDecorView(mFrameLayout1) // 填充的控件
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false) //点击屏幕，点在控件外部范围时，是否取消显示
                .build();
    }


    private void initTimePickerView() {

        mTimePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTime = date;
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                    }
                })
                .setType(new boolean[]{false, false, false, true, true, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDecorView(mFrameLayout2)
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();
    }

    @Override
    public void onClick(View v) {
        // 提取 PickerView 的数据
        mDatePickerView.returnData();
        mTimePickerView.returnData();

        if (mDate != null && mTime != null) {
            String content = getDate(mDate) + " " + getTime(mTime);
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }
}
