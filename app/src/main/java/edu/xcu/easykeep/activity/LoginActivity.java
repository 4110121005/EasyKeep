package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xcu.easykeep.R;
import edu.xcu.easykeep.databinding.ActivityLoginBinding;
import edu.xcu.easykeep.databinding.ActivityMainBinding;
import edu.xcu.easykeep.db.UserDBHelper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText etLoginUid = binding.etLoginUid;
        EditText etLoginPass = binding.etLoginPass;
        Button btnLogin = binding.btnLogin;
        Button btnGotoRegister = binding.btnGotoRegister;

        /*
         * 为登录按钮设置点击事件，检查用户信息
         * */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                UserDBHelper userDbHelper = new UserDBHelper(getApplicationContext());

                String uid = etLoginUid.getText().toString();
                String upassword = etLoginPass.getText().toString();

                //没有后端，为了避免频繁的注册账号，直接指定一个初始账号
                userDbHelper.userRegister("5001210114","123456");

                int result = userDbHelper.userLogin(uid, upassword);
                if (result == 1) {
                    Toast.makeText(getApplicationContext(), "用户" + uid + "登录成功", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if (result == 0) {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                }*/
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /*
         * 为跳转注册按钮设置点击事件，跳转到注册界面
         * */
        btnGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}