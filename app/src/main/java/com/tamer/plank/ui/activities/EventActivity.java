package com.tamer.plank.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.tamer.plank.R;
import com.tamer.plank.model.CardLab;
import com.tamer.plank.model.EventCard;
import com.tamer.plank.ui.base.BaseActivity;
import com.tamer.plank.ui.fragment.EventListFragment;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by liangzr on 16-5-8.
 */
public class EventActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {


    private EventCard mEvent;
    private ArrayList<EventCard> mEvents;
    private DatePickerDialog mDatePickerDialog;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, EventActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_event);
        toolbar.inflateMenu(R.menu.menu_event);

        UUID eventId = (UUID) getIntent().getSerializableExtra(EventListFragment.EXTRA_EVENT_ID);
        mEvent = CardLab.getInstance().getEventCard(eventId);

        EditText editText = (EditText) findViewById(R.id.event_title);
        editText.setText(mEvent.getTitle());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvent.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        }, 2016, 5, 10);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_event_tips);

        if (fragment == null) {
            fragment = EventListFragment.newInstance(mEvent.getId());
            fm.beginTransaction().add(R.id.fragment_event_tips, fragment).commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_tip);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvent.getTips().add(null);
                }
            });
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notice:
                mDatePickerDialog.show();
                return true;
            case R.id.action_share:
                return true;
            default:
        }
        return true;
    }
}
