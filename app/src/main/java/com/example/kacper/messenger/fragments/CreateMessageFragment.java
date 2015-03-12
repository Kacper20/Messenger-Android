package com.example.kacper.messenger.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.kacper.messenger.*;
import com.example.kacper.messenger.NetworkTasks.SendMessageTask;
import com.example.kacper.messenger.dialogs.DatePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CreateMessageFragment extends Fragment {
    private Date sendDate = null;



    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0xff;
    private static final int REQUEST_TIME = 0xfe;
    private static final int REQUEST_CHOICE = 0xfd;


    private EditText recipientField;
    private EditText contentField;
    private Button dateField;
    public CreateMessageFragment() {
        setHasOptionsMenu(true);
        sendDate = new Date();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_message_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_send_message){
            String recipient = recipientField.getText().toString();
            String content = contentField.getText().toString();
            SendMessageTask task = new SendMessageTask(getActivity(), recipient, sendDate, content);
            task.execute();
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_create_message, container, false);

        dateField = (Button) rootView.findViewById(R.id.delivery_date_button);
        recipientField = (EditText) rootView.findViewById(R.id.message_recipient_name);
        contentField = (EditText) rootView.findViewById(R.id.message_content);
        dateField.setText("Click to set delivery date");
        dateField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDateTimeDialog();
            }
        });
        return rootView;
    }

    private void editDateTimeDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        com.example.kacper.messenger.ChoiceDialogFragment dialogFragment = new com.example.kacper.messenger.ChoiceDialogFragment();
        dialogFragment.setTargetFragment(CreateMessageFragment.this, REQUEST_CHOICE);
        dialogFragment.show(fm, null);
    }
    private void showDialog(){
        DatePickerFragment dialog =  DatePickerFragment.newInstance(new Date());
        dialog.setTargetFragment(this, REQUEST_DATE);
        dialog.show(getActivity().getSupportFragmentManager(), DIALOG_DATE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            combineDate(date);
            updateDate();
        }
        if (requestCode == REQUEST_TIME) {
            Date date = (Date) data.getSerializableExtra(com.example.kacper.messenger.TimePickerFragment.EXTRA_TIME);
            combineTime(date);
            updateDate();
        }

        if (requestCode == REQUEST_CHOICE) {
            int choice = data.getIntExtra(com.example.kacper.messenger.ChoiceDialogFragment.EXTRA_CHOICE, 0);
            if (choice == 0) {
                Log.d("choice dialog", "requested choice returned nothing");
                return;
            }
            if (choice == com.example.kacper.messenger.ChoiceDialogFragment.CHOICE_TIME)
                editTimeDialog();
            else if (choice == com.example.kacper.messenger.ChoiceDialogFragment.CHOICE_DATE)
                editDateDialog();
        }
    }

    private void editDateDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(sendDate);
        dialog.setTargetFragment(CreateMessageFragment.this, REQUEST_DATE);
        dialog.show(fm, null);
    }

    private void editTimeDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        com.example.kacper.messenger.TimePickerFragment dialog = com.example.kacper.messenger.TimePickerFragment.newInstance(sendDate);
        dialog.setTargetFragment(CreateMessageFragment.this, REQUEST_TIME);
        dialog.show(fm, null);
    }

    private void combineTime(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sendDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(time);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);
        Date finalD = new GregorianCalendar(year, month, day, hours, mins).getTime();
        sendDate = finalD;
    }

    private void combineDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(sendDate);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        Date finalD = new GregorianCalendar(year, month, day, hours, mins).getTime();
        sendDate = finalD;
    }
    private void updateDate() {
        dateField.setText(sendDate.toString());
    }
}





