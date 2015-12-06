package lab.acme.diciembre15;

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

import lab.acme.diciembre15.fragments.ChooseCategoryTypeDialogFragment;
import lab.acme.diciembre15.fragments.ChooseCategoryTypeDialogFragment.ChooseCategoryTypeCaller;
import lab.acme.diciembre15.utils.CategoryIconUtils;

public class ActivityCategoryImageSpinner extends AppCompatActivity implements ChooseCategoryTypeCaller {

    private EditText mCategory;
    private Spinner mCategoryTypeIcon;
    private String iconValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iconValue = "";

        setContentView(R.layout.activity_category_selector);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        mCategoryTypeIcon = (Spinner) findViewById(R.id.spinner_category_type_icon);
        mCategoryTypeIcon.setAdapter(CategoryIconUtils.getIconSpinnerAdapter(this, iconValue));
        mCategoryTypeIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                   // ChooseCategoryTypeDialogFragment.newInstance(activityType.getText().toString()).show(
                    ChooseCategoryTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseCategoryTypeDialogFragment.CHOOSE_CATEGORY_TYPE_DIALOG_TAG);
                }
                return true;
            }
        });
        mCategoryTypeIcon.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    ChooseCategoryTypeDialogFragment.newInstance(mCategory.getText().toString()).show(
                            getSupportFragmentManager(),
                            ChooseCategoryTypeDialogFragment.CHOOSE_CATEGORY_TYPE_DIALOG_TAG);
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

    private void setmCategoryTypeIcon(String value) {
        iconValue = value;
        CategoryIconUtils.setIconSpinner(mCategoryTypeIcon, value);
    }

    @Override
    public void onChooseCategoryTypeDone(String value) {
        setmCategoryTypeIcon(value);
        mCategory.setText(getString(CategoryIconUtils.getIconActivityType(value)));
    }


}
