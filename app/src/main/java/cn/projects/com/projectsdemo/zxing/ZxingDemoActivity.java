package cn.projects.com.projectsdemo.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 2017/9/21.
 */

public class ZxingDemoActivity extends AppCompatActivity {

    private static final String TAG = ZxingDemoActivity.class.getSimpleName();
    private ImageView iv_show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_demo);
        Log.d(TAG, "onCreate: 开始扫描了");
        Button startZxing = (Button) findViewById(R.id.zxing_demo_btn);
        iv_show = (ImageView) findViewById(R.id.zxing_demo_show_image);
        startZxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ZxingDemoActivity.this);
                //设置可以保存条形码（二维码）图片
                intentIntegrator.setBarcodeImageEnabled(true);
                // 开始扫描
                intentIntegrator.initiateScan();
            }
        });
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                String barcodeImagePath = result.getBarcodeImagePath();
                Log.d(TAG, "显示条形码（二维码）图片的保存路径 "+barcodeImagePath);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
