package com.example.myinterestingtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.douyin.DouyinActivity;
import com.example.douyin.tiktok.TikTokActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_douyin_one)
    Button btnDouyinOne;
    @BindView(R.id.btn_douyin_two)
    Button btnDouyinTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_douyin_one, R.id.btn_douyin_two})
    public void onViewClicked(View view) {
        Intent intent=new Intent(this, TikTokActivity.class);
        switch (view.getId()) {
            case R.id.btn_douyin_one:
                intent.putExtra("type","one");
                startActivity(intent);

                break;
            case R.id.btn_douyin_two:
                intent.putExtra("type","two");
                startActivity(intent);
                break;
        }
    }
}
