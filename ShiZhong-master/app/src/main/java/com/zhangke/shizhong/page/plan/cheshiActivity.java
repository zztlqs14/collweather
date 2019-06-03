package com.zhangke.shizhong.page.plan;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangke.shizhong.R;
import com.zhangke.shizhong.event.ThemeChangedEvent;
import com.zhangke.shizhong.page.base.BaseActivity;
import com.zhangke.shizhong.util.ThemeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class cheshiActivity extends BaseActivity implements SensorEventListener {

    private TextView tv1,tv2,tv3;
    private ImageView img;
    private SensorManager sensorManager;
    private Sensor sensor;
    private float startAngle = 0;
    private DefinedRotateAnimation definedRotateAnimation;
    private Toolbar toolbar;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cheshi;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        init();
        initToolbar(toolbar, "返回", true);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void init(){

        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        img = (ImageView)findViewById(R.id.img);
        toolbar = findViewById(R.id.toolbar);
        //获取传感器管理者，通过系统获取
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //获取方向传感器
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        definedRotateAnimation = new DefinedRotateAnimation();
        definedRotateAnimation.setDuration(100);
        definedRotateAnimation.setFillAfter(true);

        tv1.bringToFront();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float presentAngle = event.values[0];
        if(startAngle == (-presentAngle)){
            return ;
        }
        if((presentAngle>=340&&presentAngle<=360)||(presentAngle>=0&&presentAngle<=20)){
            tv3.setText("北");
        }else if(presentAngle>20&&presentAngle<70){
            tv3.setText("东北");
        }else if(presentAngle>=70&&presentAngle<=110){
            tv3.setText("东");
        }else if(presentAngle>110&&presentAngle<160){
            tv3.setText("东南");
        }else if(presentAngle>=160&&presentAngle<=200){
            tv3.setText("南");
        }else if(presentAngle>200&presentAngle<250){
            tv3.setText("西南");
        }else if(presentAngle>=250&&presentAngle<=290){
            tv3.setText("西");
        }else {
            tv3.setText("西北");
        }
        tv2.setText((int)presentAngle + "°");
        presentAngle = (-presentAngle);
        definedRotateAnimation.setStartAngle(startAngle);
        definedRotateAnimation.setEndAngle(presentAngle);
        img.startAnimation(definedRotateAnimation);
        startAngle = presentAngle;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
