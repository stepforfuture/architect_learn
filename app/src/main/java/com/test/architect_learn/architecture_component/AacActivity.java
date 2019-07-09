package com.test.architect_learn.architecture_component;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.architect_learn.R;


/**
 * Description: AAC 是 Android Architechture component的简称 官方提供的一套 构建稳定容易测试的 架构方案(ACC的学习)
 * <br>Author：tian
 * <br>Time: 2019/6/14 14:55
 */
public class AacActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac);
    }
}
