package com.illicitintelligence.finalproject.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.illicitintelligence.finalproject.R;
import com.illicitintelligence.finalproject.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.octocat_imageview)
    ImageView octocatImg;

    @BindView(R.id.leaf_stand_imageview)
    ImageView leafyStand;

    @BindView(R.id.get_started_button)
    ImageView startButton;

    @BindView(R.id.logo_textview)
    TextView logoTextView;

    @BindView(R.id.logo_textview2)
    TextView logoTextView2;

    Animation fromBottom, fromRight, bouncer, darkoFadeIn;

    ObjectAnimator scaleLeafDownX, scaleLeafDownY, scaleTextDownX, scaleTextDownY;
    AnimatorSet scale;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.octocat_splash_screen);
        ButterKnife.bind(this);

        setUpAnimations();
        startAnimations();
    }

    @OnClick(R.id.get_started_button)
    public void startMainActivity(){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void startAnimations() {
        scale.play(scaleTextDownX).with(scaleTextDownY);
        scale.play(scaleLeafDownX).with(scaleLeafDownY);
        scale.start();
    }

    private void setUpAnimations() {

        // instantiate animatorSet
        scale = new AnimatorSet();

        // load in animations
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        //fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        bouncer = AnimationUtils.loadAnimation(this, R.anim.bouncer);
        darkoFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in_darkat);

        // Bouncing animation
        bouncer.setDuration(4000);
        octocatImg.setAnimation(bouncer);
        logoTextView.setAnimation(bouncer);

        // Sliding animation
        startButton.setAnimation(fromBottom);

        // Scaling animations
        scaleLeafDownX = ObjectAnimator.ofFloat(leafyStand,
                "scaleX",
                0.8f)
                .setDuration(Constants.SCALE_ANIMATION_DURATION);
        scaleLeafDownY = ObjectAnimator.ofFloat(leafyStand,
                "scaleY",
                0.8f)
                .setDuration(Constants.SCALE_ANIMATION_DURATION);

        scaleTextDownX = ObjectAnimator.ofFloat(logoTextView2,
                "scaleX",
                1.4f)
                .setDuration(Constants.SCALE_ANIMATION_DURATION);
        scaleTextDownY = ObjectAnimator.ofFloat(logoTextView2,
                "scaleY",
                1.4f)
                .setDuration(Constants.SCALE_ANIMATION_DURATION);
    }
}
