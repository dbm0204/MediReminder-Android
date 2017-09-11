package project.dbm0204.org.meditimer;
import android.net.Uri;

import java.util.Date;

/**
 * Created by bmathew2014 on 6/17/15.
 *
 * ModelAlarm holds information about the Alarm and it's reminder information
 */
public class ModelAlarm {

    private String patient;
    private String medicine;
    private String dosage;
    private String instructions;
    private long id;
    private int alarmHour;
    private int alarmMinutes;
    private int repeat;
    private Uri ringtone;
    public boolean isEnabled;


    /**
     * ModelAlarm Constructor that initializes it's instance variables.
     */
    public ModelAlarm() {
        this.patient = "";
        this.medicine = "";
        this.dosage = "";
        this.instructions = "";
        this.alarmHour = 24;
        this.alarmMinutes = 0;
        this.isEnabled = false;
        this.id = -1;
        this.repeat = 0;

    }

    public void setID(long id) {
        this.id = id;
    }

    /**
     * Gets the ModelAlarm's ID.
     * @return The ID
     */
    public long getID() {
        return this.id;
    }

    /**
     * Sets the patient's name.
     * @param patient   The patient's name
     */
    public void setPatient(String patient) {
        this.patient = patient;
    }

    /**
     * Gets the ModelAlarm's name
     * @return The patient's name
     */
    public String getPatient() {
        return this.patient;
    }

    /**
     * Sets the medicine's name.
     * @param medicine  The medicine's name
     */
    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    /**
     * Gets the ModelAlarm's medicine name
     * @return The name of the medicine
     */
    public String getMedicine() {
        return this.medicine;
    }

    /**
     * Sets the dosage amount.
     * @param dosage    The dosage amount
     */
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    /**
     * Gets the ModelAlarm's edicine name
     * @return The dosage
     */
    public String getDosage() {
        return this.dosage;
    }

    /**
     * Sets the special instructions.
     * @param instructions  The special instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Gets the ModelAlarm's medicine instructions
     * @return The instructions
     */
    public String getInstructions() {
        return this.instructions;
    }

    /**
     * Sets the alarm's hour 1-24.
     * @param alarmHour The alarms hour
     */
    public void setAlarmHour(int alarmHour) {
        if (alarmHour < 0 || alarmHour > 23) {
            throw new IllegalArgumentException("The Hour must be between 0 and 23");
        }
        this.alarmHour = alarmHour;
    }

    /**
     * Gets the ModelAlarm's alarm hour
     * @return The hour
     */
    public int getHours() {
        return this.alarmHour;
    }

    /**
     * Sets the alarm's minutes 0-59.
     * @param alarmMinutes the alarms minutes
     */
    public void setAlarmMinutes(int alarmMinutes) {
        if (alarmMinutes < 0 || alarmMinutes > 59) {
            throw new IllegalArgumentException("The minutes must be between 0 and 59");
        }
        this.alarmMinutes = alarmMinutes;
    }

    /**
     * Gets the ModelAlarm's alarm minutes
     * @return The minutes
     */
    public int getMinutes() {
        return this.alarmMinutes;
    }

    /**
     * Set's if the alarm is enabled.
     * @param isEnabled is the alarm enabled
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Returns if the ModelAlarm is enabled.
     * @return if the ModelAlarm is enabled.
     */
    public boolean getIsEnabled() {
        return isEnabled;
    }


    /**
     * Sets the ringtone.
     * @param ringtone  The ringtone
     */
    public void setRingtone(Uri ringtone) {
        this.ringtone = ringtone;
    }

    /**
     * Gets the ringtone.
     * @return  The ringtone
     */
    public Uri getRingtone() {
        return this.ringtone;
    }

    /**
     * Gets the hours that the alarm should wait to repeat
     * @return the hours till the alarm repeats again.
     */
    public int getRepeat() { return this.repeat; }

    public void setRepeat(int hours) {
        this.repeat = hours;
    }

    /**
     * Returns a String representation of the AlarmList information.
     * @return  The String representation of the reminder info
     */
    public String toStringReminderInfo() {
        return this.patient + " needs to take " + this.dosage + " of " + this.medicine
                + '\n' + "Instructions: " + this.instructions;
    }

    /**
     * Returns a String representation of the alarm info
     * @return String representation of the alarm info
     */
    public String toStringAlarmInfo() {
        return alarmHour + ":" + alarmMinutes + " " + isEnabled;
    }

    /**
     * Returns a String representation of the ModelAlarm Object
     * @return String representation of the ModelAlarm Object
     */
    @Override
    public  String toString() {
        return toStringAlarmInfo() + " " + toStringReminderInfo();
    }
}
