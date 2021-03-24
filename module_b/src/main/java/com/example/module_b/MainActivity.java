package com.example.module_b;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.common_service.Pages;
import com.example.common_service.base.BaseActivity;
import com.example.common_service.utlis.ProcessUtil;
import com.woodys.router.Router;
import com.woodys.router.annotation.Route;
import com.woodys.router.annotation.RouteConfig;

@RouteConfig(baseUrl = "woodys://page/", pack = "com.woodys.b")
@Route(Pages.B_MAIN)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_b_activity_main);

        TextView tv_info = findViewById(R.id.tv_info);
        tv_info.setText("当前页面：MainActivity\n当前组件：module_b\n当前进程：" + ProcessUtil.getProcessName(this));


        addViewClickEvent(R.id.btn_multi_router, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.create(Pages.C_SECOND).open(MainActivity.this);
            }
        });
    }
}
