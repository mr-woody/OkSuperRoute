package com.example.module_a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common_service.Pages;
import com.example.common_service.base.BaseActivity;
import com.example.common_service.utlis.ProcessUtil;
import com.woodys.router.Router;
import com.woodys.router.annotation.Route;
import com.woodys.router.annotation.RouteConfig;

@RouteConfig(baseUrl = "woodys://page/", pack = "com.woodys.a")
@Route(Pages.A_MAIN)
public class MainActivity extends BaseActivity {

    static boolean IS_ONLINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_a_activity_main);

        TextView tv_info = findViewById(R.id.tv_info);
        tv_info.setText("当前页面：MainActivity\n当前组件：module_a\n当前进程：" + ProcessUtil.getProcessName(this));

        addViewClickEvent(R.id.btn_navigator_b_second, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.create(Pages.B_SECOND).open(MainActivity.this);
            }
        });

        addViewClickEvent(R.id.btn_navigator4result, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivityForResult的用法，需要在onActivityResult中处理回调
                Router.create(Pages.B_SECOND).open(MainActivity.this);
            }
        });

        addViewClickEvent(R.id.btn_navigator_callback, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //推荐
                //DRouter提供的直接使用Callback的用法，不需要重写onActivityResult
                Router.create(Pages.B_SECOND).open(MainActivity.this);
            }
        });

        addViewClickEvent(R.id.btn_interceptor, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.create(Pages.A_THIRD).open(MainActivity.this);
            }
        });

        addViewClickEvent(R.id.btn_login, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_ONLINE) {
                    ((TextView) v).setText("模拟登录");
                } else {
                    ((TextView) v).setText("退出登录");
                }
                IS_ONLINE = !IS_ONLINE;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 66) {
            String s = "";
            if (data != null) {
                s = data.getStringExtra("callback");
            }
            Toast.makeText(MainActivity.this, "startActivityForResult:" + s,Toast.LENGTH_SHORT).show();
        }
    }
}
