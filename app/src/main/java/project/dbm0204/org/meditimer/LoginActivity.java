package project.dbm0204.org.meditimer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import butterknife.ButterKnife;
import butterknife.BindView;
/**
 * Created by dbm0204 on 9/10/17.
 */
public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    private static final String TAG ="LoginActivity";
    private static final int REQUEST_SIGNUP=0;
    private static final int RC_SIGN_IN=9001;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private TextView mStatusTextView;

    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup) TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //Configure sign-in to request the user ID, email address and basic profile are included in DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this/*onConnectionFailedListener*/)
                .enableAutoManage(this /*FragmentActivity*/, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

    _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }
        public void login() {
            Log.d(TAG, "Login");

            if (!validate()) {
                onLoginFailed();
                return;
            }

            _loginButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.Theme_AppCompat_DayNight_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            // TODO: Implement your own authentication logic here.

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess();
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }



        @Override
        public void onBackPressed() {
            // disable going back to the MainActivity
            moveTaskToBack(true);
        }

        public void onLoginSuccess() {
            _loginButton.setEnabled(true);
            finish();
        }

        public void onLoginFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            _loginButton.setEnabled(true);
        }

        public boolean validate() {
            boolean valid = true;

            String email = _emailText.getText().toString();
            String password = _passwordText.getText().toString();

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _emailText.setError("enter a valid email address");
                valid = false;
            } else {
                _emailText.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            return valid;
        }

    @Override
    public void onStart(){
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if(opr.isDone()){
            // if the user's cached credentials are valid, tht optionalPendingResult will be "done"
            // this asychronous branch will attempy to sign in the user silently. Cross-device
            // single sign-on will occur in this branch
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    @Override
    protected void onResume(){
    super.onResume();
    hideProgressDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }

    }
    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG,"handleSignInResult:"+result.isSuccess());
        if(result.isSuccess()){
            //Signed in Sucessfullt show authenticated UI
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt,acct.getDisplayName()));
            updateUI(true);
        } else {
            updateUI(false);

        }
    }

    private void updateUI(boolean signedIn){
        if(signedIn){
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "Google Sign-In Sucessful", Toast.LENGTH_SHORT).show();
        }
    }
    private void signIn(){
        Intent signInIntent =Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);

    }
    private void signOut(){
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }
    private void revokeAccess(){
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(false);
                    }
                }
        );
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){
        Log.d(TAG,"onConnectionFailed:"+connectionResult);
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sign_in_button:
                signIn();

        }
    }
    private void hideProgressDialog(){
        if(mProgressDialog !=null && mProgressDialog.isShowing()){
            mProgressDialog.hide();

        }
    }
    private void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("loading");
            mProgressDialog.setIndeterminate(true);
        }
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
}
