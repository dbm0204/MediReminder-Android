package project.dbm0204.org.meditimer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.style.TtsSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


/***
 * @author bmathew2014
 *
 * this class sets up a form for the user to input information about the patient, medicine,
 * the alarm frequencey, etc.
 */
public class AlarmCreator extends ActionBarActivity implements View.OnClickListener {

    private EditText timeEtxt;
    private int startHour;
    private int startMin;
    private TimePickerDialog timePickerDialog;
    private ModelAlarm alarmDetails;
    private AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    private SimpleDateFormat dateFormatter;
    private Date today;
    private EditText patient;
    private EditText medicine;
    private EditText dosage;
    private EditText instructions;
    private Spinner repeatTime;
    private CheckBox alarmOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_creator);

        alarmDetails = new ModelAlarm();
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        today = new Date();

        findViewsById();
        setDateTimeField();

        final LinearLayout ringToneContainer = (LinearLayout) findViewById(R.id.alarm_ringtone_container);
        ringToneContainer.requestFocus();
        ringToneContainer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                startActivityForResult(intent, 1);
            }
        });
    }

    //get the fields and input types
    private void findViewsById() {
        timeEtxt = (EditText) findViewById(R.id.startTime);
        timeEtxt.requestFocus();
    }

    /***
     * sets up the date and time pickers on the form. When the start date end date or time are selected
     * the appropriate picker will be displayed
     */
    private void setDateTimeField() {

        timeEtxt.setOnClickListener(this);


        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        if (hour == 0) {
            timeEtxt.setText(String.format("%02d:%02d", hour + 12, minute) + " am");
        } else if (hour < 10) {
            timeEtxt.setText(String.format("%01d:%02d", hour, minute) + " am");
        } else if (hour < 12) {
            timeEtxt.setText(String.format("%02d:%02d", hour, minute) + " am");
        } else if (hour == 12) {
            timeEtxt.setText(String.format("%02d:%02d", hour, minute) + " pm");
        } else if (hour > 12 && hour < 22) {
            timeEtxt.setText(String.format("%01d:%02d", hour - 12, minute) + " pm");
        } else {
            timeEtxt.setText(String.format("%02d:%02d", hour - 12, minute) + " pm");
        }
        this.startHour = hour;
        this.startMin = minute;
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minuteOfDay) {
                        // Display Selected time in textbox
                        if (hourOfDay == 0) {
                            timeEtxt.setText(String.format("%02d:%02d", hourOfDay + 12, minuteOfDay) + " am");
                        } else if (hourOfDay < 10) {
                            timeEtxt.setText(String.format("%01d:%02d", hourOfDay, minuteOfDay) + " am");
                        } else if (hourOfDay < 12) {
                            timeEtxt.setText(String.format("%02d:%02d", hourOfDay, minuteOfDay) + " am");
                        } else if (hourOfDay == 12) {
                            timeEtxt.setText(String.format("%02d:%02d", hourOfDay, minuteOfDay) + " pm");
                        } else if (hourOfDay > 12 && hourOfDay < 22) {
                            timeEtxt.setText(String.format("%01d:%02d", hourOfDay - 12, minuteOfDay) + " pm");
                        } else {
                            timeEtxt.setText(String.format("%02d:%02d", hourOfDay - 12, minuteOfDay) + " pm");
                        }
                        startHour = hourOfDay;
                        startMin = minuteOfDay;

                    }
                }, hour, minute, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_creator, menu);
        return super.onCreateOptionsMenu(menu);

    }


    /***
     * identifies which field was selected
     * @param view  The view
     */
    @Override
    public void onClick(View view) {
       timePickerDialog.show();
    }

    /***
     * gives function to the action bar save button
     * @param item  The MenuItem
     * @return the item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();

                break;
            }

            case R.id.action_save_reminder: {
                if (isValid()) {
                    updateModelFromLayout();


                    AlarmManagerHelper.cancelAlarms(this);

                    if (alarmDetails.getID() < 0) {
                        dbHelper.createAlarm(alarmDetails);
                    } else {
                        dbHelper.updateAlarm(alarmDetails);
                    }
                    AlarmManagerHelper.setAlarms(this);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /***
     * this method will get and store the values that the user input when
     * a new alarm is created
     */
    private void updateModelFromLayout() {
        //get the values that the user input
        getTextFields();

        setTextFields();
        //work around to set the hour and minutes as separate fields in the Alarm Model object.
        alarmDetails.setAlarmHour(startHour);
        alarmDetails.setAlarmMinutes(startMin);



    }

    /***
     * picking the alarm sound
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    Uri name = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    alarmDetails.setRingtone(name);

                    TextView txtToneSelection = (TextView) findViewById(R.id.alarm_label_tone_selection);
                    txtToneSelection.setText(RingtoneManager.getRingtone(this, alarmDetails.getRingtone()).getTitle(this));
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    /**
     * method to get the info from the user input screen
     *
     */
    private void getTextFields() {
        patient = (EditText) findViewById(R.id.EditTextName);
        medicine = (EditText) findViewById(R.id.medNameText);
        dosage = (EditText) findViewById(R.id.editTextDosage);
        instructions = (EditText) findViewById(R.id.specialInstText);
        alarmOn = (CheckBox) findViewById(R.id.CheckBoxResponse);
        repeatTime = (Spinner) findViewById(R.id.SpinnerFeedbackType);


    }

    /**
     * set the properities of the alarm model from the Text Fields
     */
    private void setTextFields() {
        alarmDetails.setPatient(patient.getText().toString());
        alarmDetails.setMedicine(medicine.getText().toString());
        alarmDetails.setDosage(dosage.getText().toString());
        alarmDetails.setInstructions(instructions.getText().toString());
        if(alarmOn.isChecked()){
            alarmDetails.setEnabled(true);
        }

        String repeat = repeatTime.getSelectedItem().toString();
        if (repeat.equalsIgnoreCase("4 hours")){
            alarmDetails.setRepeat(4);
        } else if (repeat.equalsIgnoreCase("8 hours")){
            alarmDetails.setRepeat(8);
        } else if (repeat.equalsIgnoreCase("12 hours")) {
            alarmDetails.setRepeat(12);
        } else {
            alarmDetails.setRepeat(0);
        }
    }

    /***
     * this method validates the text fields and dates on the user input form
     * @return true if all the data is correct false if one or more of the fields is not
     * correct
     */
    private boolean isValid(){
        getTextFields();


        if( patient.getText().toString().length() == 0 ){
            patient.setError( "This field is required!" );
            return false;
        } else if( medicine.getText().toString().length() == 0 ) {
            medicine.setError("This field is required!");
            return false;
        } else if( dosage.getText().toString().length() == 0 ) {
            dosage.setError("This field is required!");
            return false;
        } else if( instructions.getText().toString().length() == 0 ) {
            instructions.setError( "This field is required!" );
            return false;
        }
        return true;
    }

}