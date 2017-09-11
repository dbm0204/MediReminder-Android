package project.dbm0204.org.meditimer;

import android.test.InstrumentationTestCase;


/**
 * Created by bmathew2014 on 6/19/15.
 *
 * Tests ModelAlarm's methods
 */
public class ModelAlarmTest extends InstrumentationTestCase {

    /**
     * Tests when Sam is set for the patient name that Same is returned.
     * @throws Exception
     */
    public void testSetGetPatientSamReturnsSam() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setPatient("Sam");
        assertEquals("Sam", alarm.getPatient());
    }

    /**
     * Tests when Med is set for the medicine name that Med is returned.
     * @throws Exception
     */
    public void testSetGetMedicineMedReturnsMed() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setMedicine("Med");
        assertEquals("Med", alarm.getMedicine());
    }

    /**
     * Tests when 10 is set for the dosage that 10 is returned.
     * @throws Exception
     */
    public void testSetGetDosage10Returns10() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setDosage("10");
        assertEquals("10", alarm.getDosage());
    }

    /**
     * Tests when 1 is set for the Alarm's hour that 1 is returned.
     * @throws Exception
     */
    public void testSetGetHour1Returns1() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setAlarmHour(10);
        assertEquals(10, alarm.getHours());
    }

    /**
     * Tests when Take Now is set for the instructions that Test Now is returned.
     * @throws Exception
     */
    public void testSetGetInstructionsTakeNowReturnsTakeNow() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setInstructions("Take Now");
        assertEquals("Take Now", alarm.getInstructions());
    }

    /**
     * Tests when 59 is set for that minutes that 59 is returned.
     * @throws Exception
     */
    public void testSetGetAlarmMinutes59Equals59() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setAlarmMinutes(59);
        assertEquals(59, alarm.getMinutes());
    }

    /**
     * Tests when isEnabled is set to true, true is returned when getIsEnabled is called.
     * @throws Exception
     */
    public void testSetGetIsEnabledTrueReturnsTrue() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setEnabled(true);
        assertEquals(true, alarm.getIsEnabled());
    }

    /**
     * Tests when repeat is set to 1 that when getRepeat is called it returns 1
     * @throws Exception
     */
    public void testSetGetRepeat1Equals1() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setRepeat(1);
        assertEquals(1, alarm.getRepeat());
    }

    /**
     * Tests the toStringReminderInfo() method.
     * @throws Exception
     */
    public void testToStringReminderInfo() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setPatient("Nick");
        alarm.setMedicine("Prozac");
        alarm.setDosage("1 pill");
        alarm.setInstructions("With food");
        assertEquals("Nick needs to take 1 pill of Prozac\nInstructions: With food", alarm.toStringReminderInfo());
    }

    /**
     * Tests the toStringAlarmInfo() method using 12:24 and disabling the alarm.
     * @throws Exception
     */
    public void testToStringAlarmInfo1224NotEnabled() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setAlarmHour(12);
        alarm.setAlarmMinutes(24);
        alarm.setEnabled(false);
        assertEquals("12:24 false", alarm.toStringAlarmInfo());
    }

    /**
     * Tests the toString() method.
     * @throws Exception
     */
    public void testToString() throws Exception {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setPatient("Nick");
        alarm.setMedicine("Prozac");
        alarm.setDosage("1 pill");
        alarm.setInstructions("With food");
        alarm.setAlarmHour(12);
        alarm.setAlarmMinutes(24);
        alarm.setEnabled(true);
        assertEquals("12:24 true Nick needs to take 1 pill of Prozac\nInstructions: With food", alarm.toString());
    }
}
