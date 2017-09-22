package project.dbm0204.org.meditimer;

import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyGenParameterSpec.Builder;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * Created by dbm0204 on 9/22/17.
 */

public class FingerPrintActivity extends AppCompatActivity {
    private static final String KEY_NAME="SwA";
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private TextView message;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        message = (TextView) findViewById(R.id.fingerprint);
        final FingerprintHandler fph = new FingerprintHandler(message);
        if (!checkFinger()) {
            // Set action
        } else {
            try {
                generateKey();
                Cipher cipher = generateCiper();
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
            } catch (FingerprintException fpe) {
                // Handle Exception
            }
        }
    }
    private boolean checkFinger() {
        //keyguard Manager
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        // Fingerprint Manager
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        try {
            //check if the fingerprint sensor is present
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(getApplicationContext(), "Fingerprint Authentication not supported", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(getApplicationContext(), "No Fingerprints Configured", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(getApplicationContext(), "Secure Lock Screen not Enabled", Toast.LENGTH_LONG).show();
                return false;
            }
        } catch (SecurityException sec) {
            sec.printStackTrace();
        }
        return true;
    }
    private void generateKey() throws FingerprintException {
        try {
            //Get the reference to the Key Store
            keyStore = KeyStore.getInstance("AndroidKeyStore");

            //Key generator to generate the key
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).build());
            keyGenerator.generateKey();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (CertificateException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        }
    }
    private Cipher generateCiper() throws FingerprintException{
        try{
            Cipher cipher =Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"
            +KeyProperties.BLOCK_MODE_CBC+"/"
            +KeyProperties.ENCRYPTION_PADDING_PKCS7);
            SecretKey key =(SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return cipher;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (KeyStoreException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new FingerprintException(e);
        }
    }
    private class FingerprintException extends Exception{
        public FingerprintException() {}
        public FingerprintException(Exception e) {
            super(e);
        }
    }

}
