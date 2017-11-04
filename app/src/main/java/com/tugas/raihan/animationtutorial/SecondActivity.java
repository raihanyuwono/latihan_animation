package com.tugas.raihan.animationtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeScroll;
import android.transition.Explode;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private FrameLayout sceneHolder;
    private Scene sceneAll;
    private Scene sceneFirst;
    private Scene sceneSecond;

    private TextView welcome;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initItems();
        loadItems();

    }

    private void loadItems() {
        welcome.setText("Welcome " + getIntent().getStringExtra("username"));
        profilePicture.setImageResource(getIntent().getIntExtra("profile_picture", R.drawable.empty));
    }

    private void initItems() {
        sceneHolder = findViewById(R.id.scene_holder);
        welcome = findViewById(R.id.welcome);
        profilePicture = findViewById(R.id.profile_picture);
        sceneAll = Scene.getSceneForLayout(sceneHolder, R.layout.all_scene, this);
        sceneFirst = Scene.getSceneForLayout(sceneHolder, R.layout.first_scene, this);
        sceneSecond = Scene.getSceneForLayout(sceneHolder, R.layout.second_scene, this);
    }

    public void showScene(View v) {
        Scene sceneTranstion = null;
        switch (v.getId()) {
            case R.id.btn_all:
                sceneTranstion = sceneAll;
                break;
            case R.id.btn_office:
                sceneTranstion = sceneFirst;
                break;
            case R.id.btn_gym:
                sceneTranstion = sceneSecond;
                break;
        }
        TransitionManager.go(sceneTranstion, new ChangeBounds());
    }

}
