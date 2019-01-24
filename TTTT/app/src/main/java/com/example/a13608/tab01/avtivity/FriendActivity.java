package com.example.a13608.tab01.avtivity;


import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;


import com.example.a13608.tab01.R;
import com.example.a13608.tab01.View.CommentDialogFragment;

import static org.litepal.LitePalApplication.getContext;


public class FriendActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton fab;
    private Dialog dialog;
    private CommentDialogFragment commentDialogFragment;
    private ImageView iv_addphoto;
    private EditText editText;
    private Button btn_evluate;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        //************

        commentDialogFragment=new CommentDialogFragment();
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //评价 弹出输入框
                commentDialogFragment.show(getSupportFragmentManager(),"");
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_addphoto:
                Toast.makeText(getContext(),"45",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_evluate:
                Toast.makeText(getContext(),"45565",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
