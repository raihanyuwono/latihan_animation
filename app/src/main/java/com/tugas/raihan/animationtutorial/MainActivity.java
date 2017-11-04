package com.tugas.raihan.animationtutorial;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private View container;
    private View welcome;
    private View profilePicture;
    private View signIn;

    private EditText username;

    private int imageProfilePicture;
    private boolean playAnimations = true;
    private String[] arrayUsers = {
            "fox", "frog", "hedgehog", "octopus", "owl", "penguin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initItems();
        initListener();
    }

    private void initItems() {
        container = findViewById(R.id.container);
        welcome = findViewById(R.id.welcome);
        profilePicture = findViewById(R.id.profile_picture);
        signIn = findViewById(R.id.sign_in);
        username = findViewById(R.id.username);
    }

    private void initListener() {
        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (Arrays.asList(arrayUsers).contains(username.getText().toString()))
                        changeProfilePicture();
                    else {
                        setDefaultProfilePicture();
                    }
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("username", username.getText().toString());
                intent.putExtra("profile_picture", imageProfilePicture);
                Log.d("imageProfilePicture", String.valueOf(imageProfilePicture));
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this,
                        new Pair<View, String>(profilePicture, "profile_picture"),
                        new Pair<View, String>(welcome, "welcome"));
                startActivity(intent, options.toBundle());
            }
        });
    }

    private void setDefaultProfilePicture() {
        profilePicture.animate().rotationY(-90).setDuration(750)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ((ImageView) profilePicture).setImageResource(R.drawable.empty);
                        profilePicture.animate().rotationY(0).setDuration(750);
                    }
                });
        ((ImageView) profilePicture).setImageResource(R.drawable.empty);
    }

    private void changeProfilePicture() {
        profilePicture.animate().rotationY(90).setDuration(750)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ((ImageView) profilePicture).setImageResource(getImageResource(username.getText().toString()));
                        profilePicture.animate().rotationY(0).setDuration(750).setInterpolator(new OvershootInterpolator());
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && playAnimations) {
            showContainer();
            playAnimations = false;
        }
    }

    private void showContainer() {
        container.animate().alpha(1f).setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        showOtherItems();
                    }
                });
    }

    private int getImageResource(String user) {
        int imageResource = R.drawable.empty;
        user = user.toLowerCase();
        switch (user) {
            case "fox":
                imageResource = R.drawable.ic_fox;
                break;
            case "frog":
                imageResource = R.drawable.ic_frog;
                break;
            case "hedgehog":
                imageResource = R.drawable.ic_hedgehog;
                break;
            case "octopus":
                imageResource = R.drawable.ic_octopus;
                break;
            case "owl":
                imageResource = R.drawable.ic_owl;
                break;
            case "penguin":
                imageResource = R.drawable.ic_penguin;
                break;
        }

        return imageProfilePicture = imageResource;
    }

    private void showOtherItems() {
        ObjectAnimator animWelcome = animateWelcome();
        ObjectAnimator animProfilePicture = animateProfilePicture();
        ObjectAnimator animSignIn = animateSignIn();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animWelcome).after(animProfilePicture);    //animProfilePicture -> animWelcome
        animatorSet.play(animWelcome).before(animSignIn);           //animWelcome -> animSignIn

        animatorSet.start();
    }

    private ObjectAnimator animateWelcome() {
        float startX = -welcome.getWidth();
        float endX = welcome.getX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(welcome, View.X, startX, endX);
        animator.setDuration(1500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                welcome.setVisibility(View.VISIBLE);
            }
        });

        return animator;
    }

    private ObjectAnimator animateProfilePicture() {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(profilePicture, scaleX, scaleY);
        animator.setDuration(1500);

        return animator;
    }

    private ObjectAnimator animateSignIn() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.animation_sign_in);
        animator.setTarget(signIn);

        return animator;
    }

}
