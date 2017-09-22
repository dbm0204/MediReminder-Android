package project.dbm0204.org.meditimer;

import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;

/**
 * Created by dbm0204 on 9/22/17.
 */

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

    private TextView tv;

    public FingerprintHandler(TextView tv) {
        this.tv = tv;
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString){
        super.onAuthenticationError(errorCode,errString);
        tv.setText("Auth error");
    }
    @Override
    public void onAuthenticationHelp(int helpCode,CharSequence helpString){
        super.onAuthenticationHelp(helpCode,helpString);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result){
        super.onAuthenticationSucceeded(result);
        tv.setText("Auth ok!");
        tv.setTextColor(tv.getContext().getResources().getColor(android.R.color.black));
    }

    @Override
    public void onAuthenticationFailed(){
        super.onAuthenticationFailed();
    }
    public void doAuth(FingerprintManager manager, FingerprintManager.CryptoObject obj){
        CancellationSignal signal= new CancellationSignal();
        try{
            manager.authenticate(obj,signal,0,this,null);
        } catch (SecurityException sec){
            sec.printStackTrace();
        }
    }
}
