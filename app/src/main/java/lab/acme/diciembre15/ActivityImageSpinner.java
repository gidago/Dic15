package lab.acme.diciembre15;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import lab.acme.diciembre15.fragments.ChooseActivityTypeDialogFragment;
import lab.acme.diciembre15.fragments.ChooseActivityTypeDialogFragment.ChooseActivityTypeCaller;
import lab.acme.diciembre15.utils.TrackIconUtils;

public class ActivityImageSpinner extends AppCompatActivity implements ChooseActivityTypeCaller{

    private EditText mCategory;

    private Spinner activityTypeIcon;
    public static final String DEFAULT_ACTIVITY_TYPE = "TestActivity";
    public static String activityType = DEFAULT_ACTIVITY_TYPE;

    private String iconValue;

    /**
     * Activity types.
     */
    public enum ActivityType {
        CYCLING, RUNNING, WALKING, INVALID
    }

    /**
     * Gets the activity type.
     *
     * @param context the context
     * @param activityType the activity type
     */
    public static ActivityType getActivityType(Context context, String activityType) {
        if (activityType == null || activityType.equals("")) {
            return ActivityType.INVALID;
        }
        if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.WALK)) {
            return ActivityType.WALKING;
        } else if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.RUN)) {
            return ActivityType.RUNNING;
        } else if (TrackIconUtils.getIconValue(context, activityType).equals(TrackIconUtils.BIKE)) {
            return ActivityType.CYCLING;
        }
        return ActivityType.INVALID;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_image_spinner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategory = (EditText) findViewById(R.id.edit_text_category);
/**
        Spinner spinner = (Spinner) findViewById(R.id.track_edit_activity_type_icon);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Spinner item selection Listener
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
*/


        activityTypeIcon = (Spinner) findViewById(R.id.track_edit_activity_type_icon);
        // activityTypeIcon.setAdapter(TrackIconUtils.getIconSpinnerAdapter(this, iconValue));
        activityTypeIcon.setAdapter(TrackIconUtils.getIconSpinnerAdapter(this, "AIRPLANE"));
        activityTypeIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                   // ChooseActivityTypeDialogFragment.newInstance(activityType.getText().toString()).show(
                    ChooseActivityTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseActivityTypeDialogFragment.CHOOSE_ACTIVITY_TYPE_DIALOG_TAG);
                }
                return true;
            }
        });
        activityTypeIcon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    ChooseActivityTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseActivityTypeDialogFragment.CHOOSE_ACTIVITY_TYPE_DIALOG_TAG);
                }
                return true;
            }
        });


    }

    /**
     *  Set category from spinner
     */
    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            Snackbar.make(view, "Item: " + parent.getItemAtPosition(pos).toString() , Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mCategory.setText(parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            // Another interface callback
        }
    }

    private void setActivityTypeIcon(String value) {
        iconValue = value;
        TrackIconUtils.setIconSpinner(activityTypeIcon, value);
    }

    @Override
    public void onChooseActivityTypeDone(String value, boolean hasNewWeight) {
        /*if (!newWeight) {
            newWeight = hasNewWeight;
        }*/
        setActivityTypeIcon(value);
        mCategory.setText(getString(TrackIconUtils.getIconActivityType(value)));
    }


}
