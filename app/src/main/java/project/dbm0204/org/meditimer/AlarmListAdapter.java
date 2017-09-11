package project.dbm0204.org.meditimer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

/**
 * Created by bmathew2014 on 6/20/2015.
 * This class will help in render list
 */
public class AlarmListAdapter extends BaseAdapter {

    private List<ModelAlarm> mAlarm;
    private Context context;

    /**
     * Construction with parameter
     * @param con context of current state of application
     * @param alarm alarm list
     */

    public AlarmListAdapter(Context con, List<ModelAlarm> alarm) {
        context = con;
        mAlarm = alarm;
    }

    /**
     * How many items are in the
     * data set represented by adapter
     *
     * @return size of list
     */

    @Override
    public int getCount() {
        if (mAlarm != null) {
            return mAlarm.size();
        }
        return 0;
    }

    /**
     * Get the data item associated with the specified position in the data set
     * @param position The position of the Alarm in the list
     * @return position of item in dataset
     *          if dataset != null
     *          else @ return null
     */
    @Override
    public Object getItem(int position) {
        if (mAlarm != null) {
            return mAlarm.get(position);
        }
        return null;
    }

    /**
     *
     * Get the row id associated with the specified position in the list.
     * @param position  The position of the Alarm in the list
     * @return          row id associated with the specified position in the list. ( if list is not null)
     *                  else @return 0
     */
    @Override
    public long getItemId(int position) {
        if (mAlarm != null) {
            return mAlarm.get(position).getID();
        }
        return 0;
    }

    /**
     * Sets the alarms
     * @param alarms    A list of ModelAlarms
     */
    public void setAlarms(List<ModelAlarm> alarms) {
        this.mAlarm = alarms;
    }

    /**
     * Get a View that displays the data at the specified position in the data set
     * @param position The position of the item
     * @param convertView view
     * @param parent parent eventually attached to
     * @return convertView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = lInflater.inflate(R.layout.activity_alarm_list_item, parent, false);
        }

        ModelAlarm model = (ModelAlarm) getItem(position);

        TextView tvTime = (TextView) convertView.findViewById(R.id.alarm_item_time);
        if (model.getHours() == 0) {
            tvTime.setText(String.format("%02d:%02d", model.getHours() + 12, model.getMinutes()) + " am");
        } else if (model.getHours() < 10) {
            tvTime.setText(String.format("%01d:%02d", model.getHours(), model.getMinutes()) + " am");
        } else if (model.getHours() < 12) {
            tvTime.setText(String.format("%02d:%02d", model.getHours(), model.getMinutes()) + " am");
        } else if (model.getHours() == 12) {
            tvTime.setText(String.format("%02d:%02d", model.getHours(), model.getMinutes()) + " pm");
        } else if (model.getHours() > 12 && model.getHours() < 22) {
            tvTime.setText(String.format("%01d:%02d", model.getHours() - 12, model.getMinutes()) + " pm");
        } else {
            tvTime.setText(String.format("%02d:%02d", model.getHours() - 12, model.getMinutes()) + " pm");
        }
        TextView tvReminderInfo = (TextView)convertView.findViewById(R.id.alarm_item_reminder);
        tvReminderInfo.setText(model.toStringReminderInfo());



        ToggleButton button = (ToggleButton) convertView.findViewById(R.id.toggleButton);

        button.setTag(Long.valueOf(model.getID()));
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((AlarmList) context).setAlarmEnable(((Long) buttonView.getTag()).longValue(), isChecked);
            }
        });
        button.setChecked(model.isEnabled);


        convertView.setTag(model.getID());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AlarmList) context).startAlarmDetailsActivity((Long) v.getTag());
            }
        });

        return convertView;
    }
}
