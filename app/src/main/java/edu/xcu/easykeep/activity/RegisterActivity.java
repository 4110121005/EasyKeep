package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xcu.easykeep.bean.UserBean;
import edu.xcu.easykeep.databinding.ActivityRegisterBinding;
import edu.xcu.easykeep.db.UserDBManger;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final EditText etRegisterUid = binding.etRegisterUid;
        final EditText etRegisterPass = binding.etRegisterPass;
        final EditText etCheckPass = binding.etCheckPass;
        ImageView ivWarn = binding.ivWarn;
        Button btnGotoLogin = binding.btnGotoLogin;
        Button btnRegister = binding.btnRegister;

        /*
         * 为注册按钮设置点击事件，检查用户信息并创建账号
         * */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDBManger userDBManger = new UserDBManger(getApplicationContext());

                final String upassword = etRegisterPass.getText().toString();
                final String checkPass = etCheckPass.getText().toString();
                final String uid = etRegisterUid.getText().toString();

                if(uid.isEmpty() || upassword.isEmpty()){
                    Toast.makeText(getApplicationContext(),  "请输入账号和密码", Toast.LENGTH_LONG).show();
                    return;
                }
                //检查两次密码是否相同
                if(!upassword.equals(checkPass)){
                    etCheckPass.clearFocus();//先清除焦点
                    ivWarn.setVisibility(View.VISIBLE);
                    /*
                    * 设置焦点事件，只有在输入框获得焦点时才会取消警告
                    * */
                    etCheckPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if(hasFocus){
                                ivWarn.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                }
                else{
                    int result = userDBManger.userRegister(new UserBean(uid, upassword));
                    if (result == 1) {
                        Toast.makeText(getApplicationContext(),  "注册成功", Toast.LENGTH_SHORT).show();
                    } else if (result == 0) {
                        Toast.makeText(getApplicationContext(), "用户已存在", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "注册失败，请稍后再试", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        /*
         * 为跳登录按钮设置点击事件，跳转到登录界面
         * */
        btnGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}