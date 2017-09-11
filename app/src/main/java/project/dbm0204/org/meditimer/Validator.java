package project.dbm0204.org.meditimer;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;


/**
 * Created by bmathew2014 on 6/26/2015.
 *
 * class to test if the data the user inputs is the correct format or
 * present.
 *
 */
public class Validator extends ActionBarActivity {
    public Validator() {

    }
    public boolean isValid() {
        EditText patient = (EditText) findViewById(R.id.EditTextName);
        EditText medicine = (EditText) findViewById(R.id.medNameText);
        EditText dosage = (EditText) findViewById(R.id.editTextDosage);
        EditText instructions = (EditText) findViewById(R.id.specialInstText);

        if (patient.getText().toString().length() == 0) {
            patient.setError("This field is required!");
            return false;
        } else if (medicine.getText().toString().length() == 0) {
            medicine.setError("This field is required!");
            return false;
        } else if (dosage.getText().toString().length() == 0) {
            dosage.setError("This field is required!");
            return false;
        } else if (instructions.getText().toString().length() == 0) {
            instructions.setError("This field is required!");
            return false;
        } else {
            return true;
        }
    }
}
