package edu.xcu.easykeep.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.xcu.easykeep.EasyKeepApp;
import edu.xcu.easykeep.bean.UserBean;
import edu.xcu.easykeep.databinding.ActivityLoginBinding;
import edu.xcu.easykeep.db.UserDBManger;

/**
 * 登录活动类，负责处理用户登录逻辑及界面交互。
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserDBManger userDBManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 尝试自动登录，如果用户已登录，则直接跳转到主界面
        attemptAutoLogin();

        // 初始化视图绑定
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 初始化数据库管理类
        userDBManger = new UserDBManger(this);

        // 设置登录按钮点击事件
        binding.btnLogin.setOnClickListener(v -> handleLoginButtonClick());

        // 设置跳转到注册界面按钮点击事件
        binding.btnGotoRegister.setOnClickListener(v -> gotoRegisterActivity());
    }

    /**
     * 尝试自动登录用户。
     * 如果在 SharedPreferences 中找到了用户 ID，则认为用户已登录，跳转到主界面。
     */
    private void attemptAutoLogin() {
        EasyKeepApp app = (EasyKeepApp) getApplicationContext();
        SharedPreferences sharedPreferences = app.getSharedPreferences();
        String uid = sharedPreferences.getString("uid", null);
        if (uid != null) {
            gotoMainActivity(uid);
        }
    }

    /**
     * 保存用户的登录状态。
     * 将用户 ID 保存到 SharedPreferences 中，以便下次自动登录。
     *
     * @param uid 要保存的用户ID。
     */
    private void saveLoginState(String uid) {
        EasyKeepApp app = (EasyKeepApp) getApplicationContext();
        SharedPreferences.Editor editor = app.getSharedPreferences().edit();
        editor.putString("uid", uid);
        editor.apply();
    }

    /**
     * 导航到主界面。
     * 将用户 ID 传递给主界面，以便主界面加载用户数据。
     *
     * @param uid 要传递给主界面的用户ID。
     */
    private void gotoMainActivity(String uid) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
        finish();
    }

    /**
     * 处理用户登录按钮点击事件。
     * 获取用户输入的用户名和密码，验证登录信息，
     * 如果登录成功，保存登录状态并跳转到主界面，否则提示错误信息。
     */
    private void handleLoginButtonClick() {
        // 获取用户输入的账号和密码
        String uid = binding.etLoginUid.getText().toString();
        String upassword = binding.etLoginPass.getText().toString();

        // 校验输入是否为空
        if (uid.isEmpty() || upassword.isEmpty()) {
            Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 用户登录
        int result = userDBManger.userLogin(new UserBean(uid, upassword));

        // 根据返回结果显示提示信息
        switch (result) {
            case 1: // 登录成功
                Toast.makeText(getApplicationContext(), "用户" + uid + "登录成功", Toast.LENGTH_SHORT).show();
                saveLoginState(uid);
                gotoMainActivity(uid);
                break;
            case 0: // 密码错误
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
                break;
            default: // 用户不存在
                Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 跳转到注册界面。
     * 启动注册 Activity，并结束当前 Activity。
     */
    private void gotoRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
