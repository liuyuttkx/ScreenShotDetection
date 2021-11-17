package com.kx.screen;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.PermissionUtils;
import com.kx.screenshot.detection.OnScreenShotDetection;
import com.kx.screenshot.detection.OnScreenShotNotifycationListener;
import com.kx.screenshot.detection.ScreenShotDetectionManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnScreenShotDetection mDetection;
    private ImageView mShotIv;
    private TextView mHintTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShotIv = findViewById(R.id.shot_iv);
        mHintTv = findViewById(R.id.hint_tv);


        boolean granted = PermissionUtils.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE);
        //7.0及以上必须申请存储权限才可以使用，否则无法获取到截图图片的uri
        PermissionUtils.permission(Manifest.permission.READ_EXTERNAL_STORAGE).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(@NonNull List<String> granted) {
                initScreenShot();
            }

            @Override
            public void onDenied(@NonNull List<String> deniedForever, @NonNull List<String> denied) {
                Toast.makeText(MainActivity.this, "无存储权限", Toast.LENGTH_LONG).show();
            }
        }).request();
    }

    private void initScreenShot() {
        //创建OnScreenShotDetection实现类对象
        mDetection = ScreenShotDetectionManager.create(this);
        //设置屏幕截图监听
        mDetection.setScreenShotChangeListener(new OnScreenShotNotifycationListener() {
            @Override
            public void onShot(String imagePath, Uri imageUri) {
                // imagePath 不能直接使用，由于安卓10系统及以上，限制了访问SD卡，需要使用ContentResolver访问。
                // 通过imagePath获取图片的Uri可以使用
                if (mShotIv != null) {
                    mShotIv.setImageURI(imageUri);
                }
                updateHint();
                Toast.makeText(MainActivity.this, "图片路径 ：" + imagePath.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateHint() {
        String text = mHintTv.getText().toString();
        Integer integer = Integer.parseInt(text);
        integer++;
        mHintTv.setText(String.valueOf(integer));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDetection != null) {
            //开启屏幕截图监听
            mDetection.startScreenShotDetection();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDetection != null) {
            //关闭屏幕截图监听
            mDetection.stopScreenShotDetection();
        }
    }
}