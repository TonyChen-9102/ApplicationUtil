package com.demo.ck.applicationUtil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.ck.applicationUtil.util.ApplicationInfoUtil;
import com.demo.ck.applicationUtil.util.PackageInfoUtil;

public class MainActivity extends AppCompatActivity {
    private ImageView ivAppIcon;
    private TextView tvAppName;
    private TextView tvVersionName;
    private TextView tvVersionCode;
    private TextView tvPackageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivAppIcon = findViewById(R.id.ivAppIcon);
        tvAppName = findViewById(R.id.tvAppName);
        tvVersionName = findViewById(R.id.tvVersionName);
        tvVersionCode = findViewById(R.id.tvVersionCode);
        tvPackageName = findViewById(R.id.tvPackageName);
        
        //chenkaidd
        initView();
    }

    private void initView() {
        ivAppIcon.setImageBitmap(ApplicationInfoUtil.getAppIcon(this));
        tvAppName.setText(ApplicationInfoUtil.getAppName(this));
        tvVersionName.setText(PackageInfoUtil.getVersionName(this));
        tvVersionCode.setText(String.valueOf(PackageInfoUtil.getVersionCode(this)));
        tvPackageName.setText(PackageInfoUtil.getPackageName(this));

    }

}
