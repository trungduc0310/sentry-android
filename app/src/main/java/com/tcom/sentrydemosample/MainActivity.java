package com.tcom.sentrydemosample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tcom.logingsdk.LogAttachment;
import com.tcom.logingsdk.LogCapture;
import com.tcom.logingsdk.UserBuilder;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJava, btnMess, btnSendFeedBack, btnSetUser, btnUnsetUser, btnAddAttachment,
            btnBreadcrumb, btnCapException;
    private UserBuilder userBuilder;
    private LogCapture logCapture;
    private LogAttachment logAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userBuilder = new UserBuilder().setId("id1")
                .setUserName("abc1")
                .setEmail("abc123@gmail.com");
        userBuilder.create();
        logCapture = new LogCapture(userBuilder);
        logAttachment = new LogAttachment();
        initView();
        setEventClick();
    }

    private void initView() {
        btnJava = findViewById(R.id.crash_from_java);
        btnMess = findViewById(R.id.send_message);
        btnSendFeedBack = findViewById(R.id.send_user_feedback);
        btnSetUser = findViewById(R.id.set_user);
        btnUnsetUser = findViewById(R.id.unset_user);
        btnAddAttachment = findViewById(R.id.add_attachment);
        btnBreadcrumb = findViewById(R.id.breadcrumb);
        btnCapException = findViewById(R.id.capture_exception);
    }

    private void setEventClick() {
        btnJava.setOnClickListener(this);
        btnMess.setOnClickListener(this);
        btnSendFeedBack.setOnClickListener(this);
        btnSetUser.setOnClickListener(this);
        btnUnsetUser.setOnClickListener(this);
        btnAddAttachment.setOnClickListener(this);
        btnBreadcrumb.setOnClickListener(this);
        btnCapException.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crash_from_java:
                int b = 2 / 0;
                Log.d("Crash b: ", String.valueOf(b));
                break;
            case R.id.send_message:
                try {
                    logCapture.captureMessage("Log message");
                } catch (Exception ex) {
                    logCapture.captureExeption(ex);
                }
                break;
            case R.id.send_user_feedback:
                try {
                    logCapture.captureFeedback("User abc1 feedback");
                } catch (Exception ex) {
                    logCapture.captureExeption(ex);
                }
                break;
            case R.id.breadcrumb:

                break;
            case R.id.set_user:

                break;
            case R.id.unset_user:
                userBuilder.unSetUser();
                break;
            case R.id.add_attachment:
                logAttachment.addAttachment("localPath","fileLog");
                break;
            case R.id.capture_exception:
                try {
                    int c = 2 / 0;
                } catch (Exception ex) {
                    logCapture.captureExeption(ex);
                }
                break;
        }
    }
}
