package com.example.user.sprachtrainer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;


import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.sprachtrainer.io.VolleySingleton;
import com.example.user.sprachtrainer.io.VolleySingleton;
import com.example.user.sprachtrainer.models.Login;
import com.example.user.sprachtrainer.models.User;
import com.example.user.sprachtrainer.utils.SessionPrefs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.RuntimeException;
import  java.lang.Object;
import java.util.Map;


import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    public static  String BASE_URL = "http://sprachtrainer.naylamp1.com/v2/public/index.php/user/auth/query";
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
   /* public Retrofit mRestAdapter;
    private SprachtrainerService SprachtrainerServiceApi;*/

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set up Server connection


       /* mRestAdapter = new Retrofit.Builder().baseUrl(SprachtrainerService.BASE_URL).
                addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        SprachtrainerServiceApi= mRestAdapter.create(SprachtrainerService.class);*/
        //
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }
    /**
     * Attempts to sign in or re
     * 11/18 18:31:30: Launching app
     * $ adb install-multiple -r -t -p com.example.user.sprachtrainer C:\Users\User\AndroidStudioProjects\sprachtrainer\app\build\intermediates\split-apk\debug\slices\slice_4.apk
     * Split APKs installed in 17 s 912 ms
     * $ adb shell am start -n "com.example.user.sprachtrainer/com.example.user.sprachtrainer.LoginActivity" -a android.intent.action.MAIN -c android.intent.category.LAUNCHER
     * Client not ready yet..Waiting for process to come online
     * Connected to process 18016 on device samsung-sm_a600fn-5200981b5e6e9595
     * Capturing and displaying logcat messages from application. This behavior can be disabled in the "Logcat output" section of the "Debugger" settings page.
     * I/zygote: no shared libraies, dex_files: 1
     * I/InstantRun: starting instant run server: is main process
     * I/zygote: Rejecting re-init on previously-failed class java.lang.Class<android.support.v4.view.ViewCompat$OnUnhandledKeyEventListenerWrapper>: java.lang.NoClassDefFoundError: Failed resolution of: Landroid/view/View$OnUnhandledKeyEventListener;
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     *         at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     *         at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     * I/zygote:     at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     *         at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     *     Caused by: java.lang.ClassNotFoundException: Didn't find class "android.view.View$OnUnhandledKeyEventListener" on path: DexPathList[[zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/base.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_dependencies_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_resources_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_0_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_1_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_2_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_3_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_4_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0
     *         at java.lang.Class dalvik.system.BaseDexClassLoader.findClass(java.lang.String) (BaseDexClassLoader.java:93)
     *         at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String, boolean) (ClassLoader.java:379)
     *         at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String) (ClassLoader.java:312)
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     * I/zygote:     at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     *         at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     *         at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     * I/zygote:     at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     * I/zygote: Rejecting re-init on previously-failed class java.lang.Class<android.support.v4.view.ViewCompat$OnUnhandledKeyEventListenerWrapper>: java.lang.NoClassDefFoundError: Failed resolution of: Landroid/view/View$OnUnhandledKeyEventListener;
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     *         at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     * I/zygote:     at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     *         at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     *         at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     *     Caused by: java.lang.ClassNotFoundException: Didn't find class "android.view.View$OnUnhandledKeyEventListener" on path: DexPathList[[zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/base.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_dependencies_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_resources_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_0_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_1_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_2_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_3_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_4_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0
     *         at java.lang.Class dalvik.system.BaseDexClassLoader.findClass(java.lang.String) (BaseDexClassLoader.java:93)
     *         at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String, boolean) (ClassLoader.java:379)
     *         at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String) (ClassLoader.java:312)
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     *         at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     *         at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     *         at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     *         at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     * I/zygote: Rejecting re-init on previously-failed class java.lang.Class<android.support.v4.view.ViewCompat$OnUnhandledKeyEventListenerWrapper>: java.lang.NoClassDefFoundError: Failed resolution of: Landroid/view/View$OnUnhandledKeyEventListener;
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     *         at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     *         at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     *         at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     *         at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     *     Caused by: java.lang.ClassNotFoundException: Didn't find class "android.view.View$OnUnhandledKeyEventListener" on path: DexPathList[[zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/base.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_dependencies_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_resources_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_0_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_1_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_2_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_3_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0ACi8A==/split_lib_slice_4_apk.apk", zip file "/data/app/com.example.user.sprachtrainer-BGV_PW9toO8U2ZNa0
     *         at java.lang.Class dalvik.system.BaseDexClassLoader.findClass(java.lang.String) (BaseDexClassLoader.java:93)
     *         at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String, boolean) (ClassLoader.java:379)
     * I/zygote:     at java.lang.Class java.lang.ClassLoader.loadClass(java.lang.String) (ClassLoader.java:312)
     *         at void android.support.v4.view.ViewCompat.setBackground(android.view.View, android.graphics.drawable.Drawable) (ViewCompat.java:2341)
     *         at void android.support.v7.widget.ActionBarContainer.<init>(android.content.Context, android.util.AttributeSet) (ActionBarContainer.java:62)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance0(java.lang.Object[]) (Constructor.java:-2)
     *         at java.lang.Object java.lang.reflect.Constructor.newInstance(java.lang.Object[]) (Constructor.java:334)
     *         at android.view.View android.view.LayoutInflater.createView(java.lang.String, java.lang.String, android.util.AttributeSet) (LayoutInflater.java:647)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:790)
     *         at android.view.View android.view.LayoutInflater.createViewFromTag(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet) (LayoutInflater.java:730)
     *         at void android.view.LayoutInflater.rInflate(org.xmlpull.v1.XmlPullParser, android.view.View, android.content.Context, android.util.AttributeSet, boolean) (LayoutInflater.java:863)
     *         at void android.view.LayoutInflater.rInflateChildren(org.xmlpull.v1.XmlPullParser, android.view.View, android.util.AttributeSet, boolean) (LayoutInflater.java:824)
     *         at android.view.View android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean) (LayoutInflater.java:515)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean) (LayoutInflater.java:423)
     *         at android.view.View android.view.LayoutInflater.inflate(int, android.view.ViewGroup) (LayoutInflater.java:374)
     *         at android.view.ViewGroup android.support.v7.app.AppCompatDelegateImpl.createSubDecor() (AppCompatDelegateImpl.java:607)
     *         at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)
     *         at void android.support.v7.app.AppCompatDelegateImpl.setContentView(int) (AppCompatDelegateImpl.java:466)
     *         at void android.support.v7.app.AppCompatActivity.setContentView(int) (AppCompatActivity.java:140)
     *         at void com.example.user.sprachtrainer.LoginActivity.onCreate(android.os.Bundle) (LoginActivity.java:82)
     *         at void android.app.Activity.performCreate(android.os.Bundle) (Activity.java:7183)
     *         at void android.app.Instrumentation.callActivityOnCreate(android.app.Activity, android.os.Bundle) (Instrumentation.java:1220)
     *         at android.app.Activity android.app.ActivityThread.performLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent) (ActivityThread.java:2910)
     *         at void android.app.ActivityThread.handleLaunchActivity(android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:3032)
     *         at void android.app.ActivityThread.-wrap11(android.app.ActivityThread, android.app.ActivityThread$ActivityClientRecord, android.content.Intent, java.lang.String) (ActivityThread.java:-1)
     *         at void android.app.ActivityThread$H.handleMessage(android.os.Message) (ActivityThread.java:1696)
     *         at void android.os.Handler.dispatchMessage(android.os.Message) (Handler.java:105)
     *         at void android.os.Looper.loop() (Looper.java:164)
     *         at void android.app.ActivityThread.main(java.lang.String[]) (ActivityThread.java:6944)
     *         at java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object[]) (Method.java:-2)
     *         at void com.android.internal.os.Zygote$MethodAndArgsCaller.run() (Zygote.java:327)
     *         at void com.android.internal.os.ZygoteInit.main(java.lang.String[]) (ZygoteInit.java:1374)
     * D/ScrollView: initGoToTop
     * I/TextInputLayout: EditText added is not a TextInputEditText. Please switch to using that class instead.
     * I/TextInputLayout: EditText added is not a TextInputEditText. Please switch to using that class instead.
     * D/NetworkSecurityConfig: No Network Security Config specified, using platform default
     * D/OpenGLRenderer: HWUI GL Pipeline
     * D/ViewRootImpl@f9d170e[LoginActivity]: setView = DecorView@5bd452f[LoginActivity] TM=true MM=false
     * D/ViewRootImpl@f9d170e[LoginActivity]: dispatchAttachedToWindow
     * V/Surface: sf_framedrop debug : 0x4f4c, game : false, logging : 0
     * D/ViewRootImpl@f9d170e[LoginActivity]: Relayout returned: old=[0,0][0,0] new=[0,0][720,1480] result=0x7 surface={valid=true 3656525824} changed=true
     * I/OpenGLRenderer: Initialized EGL, version 1.4
     * D/OpenGLRenderer: Swap behavior 2
     * D/libGLESv1: STS_GLApi : DTS, ODTC are not allowed for Package : com.example.user.sprachtrainer
     * D/mali_winsys: EGLint new_window_surface(egl_winsys_display *, void *, EGLSurface, EGLConfig, egl_winsys_surface **, egl_color_buffer_format *, EGLBoolean) returns 0x3000,  [720x1480]-format:1
     * D/OpenGLRenderer: eglCreateWindowSurface = 0xcca5a480
     * D/ScrollView:  onsize change changed
     * D/ViewRootImpl@f9d170e[LoginActivity]: MSG_RESIZED_REPORT: frame=Rect(0, 0 - 720, 1480) ci=Rect(0, 48 - 0, 96) vi=Rect(0, 48 - 0, 96) or=1
     * D/ViewRootImpl@f9d170e[LoginActivity]: MSG_WINDOW_FOCUS_CHANGED 1
     * V/InputMethodManager: Starting input: tba=android.view.inputmethod.EditorInfo@326c05d nm : com.example.user.sprachtrainer ic=com.android.internal.widget.EditableInputConnection@b0a6dd2
     * I/InputMethodManager: startInputInner - mService.startInputOrWindowGainedFocus
     * W/View: dispatchProvideAutofillStructure(): not laid out, ignoring
     * W/View: dispatchProvideAutofillStructure(): not laid out, ignoring
     * I/AssistStructure: Flattened final assist data: 3064 bytes, containing 1 windows, 12 views
     * V/InputMethodManager: Starting input: tba=android.view.inputmethod.EditorInfo@352eea3 nm : com.example.user.sprachtrainer ic=com.android.internal.widget.EditableInputConnection@41775a0
     * D/ViewRootImpl@f9d170e[LoginActivity]: MSG_RESIZED: frame=Rect(0, 0 - 720, 1480) ci=Rect(0, 48 - 0, 678) vi=Rect(0, 48 - 0, 678) or=1
     * D/ViewRootImpl@f9d170e[LoginActivity]: Relayout returned: old=[0,0][720,1480] new=[0,0][720,1480] result=0x1 surface={valid=true 3656525824} changed=false
     * D/ScrollView:  onsize change changed
     * I/zygote: Do partial code cache collection, code=30KB, data=25KB
     * I/zygote: After code cache collection, code=30KB, data=25KB
     *     Increasing code cache capacity to 128KB
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 0
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 1
     * D/InputMethodManager: SSI - flag : 0 Pid : 18016 view : com.example.user.sprachtrainer
     * I/zygote: Do partial code cache collection, code=51KB, data=51KB
     * I/zygote: After code cache collection, code=51KB, data=51KB
     *     Increasing code cache capacity to 256KB
     * I/zygote: Compiler allocated 4MB to compile void android.view.ViewRootImpl.performTraversals()
     * I/zygote: Do full code cache collection, code=125KB, data=123KB
     * I/zygote: After code cache collection, code=108KB, data=94KB
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 0
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 1
     * V/InputMethodManager: Starting input: tba=android.view.inputmethod.EditorInfo@b4781ce nm : com.example.user.sprachtrainer ic=com.android.internal.widget.EditableInputConnection@a5338ef
     * I/InputMethodManager: startInputInner - mService.startInputOrWindowGainedFocus
     * D/InputMethodManager: SSI - flag : 0 Pid : 18016 view : com.example.user.sprachtrainer
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 0
     * D/ViewRootImpl@f9d170e[LoginActivity]: ViewPostIme pointer 1
     * I/zygote: Do partial code cache collection, code=114KB, data=110KB
     *     After code cache collection, code=114KB, data=110KB
     *     Increasing code cache capacity to 512KB
     * D/ViewRootImpl@52b2439[PopupWindow:6bd3d32]: setView = android.widget.PopupWindow$PopupDecorView{57dfa7e V.E...... ......I. 0,0-0,0} TM=true MM=false
     * D/ViewRootImpl@52b2439[PopupWindow:6bd3d32]: dispatchAttachedToWindow
     * V/Surface: sf_framedrop debug : 0x4f4c, game : false, logging : 0
     * D/ViewRootImpl@52b2439[PopupWindow:6bd3d32]: Relayout returned: old=[0,0][0,0] new=[304,387][710,497] result=0x7 surface={valid=true 3404666880} changed=true
     * D/mali_winsys: EGLint new_window_surface(egl_winsys_display *, void *, EGLSurface, EGLConfig, egl_winsys_surface **, egl_color_buffer_format *, EGLBoolean) returns 0x3000,  [406x110]-format:1
     * D/OpenGLRenderer: eglCreateWindowSurface = 0xcca5ab00
     * D/ViewRootImpl@52b2439[PopupWindow:6bd3d32]: MSG_RESIZED_REPORT: frame=Rect(304, 387 - 710, 497) ci=Rect(0, 0 - 0, 0) vi=Rect(0, 0 - 0, 0) or=1
     * D/OpenGLRenderer: eglDestroySurface = 0xcca5ab00
     * D/ViewRootImpl@52b2439[PopupWindow:6bd3d32]: dispatchDetachedFromWindow
     * D/InputEventReceiver: channel '6039745 PopupWindow:6bd3d32 (client)' ~ Disposing input event receiver.
     *     channel '6039745 PopupWindow:6bd3d32 (client)' ~NativeInputEventReceiver.
     * D/ViewRootImpl@f9d170e[LoginActivity]: Relayout returned: old=[0,0][720,1480] new=[0,0][720,1480] result=0x1 surface={valid=true 3656525824} changed=false
     * D/ViewRootImpl@f9d170e[LoginActivity]: Relayout returned: old=[0,0][720,1480] new=[0,0][720,1480] result=0x1 surface={valid=true 3656525824} changed=false
     * V/InputMethodManager: Starting input: tba=android.view.inputmethod.EditorInfo@f59e243 nm : com.example.user.sprachtrainer ic=com.android.internal.widget.EditableInputConnection@f07f0c0
     * I/InputMethodManager: startInputInner - mService.startInputOrWindowGainedFocus
     * D/ViewRootImpl@f9d170e[LoginActivity]: MSG_WINDOW_FOCUS_CHANGED 0
     * D/OpenGLRenderer: eglDestroySurface = 0xcca5a480gister the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin()  {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            //Call our volley library
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("Email", email);
                jsonBody.put("Password", password);
                final String requestBody = jsonBody.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String requestBody = jsonBody.toString();
            String uri = BASE_URL+"?Email="+ email+"&Password="+password;
            StringRequest  stringRequest = new StringRequest(Request.Method.GET,uri,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String  response) {
                            Log.i("VOLLEY", response);

                            Toast.makeText(getApplicationContext(), "Response:  " + response.toString(), Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject obj = new JSONObject(response);
                                //storing the user in shared preferences
                                SessionPrefs.getInstance(getApplicationContext()).storeUserName(obj.getString("Token"));
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                    },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                    Toast.makeText(getApplicationContext(),"Connection Error"+error, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });


            /*{
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {

                    String parsed;
                    try {
                        parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    } catch (UnsupportedEncodingException e) {
                        parsed = new String(response.data);
                    }
                    return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));

                }*/
            ;

           // requestQueue.add(stringRequest);
            VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);

           /* Call<User> loginCall=SprachtrainerServiceApi.login(new Login(email,password));
            loginCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    showProgress(false);
                    //errors
                    String error;
                    Gson gson = new Gson();
                    if(!response.isSuccessful()){

                        if (response.code() == 400 ) {
                            Log.d("LoginActivity", "onResponse - Status : " + response.code());
                        }
                        if(response.errorBody().contentType().subtype().equals("application/json")){
                          //  ApiError apiError =ApiError.fromResponseBody(response.errorBody());
                            //error= apiError.getMessage();
                            Toast.makeText(getApplicationContext(), "Credential are not valid",
                                    Toast.LENGTH_SHORT).show();

                            Log.d("LoginActivity", "error de conection");
                        }

                    }else{

                        int statusCode = response.code();
                        User user =response.body();
                        Log.e("LoginActivity","apt"+ response.errorBody());
                        error = response.message();
                    }

                    // Guardar afiliado en preferencias

                   // SessionPrefs.get().saveAffiliate(response.body());


                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });*/





           // mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.1
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

