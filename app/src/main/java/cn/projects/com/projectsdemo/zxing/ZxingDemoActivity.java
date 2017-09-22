package cn.projects.com.projectsdemo.zxing;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 2017/9/21.
 */

public class ZxingDemoActivity extends AppCompatActivity {

    private static final String TAG = ZxingDemoActivity.class.getSimpleName();
    private ImageView iv_show;
    private TextView tv_path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_demo);

        Button startZxing = (Button) findViewById(R.id.zxing_demo_btn);
        iv_show = (ImageView) findViewById(R.id.zxing_demo_show_image);
        tv_path = (TextView) findViewById(R.id.zxing_demo_path);

        startZxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(ZxingDemoActivity.this);
                intentIntegrator.setCaptureActivity(CustomCaptureActivity.class);
                //设置可以保存条形码（二维码）图片
                intentIntegrator.setBarcodeImageEnabled(true);
                intentIntegrator.setPrompt("立正！站好！我要开始扫描了");
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
            String contents = result.getContents();
            if (contents == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();
                String barcodeImagePath = result.getBarcodeImagePath();
                Log.d(TAG, "显示条形码（二维码）图片的保存路径 " + barcodeImagePath);
                if (contents.startsWith("http") || contents.startsWith("https")) {
                    turn2WebClient(ZxingDemoActivity.this, contents);
                }
                showImage(result.getBarcodeImagePath());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 根据路径显示图片
     *
     * @param barcodeImagePath
     */
    private void showImage(String barcodeImagePath) {
        FileInputStream fileInputStream = null;
        tv_path.setText(barcodeImagePath);
        try {
            if (barcodeImagePath != null) {
                fileInputStream = new FileInputStream(new File(barcodeImagePath));
                iv_show.setImageBitmap(BitmapFactory.decodeStream(fileInputStream));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void turn2WebClient(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
