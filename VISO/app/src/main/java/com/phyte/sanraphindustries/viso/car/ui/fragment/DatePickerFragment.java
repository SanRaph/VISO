package com.phyte.sanraphindustries.viso.car.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.appeaser.sublimepickerlibrary.SublimePicker;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import java.util.Calendar;

/**
 *Application Name: CarAssistant
 * Package Name: com.classic.car.ui.fragment
 *
 * File Description: Date Selection
 * Created by: Continued to write classic
 * Created: 16/6/26 5:51 PM
 */
public class DatePickerFragment extends DialogFragment {
    private SublimePicker mSublimePicker;
    private Callback      mCallback;

    public static DatePickerFragment newInstance() {
        return new DatePickerFragment();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSublimePicker = (SublimePicker) inflater.inflate(com.appeaser.sublimepickerlibrary.R.layout.sublime_picker,
                container, false);
        initSublimePicker();
        return mSublimePicker;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    private void initSublimePicker() {
        SublimeOptions options = new SublimeOptions();
        options.setDisplayOptions(SublimeOptions.ACTIVATE_DATE_PICKER);
        options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
        //final Calendar calendar = Calendar.getInstance();
        //options.setTimeParams(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        mSublimePicker.initializePicker(options, new SublimeListenerAdapter() {
            @Override
            public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker, SelectedDate selectedDate,
                                                int hourOfDay, int minute,
                                                SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                                String recurrenceRule) {
                if (null != mCallback && null != selectedDate && null != selectedDate.getFirstDate()) {
                    mCallback.onFinish(selectedDate.getFirstDate());
                }
                dismiss();
            }

            @Override public void onCancelled() {
                if (null != mCallback) {
                    mCallback.onCancel();
                }
                dismiss();
            }
        });
    }

    @Override public void show(FragmentManager manager, String tag) {
        try{
            super.show(manager, tag);
        }catch (Exception e){
            e.printStackTrace();
            if(isAdded()){
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.remove(this);
                show(transaction, tag);
            }
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {
        void onCancel();

        void onFinish(Calendar calendar);
    }
}
