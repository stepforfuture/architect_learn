package com.test.architect_learn.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.test.architect_learn.R;

/**
 * 动画的学习和总结
 */
public class AnimationTestActivity extends AppCompatActivity {

    private Button mShowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_test);
        initViews();

        //不能直接去获取宽高
        mShowBtn.post(new Runnable() {
            @Override
            public void run() {
//                testValueAnimator01();
                testValueAnimator02();
            }
        });
    }

    private void initViews() {
        mShowBtn = findViewById(R.id.btn_show);
    }

    /**
     * 改变 按钮宽度为 例子
     */
    private void testValueAnimator01() {
        //步骤1 ofInt()作用有2个
        //1.创建动画实例
        //2.将传入的多个int参数进行平滑过渡，如传入0，1表示值从0平滑过渡到1，如果传入3个int值a,b,c表示从a平滑过渡到b,从b平滑过渡到c
        //ValueAnimator.ofInt(..)内置了整型估值器，直接采用默认的，不需要设置
        ValueAnimator animator = ValueAnimator.ofInt(mShowBtn.getWidth(), 500);

        //步骤2 设置动画的各种属性
        animator.setDuration(500);
        animator.setStartDelay(500);//设置延迟播放时间
        animator.setRepeatCount(0); //设置重播次数
        animator.setRepeatMode(ValueAnimator.RESTART);//设置重复播放动画的模式 ValueAnimator.RESTART(默认):正序播放，ValueAnimator.REVERSE:倒序播放

        //步骤3 将改变的值赋值给对象的属性值：通过动画的监视器更新值
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                //输出改变后的值
                System.out.println(currentValue);
                //步骤4 将改变后的值赋值给对象的属性
                mShowBtn.setWidth(currentValue);
                //步骤5 刷新视图
                mShowBtn.requestLayout();
            }
        });
        animator.start();
    }


    private void testValueAnimator02() {
        ValueAnimator anim = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.width_animator);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) animation.getAnimatedValue();
                System.out.println(currentValue);
                mShowBtn.setWidth(currentValue);
                mShowBtn.requestLayout();
            }
        });
        anim.start();
    }

}
