package com.zhangke.shizhong.page.plan;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class DefinedRotateAnimation extends Animation {
    private float startAngle = 0;
    private float endAngle = 0;
    private int width = 0;
    private int height = 0;

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float angleDifference = endAngle - startAngle;
        //interpolatedTime时间区间【0-1】,逐渐递增
        t.getMatrix().setRotate(startAngle + angleDifference * interpolatedTime,width/2,height/2);
    }

    public float getStartAngle(){
        return this.startAngle;
    }
    public void setStartAngle(float startAngle){
        this.startAngle = startAngle;
    }
    public float getEndAngle(){
        return this.endAngle;
    }
    public void setEndAngle(float endAngle){
        this.endAngle = endAngle;
    }
}
