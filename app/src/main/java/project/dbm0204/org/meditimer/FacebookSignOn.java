package project.dbm0204.org.meditimer;

import android.app.Dialog;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dbm0204 on 9/10/17.
 */

public class FacebookSignOn extends FragmentActivity implements View.OnClickListener,UserInfoChangedCallback{
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private Dialog share_dialog, user_profile_dailog;
    private Button userProfileBtn, updateStatusBtn, shareBtn, okBtn, timelineBtn, photoBtn;
    private TextView userName, id, url,name;
    private LoginButton loginBtn;
    private ImageView userImage;
    private EditText etshare;
    private UiLifecycleHelper uHelper;
    private HttpMethod httpMethod;
    private GraphUser user;
    private Session.StatusCallback statusCallback = new Session.StatusCallback(){
        @Override
        public void call(Session session, SessionState state, Exception exception){
            if(state.isOpened()){
                buttonsEnabled(true);
                Log.d("FacebookSampleActivity", "Facebook session opened");
            } else if(state.isClosed()){
                buttonsEnabled(false);
                Log.d("FacebookSampleActivity","Facebook session closed");
            }
        }

    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uHelper = new UiLifecycleHelper(this, statusCallback);
        uHelper.onCreate(savedInstanceState);

        setContentView(R.layout.activity_facebook);

        userName = (TextView) findViewById(R.id.user_name);
        loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
        userProfileBtn = (Button) findViewById(R.id.user_profile_btn);
        updateStatusBtn = (Button) findViewById(R.id.update_status);
        timelineBtn = (Button) findViewById(R.id.btn_timeline);
        photoBtn = (Button) findViewById(R.id.btn_photo);

        userProfileBtn.setOnClickListener(this);
        updateStatusBtn.setOnClickListener(this);
        loginBtn.setUserInfoChangedCallback(this);

        buttonsEnabled(false);
    }
    public void postStatusMessage(String message) {
        if (checkPermissions()) {
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), message,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(getApplicationContext(),
                                        "Status updated successfully",
                                        Toast.LENGTH_LONG).show();
                        }
                    });
            request.executeAsync();
        } else {
            requestPermission();
        }
    }
    public void buttonsEnabled(boolean isEnabled){
        userProfileBtn.setEnabled(isEnabled);
        updateStatusBtn.setEnabled(isEnabled);
        timelineBtn.setEnabled(false);
        photoBtn.setEnabled(false);
    }
    public void requestPermission(){
        Session session = Session.getActiveSession();
        if(session!=null){
            session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this,PERMISSIONS));
        }
    }
    public boolean checkPermissions(){
        Session session =Session.getActiveSession();
        return session!=null && session.getPermissions().contains(PERMISSIONS);

    }
    @Override
    public void onResume(){
        super.onResume();
        uHelper.onResume();
        buttonsEnabled(Session.getActiveSession().isOpened());
    }
    @Override
    public void onPause(){
        super.onPause();
        uHelper.onPause();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        uHelper.onDestroy();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        uHelper.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onSavedInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        uHelper.onSaveInstanceState(savedState);
    }
    @Override
    public void onUserInfoFetched(GraphUser user) {
        if(user !=null){
            userName.setText("Hello "+user.getName());
            this.user=user;
        } else {
            userName.setText("You are not logged in");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_profile_btn:
                if (Session.getActiveSession().isOpened()) {
                    try {

                        // URL img_url = new URL("http://graph.facebook.com/"+user.getId()+"/picture?type=normal");
                        // Bitmap bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
                        user_profile_dailog = new Dialog(getApplicationContext());
                        user_profile_dailog.setContentView(R.layout.fragment_user_profile);
                        user_profile_dailog.setTitle("User Profile");
                        name = (TextView) user_profile_dailog.findViewById(R.id.get_name);
                        id = (TextView) user_profile_dailog.findViewById(R.id.get_id);
                        url = (TextView) user_profile_dailog.findViewById(R.id.get_url);
                        okBtn = (Button) user_profile_dailog.findViewById(R.id.ok_button);
                        userImage = (ImageView) user_profile_dailog.findViewById(R.id.imgUser);
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                user_profile_dailog.cancel();
                                Session session = Session.getActiveSession();
                                if (session != null && session.getState().isOpened()) {
                                    Log.i("sessionToken", session.getAccessToken());
                                    Log.i("sessionTokenDueDate", session.getExpirationDate().toLocaleString());
                                }
                            }
                        });
                        //                new RetrievePhoto().execute("http://graph.facebook.com/picture?type=normal");
                        //                imageParser mImageParser = new imageParser();
                        //                mImageParser.execute();

                        //                userImage.setImageBitmap();

                        name.setText(user.getName());
                        id.setText(user.getId());
                        url.setText(user.getLink());
                        user_profile_dailog.show();
                    } catch (Exception e) {
                        Log.e("Error :", "" + e);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.update_status:
                if (Session.getActiveSession().isOpened()) {
                    share_dialog = new Dialog(getApplicationContext());
                    share_dialog.setContentView(R.layout.fragment_share);
                    share_dialog.setTitle("Share Article");
                    etShare = (EditText) share_dialog.findViewById(R.id.textShare);
                    shareBtn = (Button) share_dialog.findViewById(R.id.btnShare);

                    shareBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            postStatusMessage(etShare.getText().toString());
                            share_dialog.cancel();
                        }
                    });
                    share_dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btn_timeline:
                if (Session.getActiveSession().isOpened()) {
                    Session ss = Session.getActiveSession();
                  /*test parsing timeline 7Langit*/
                    new Request(
                            ss,
                            "/7Langit",
                            null,
                            HttpMethod.GET,
                            new Request.Callback() {
                                @Override
                                public void onCompleted(Response response) {
                                    /*menampilkan apa yang di dapat*/
                                    Log.d("RESPONSE GRAPH OBJECT", "" + response.getGraphObject());
                                    Log.d("RESPONSE ", "" + response);
                                }
                            }
                    ).executeAsync();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Sign In", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_photo:
                break;
        }
    }
}
