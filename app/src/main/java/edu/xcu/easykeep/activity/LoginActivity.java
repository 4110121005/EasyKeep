package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xcu.easykeep.bean.UserBean;
import edu.xcu.easykeep.databinding.ActivityLoginBinding;
import edu.xcu.easykeep.db.UserDBManger;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String data = "yyds";//保存用户登录状态的文件名
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        autoLogin();

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
                UserDBManger userDBManger = new UserDBManger(getApplicationContext());

                String uid = etLoginUid.getText().toString();
                String upassword = etLoginPass.getText().toString();


                int result = userDBManger.userLogin(new UserBean(uid, upassword));
                if (result == 1) {
                    Toast.makeText(getApplicationContext(), "用户" + uid + "登录成功", Toast.LENGTH_SHORT).show();

                    keepLogin(uid);
                    gotoMainActivity(uid);
                } else if (result == 0) {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                }
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

    /*自动登录：（登录一次后）跳过登录界面直接进入主界面
    */
    private void autoLogin(){
        sharedPreferences = getSharedPreferences(data, MODE_PRIVATE);
        String uid = sharedPreferences.getString("uid", null);
        if(uid != null){
            gotoMainActivity(uid);
        }
    }
    /*保存用户
    *
    * uid：需要保存的用户uid
    * */
    private void keepLogin(String uid){
        sharedPreferences = getSharedPreferences(data, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("uid",uid);//保存uid
        editor.commit();
    }

    /*跳转到主界面
    *
    * uid：传递给主界面的uid，以供其识别用户，读取相应的数据
    * */
    private void gotoMainActivity(String uid){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}