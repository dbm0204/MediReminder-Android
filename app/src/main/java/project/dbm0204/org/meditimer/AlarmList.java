package project.dbm0204.org.meditimer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

/**
 *    This class has list of reminder and contain option to add new reminder
 *    Created by bmathew2014.
 */


public class AlarmList extends ActionBarActivity {
    private static final String STATE_SELECTED_POSITION ="selected_navigation_drawer_position";
    private int currentSelectedPosition =0;
    private AlarmListAdapter mAdapter;
    private AlarmDBHelper helper = new AlarmDBHelper(this);
    public final static int SAVED = 1;
    public final static int SAVED_SETTINGS=2;
    private Context mContext;
    @BindView(R.id.navigationDrawerListViewWrapper) NavigationDrawerView mNavigationDrawerListViewWrapper;
    @BindView(R.id.linearDrawer) LinearLayout mLinearDrawerLayout;
    @BindView(R.id.drawerlayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.leftDrawerListView) ListView leftDrawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
    private List<NavigationDrawerItem> navigationItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        ButterKnife.bind(this);
        mTitle = mDrawerTitle = getTitle();
        getSupportActionBar().setIcon(R.drawable.ic_action_ab_transparent);
        Timber.tag("LifeCycles");
        Timber.d("Activity Created");
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentFrame, Fragment.instantiate(AlarmList.this, Fragments.ONE.getFragment()))
                    .commit();
            } else {
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
        }
        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_one),true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_two),true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_three),true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_about), R.drawable.ic_action_about, false));
        mNavigationDrawerListViewWrapper.replaceWith(navigationItems);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer,R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        selectItem(currentSelectedPosition);

        mContext = this;
        ListView lv = (ListView) findViewById(android.R.id.list);

        //Set empty listView with message
        View empty = findViewById(R.id.empty);
        lv.setEmptyView(empty);

        mAdapter = new AlarmListAdapter(mContext, helper.getAlarms());
        lv.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm_list, menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_reminder: {
                Intent intent = new Intent(this, AlarmCreator.class);
                startActivityForResult(intent, SAVED);
                break;
            }

            case R.id.action_settings:
                Intent intent = new Intent(this, AlarmSettings.class);
                startActivityForResult(intent,SAVED_SETTINGS);
                return true;
        }
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        } else if(item.getItemId()==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  This method called when an activity is at the top of the activity stack.
     */
    @Override
    protected void onResume() {
        super.onResume();
            List<ModelAlarm> alarms = helper.getAlarms();
            mAdapter.setAlarms(alarms);
            mAdapter.notifyDataSetChanged();
    }

    /**
     * This method will start new activity
     * on item click on listview
     * @param id id of the selected alarm
     */
    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, AlarmDetails.class);
        intent.putExtra("id", id);
        ((AlarmList) mContext).startActivityForResult(intent, 0);
    }

    /**
     *This method will update the state of alarm in database
     * @param id id of item in list
     * @param isEnable state of alarm toggle button true or false
     */

    public void setAlarmEnable(long id, boolean isEnable) {
        AlarmManagerHelper.cancelAlarms(this);

        ModelAlarm model = helper.getAlarm(id);
        model.isEnabled = isEnable;
        helper.updateAlarm(model);

        AlarmManagerHelper.setAlarms(this);
    }

    public void onSavedInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }
    @Override
    public void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

   @OnItemClick(R.id.leftDrawerListView) public void OnItemClick(int position,long id){
       if(mDrawerLayout.isDrawerOpen(mDrawerLayout)){
           mDrawerLayout.closeDrawer(mLinearDrawerLayout);
           onNavigationDrawerItemSelected(position);
           selectItem(position);
       }
   }
   private void selectItem(int position){
       if(leftDrawerListView!=null){
           leftDrawerListView.setItemChecked(position,true);
           navigationItems.get(currentSelectedPosition).setSelected(false);
           navigationItems.get(position).setSelected(true);
           currentSelectedPosition=position;
           getSupportActionBar().setTitle(navigationItems.get(currentSelectedPosition).getItemName());
       }
       if(mLinearDrawerLayout!=null){
           mDrawerLayout.closeDrawer(mLinearDrawerLayout);
       }
   }
   @SuppressLint("RestrictedApi")
    private void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentOne)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame,
                                    Fragment.instantiate(AlarmList.this, Fragments.ONE.getFragment()))
                            .commit();
                }
                break;
            case 1:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentTwo)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame,Fragment.instantiate(AlarmList.this, Fragments.TWO.getFragment()))
                            .commit();
                }
                break;
            case 2:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentThree)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame,Fragment.instantiate(AlarmList.this, Fragments.THREE.getFragment()))
                            .commit();
                }
                break;
            case 3:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof FragmentAbout)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame,Fragment.instantiate(AlarmList.this, Fragments.ABOUT.getFragment()))
                            .commit();
                }
                break;
        }
    }
}
