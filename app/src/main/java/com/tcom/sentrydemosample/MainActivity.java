package com.tcom.sentrydemosample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import io.sentry.Attachment;
import io.sentry.Breadcrumb;
import io.sentry.Sentry;
import io.sentry.SentryLevel;
import io.sentry.UserFeedback;
import io.sentry.android.core.SentryAndroid;
import io.sentry.protocol.SentryId;
import io.sentry.protocol.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnJava, btnMess, btnSendFeedBack, btnSetUser, btnUnsetUser, btnAddAttachment,
            btnBreadcrumb, btnCapException;
    private User user;
    Attachment fileAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new User();
        user.setEmail("ducphamtrung1998@gmail.com");
        byte[] bytes = new byte[2048];
        try {
            getAssets().open("testAttachment.txt").read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileAttachment = new Attachment(bytes, "testAttachment.txt",
                "application/octet-stream", true);
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
                    Sentry.captureMessage("Test send message");
                } catch (Exception ex) {
                    Sentry.captureException(ex);
                }
                break;
            case R.id.send_user_feedback:
                try {
                    UserFeedback userFeedback = new UserFeedback(Sentry.captureMessage("My message"));
                    userFeedback.setComments("User Feedback Comments");
                    userFeedback.setEmail(user.getEmail());
                    userFeedback.setName("MyName");
                    Sentry.captureUserFeedback(userFeedback);
                } catch (Exception ex) {
                    Sentry.captureException(ex);
                }
                break;
            case R.id.breadcrumb:
                Sentry.configureScope(scope -> {
                    Breadcrumb breadcrumb = new Breadcrumb();
                    breadcrumb.setCategory("auth");
                    breadcrumb.setMessage("Authenticated user " + user.getEmail());
                    breadcrumb.setLevel(SentryLevel.INFO);
                    scope.addBreadcrumb(breadcrumb);
                });
                break;
            case R.id.set_user:
                Sentry.configureScope(scope -> {
                    scope.setUser(user);
                });
                break;
            case R.id.unset_user:
                Sentry.configureScope(scope -> {
                    scope.setUser(null);
                });
                break;
            case R.id.add_attachment:
                Sentry.withScope(
                        scope -> {
                            scope.addAttachment(fileAttachment);
                            Sentry.captureMessage("my Attachment");
                        });
                break;
            case R.id.capture_exception:
                try {
                    int c = 2 / 0;
                    Log.d("onClick: ", String.valueOf(c));
                } catch (Exception ex) {
                    Sentry.captureException(ex);
                }
                break;
        }
    }
}
