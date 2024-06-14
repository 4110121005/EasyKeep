package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xcu.easykeep.bean.UserBean;
import edu.xcu.easykeep.databinding.ActivityRegisterBinding;
import edu.xcu.easykeep.db.UserDBManger;

/**
 * 注册界面 Activity
 */
public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserDBManger userDBManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化数据库管理类
        userDBManger = new UserDBManger(this);

        // 设置注册按钮点击事件
        binding.btnRegister.setOnClickListener(v -> handleRegisterButtonClick());

        // 设置跳转到登录界面按钮点击事件
        binding.btnGotoLogin.setOnClickListener(v -> goToLoginActivity());
    }

    /**
     * 处理注册按钮点击事件
     */
    private void handleRegisterButtonClick() {
        // 获取用户输入的账号和密码
        String uid = binding.etRegisterUid.getText().toString().trim();
        String upassword = binding.etRegisterPass.getText().toString().trim();

        // 校验输入是否为空
        if (uid.isEmpty() || upassword.isEmpty()) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 注册用户
        int result = userDBManger.userRegister(new UserBean(uid, upassword));

        // 根据注册结果显示提示信息
        switch (result) {
            case 1:
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                goToLoginActivity(); // 注册成功后跳转到登录界面
                break;
            case 0:
                Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "注册失败，请稍后再试", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 跳转到登录界面
     */
    private void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // 关闭当前 Activity
    }
}
