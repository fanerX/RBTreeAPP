package com.example.rbtreeapp;

import androidx.appcompat.app.AppCompatActivity;
import myClass.RBTree;
import myView.RBTreeView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class MainActivity extends AppCompatActivity {
    private RBTreeView rbTreeView;
    private RBTree rbTree;

    private Button add_button,delete_button,determine_button,button_random,delete_all_button,determine_cancel;
    private LinearLayout linearLayout;
    private TextView textView;
    private EditText editText;
    private HorizontalScrollView hor_scrollview;

    int[] data = new int[]{
            1,8,6,9,4,5,4,12,56,99,20,14,33,3
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hor_scrollview=findViewById(R.id.hor_scrollview);
        rbTree=new RBTree();
        init();
        rbTreeView=findViewById(R.id.rb_tree);
        rbTreeView.setInitData(rbTree.getHeight(),rbTree.getPosition(),rbTree.getSideList());

        add_button=findViewById(R.id.add_button);
        delete_button=findViewById(R.id.delete_button);
        determine_button=findViewById(R.id.determine_button);
        linearLayout=findViewById(R.id.linear_layout);
        textView=findViewById(R.id.flag_text);
        editText=findViewById(R.id.edit_text);
        button_random=findViewById(R.id.button_random);
        delete_all_button=findViewById(R.id.delete_all_button);
        determine_cancel=findViewById(R.id.determine_cancel);
        init_view();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int w=hor_scrollview.getHeight();
                        hor_scrollview.scrollTo(w*4,w);
                    }
                });
            }
        }).start();
    }

    private void init() {
        for(int i=0;i<data.length;i++){
            rbTree.insert(data[i]);
            Log.d("Test", "init: "+data[i]);
        }
    }



    private void init_view(){
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility()!=View.VISIBLE){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                textView.setText("添加");
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linearLayout.getVisibility()!=View.VISIBLE){
                    linearLayout.setVisibility(View.VISIBLE);
                }
                textView.setText("删除");
            }
        });
        determine_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w=hor_scrollview.getHeight();
                hor_scrollview.scrollTo(w*4,w);
                String string =editText.getText().toString();
                editText.setText("");
                if(string.equals("")){
                    Toast.makeText(MainActivity.this,"输入不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                int data=0;
                try {
                    data= Integer.parseInt(string);
                }catch (NumberFormatException e){
                    Toast.makeText(MainActivity.this,"请输入整数！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(textView.getText().toString().equals("添加")){
                    Log.d("Test", "onClick: 添加");
                    rbTree.insert(data);
                    rbTreeView.setInitData(rbTree.getHeight(),rbTree.getPosition(),rbTree.getSideList());

                }else {
                    Log.d("Test", "onClick: 删除");
                    rbTree.delete(data);
                    rbTreeView.setInitData(rbTree.getHeight(),rbTree.getPosition(),rbTree.getSideList());
                }
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        button_random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "onClick: 随机添加");
                int data = (int) (Math.random()*100);
                rbTree.insert(data);
                Toast.makeText(MainActivity.this,"添加:"+data,Toast.LENGTH_SHORT).show();
                rbTreeView.setInitData(rbTree.getHeight(),rbTree.getPosition(),rbTree.getSideList());
                int w=hor_scrollview.getHeight();
                hor_scrollview.scrollTo(w*4,w);
            }
        });
        delete_all_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbTree.clear();
                rbTreeView.setInitData(rbTree.getHeight(),rbTree.getPosition(),rbTree.getSideList());
            }
        });
        determine_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
    }



}
