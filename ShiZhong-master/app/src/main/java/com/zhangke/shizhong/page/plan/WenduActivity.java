package com.zhangke.shizhong.page.plan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitmapUtils;
import com.zhangke.shizhong.R;
import com.zhangke.shizhong.page.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public class WenduActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtn1;
    private EditText mEt;
    private Button mBtn2;
    private ImageView mImage;
    private final static int REQ_CODE = 1028;
    private Context mContext;
    private TextView mTvResult;
    private ImageView mImageCallback;
    private ImageView imageView;
    private Toolbar tober;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_wendu;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        initView();
        initToolbar(tober, "返回", true);
        mContext = this;

    }

    private void initView() {
        tober = findViewById(R.id.toolbar);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mEt = (EditText) findViewById(R.id.et);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setOnClickListener(this);
        /*imageView = (ImageView) findViewById(R.id.erweima_fanghui_img_button);
        imageView.setOnClickListener(this);*/
        mTvResult = (TextView) findViewById(R.id.tv_result);
        mTvResult.setOnClickListener(this);
        mImageCallback = (ImageView) findViewById(R.id.image_callback);
        mImageCallback.setOnClickListener(this);

    }


     @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                }else{
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, REQ_CODE);
                }


                break;
            case R.id.btn2:
                mImage.setVisibility(View.VISIBLE);
                //隐藏扫码结果view
                mImageCallback.setVisibility(View.GONE);
                mTvResult.setVisibility(View.GONE);

                String content = mEt.getText().toString().trim();
                if(mEt.getText().toString().trim().length()==0||mEt.getText().toString().trim().equals("")){
                    Toast.makeText(mContext, "请输入内容生成二维码", Toast.LENGTH_SHORT).show();

                }else{
                    try {
                        Bitmap bitmap = BitmapUtils.create2DCode(content);//根据内容生成二维码
                        mTvResult.setVisibility(    View.GONE);
                        mImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            mImage.setVisibility(View.GONE);
            mTvResult.setVisibility(View.VISIBLE);
            mImageCallback.setVisibility(View.VISIBLE);

            String result = data.getStringExtra(CaptureActivity.SCAN_QRCODE_RESULT);
            Bitmap bitmap = data.getParcelableExtra(CaptureActivity.SCAN_QRCODE_BITMAP);

            mTvResult.setText("扫码结果：\n"+result);
            showToast("扫码结果：" + result);
            if(bitmap != null){
                mImageCallback.setImageBitmap(bitmap);//现实扫码图片
            }
        }


    }

    private void showToast(String msg) {
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
    }
}
