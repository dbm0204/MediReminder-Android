package project.dbm0204.org.meditimer;

import android.media.RingtoneManager;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;


/**
 * Created by bmathew2014 on 6/19/15.
 *
 * Tests the AlarmDBHelper methods.
 */
public class DBHelperTest extends AndroidTestCase {
    AlarmDBHelper helper;
    /**
     * Creates an AlarmDBHelper object which will be used throughout testing.
     * @throws Exception
     */
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "Test_");
        this.helper = new AlarmDBHelper(context);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Creates a template for the ModelAlarm.
     * @return  The ModelAlarm
     */
    private ModelAlarm getModel() {
        ModelAlarm alarm = new ModelAlarm();
        alarm.setPatient("Dude");
        alarm.setMedicine("Lovin");
        alarm.setDosage("1 Dose");
        alarm.setInstructions("With 2 drinks");
        alarm.setAlarmHour(12);
        alarm.setAlarmMinutes(34);
        alarm.setRepeat(6);
        alarm.setEnabled(true);
        alarm.setRingtone(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        return alarm;
    }

    /**
     * Tests the helpers createAlarm() method.
     */
    public void testCreateAlarm() {
        ModelAlarm alarm = this.getModel();
        long id = helper.createAlarm(alarm);
        ModelAlarm newAlarm = helper.getAlarm(id);
        assertEquals("Dude", newAlarm.getPatient());
        assertEquals("Lovin", newAlarm.getMedicine());
        assertEquals("1 Dose", newAlarm.getDosage());
        assertEquals("With 2 drinks", newAlarm.getInstructions());
        assertEquals(12, newAlarm.getHours());
        assertEquals(34, newAlarm.getMinutes());
        assertEquals(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE), alarm.getRingtone());
        assertTrue(newAlarm.getIsEnabled());
    }

    /**
     * Tests the helpers updateAlarm() method.
     */
    public void testUpdateAlarm() {
        ModelAlarm alarm = this.getModel();
        alarm.setPatient("Jerry");
        alarm.setDosage("Tons");
        alarm.setMedicine("Happiness");
        alarm.setInstructions("As much as possible.");
        alarm.setAlarmHour(11);
        alarm.setAlarmMinutes(11);
        alarm.setEnabled(false);
        long id = helper.createAlarm(alarm);
        ModelAlarm newAlarm = helper.getAlarm(id);
        assertEquals("Jerry", newAlarm.getPatient());
        assertEquals("Happiness", newAlarm.getMedicine());
        assertEquals("Tons", newAlarm.getDosage());
        assertEquals("As much as possible.", newAlarm.getInstructions());
        assertEquals(11, newAlarm.getHours());
        assertEquals(11, newAlarm.getMinutes());
        assertEquals(false, newAlarm.getIsEnabled());
    }

    /**
     * Tests the helpers deleteAlarm() method
     */
    public void testDeleteAlarm() {
        ModelAlarm alarm = this.getModel();
        long id = helper.createAlarm(alarm);
        int row = helper.deleteAlarm(id);
        assertTrue(row == 1);
        ModelAlarm newAlarm = helper.getAlarm(id);
        assertEquals(null, newAlarm);
    }
}
