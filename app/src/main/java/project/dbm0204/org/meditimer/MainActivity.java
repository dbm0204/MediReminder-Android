package project.dbm0204.org.meditimer;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


/**
 * @Author bmathew2014
 * No longer in use as main activity class is AlarmList class
 */

public class MainActivity extends ActionBarActivity {

    /**
     * This method initialize activity
     * @param savedInstanceState Bundle - most recently supplied data
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method Initialize the contents of the Activity's standard options menu
     * @param menu to place item
     * @return true if menu to be displayed
     */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method is call when Item from the menu is selected
     * @param item menu item that was selected
     * @return true
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       // int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
       //     return true;
        //}
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_reminder: {
                Intent intent = new Intent(this, AlarmCreator.class);
                startActivity(intent);
                break;
            }

        }

        return true;
    }

    /**
     * This method will start alarm creator activity
     * @param view view
     */
    public void addMedicine(View view) {
        Intent in = new Intent(getApplicationContext(),AlarmCreator.class);
        startActivity(in);
    }

}
