package com.example.module_a;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.common_service.Pages;
import com.example.common_service.base.BaseActivity;
import com.example.common_service.utlis.ProcessUtil;
import com.woodys.router.annotation.Route;

@Route(Pages.A_SECOND)
public class SecondActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_a_activity_second);

        TextView tv_info = findViewById(R.id.tv_info);
        tv_info.setText("当前页面：SecondActivity\n当前组件：module_a\n当前进程：" + ProcessUtil.getProcessName(this));

        addViewClickEvent(R.id.bt_event_a, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
