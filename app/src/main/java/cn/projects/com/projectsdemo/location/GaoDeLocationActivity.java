package cn.projects.com.projectsdemo.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClientOption;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.projects.com.projectsdemo.R;


/**
 * Created by fengxing on 2017/5/10.
 */

public class GaoDeLocationActivity extends AppCompatActivity {


    private static final String TAG = GaoDeLocationActivity.class.getSimpleName();
    @Bind(R.id.id_rg_location_mode)
    RadioGroup mRadioGroup;

    @Bind(R.id.id_btn_location_is_started)
    Button mIsStarted;

    @Bind(R.id.id_btn_location_start_location)
    Button mStartLocation;

    @Bind(R.id.id_btn_location_stop_location)
    Button mStopLocation;

    @Bind(R.id.id_et_location_is_start_result)
    TextView mIsStartResult;

    @Bind(R.id.id_et_location_result)
    TextView mLocationResult;


    private LocationHelp mLocationHelp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaode_location);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: "+mLocationHelp);
        mLocationHelp = LocationHelp.getInstance(this);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.id_rb_location_heightMode:
                        mLocationHelp.setModeAndLocation(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                        break;
                    case R.id.id_rb_location_devices:
                        mLocationHelp.setModeAndLocation(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
                        break;
                }
            }
        });

        mLocationHelp.setLocationResult(new LocationHelp.LocationResult() {
            @Override
            public void onResult(String result) {
                mLocationResult.setText(result);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }



    @OnClick(R.id.id_btn_location_start_location)
    public void onStartLocation() {
        Log.d(TAG, "onStartLocation: "+mLocationHelp);
        mLocationHelp.startLocation();
    }

    @OnClick(R.id.id_btn_location_stop_location)
    public void onStopLocation() {
        Log.d(TAG, "onStopLocation: "+mLocationHelp);
        mLocationHelp.stopLocation();
    }

    @OnClick(R.id.id_btn_location_is_started)
    public void isStartLocation() {
        boolean started = mLocationHelp.isStarted();
        mIsStartResult.setText("是否正在定位：" + started);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationHelp.destroyLocation();
    }
}
