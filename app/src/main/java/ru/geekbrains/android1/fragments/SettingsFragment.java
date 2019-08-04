package ru.geekbrains.android1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public class SettingsFragment extends Fragment {
    private SettingsPresenter settingsPresenter;

    private CheckBox humidityCB;
    private CheckBox pressureCB;
    private CheckBox windCB;
    private Spinner tempUnitSp;
    private Button doneBtn;

    private OnDoneListener onDoneListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsPresenter = SettingsPresenter.getInstance();
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setViews();
        setListeners();
    }

    private void initViews(View view) {
        humidityCB = view.findViewById(R.id.cb_humidity);
        pressureCB = view.findViewById(R.id.cb_pressure);
        windCB = view.findViewById(R.id.cb_wind);

        tempUnitSp = view.findViewById(R.id.sp_temp_unit);
        doneBtn = view.findViewById(R.id.btn_settings_done);
    }

    private void setViews() {
        humidityCB.setChecked(settingsPresenter.isHumidityChecked());
        pressureCB.setChecked(settingsPresenter.isPressureChecked());
        windCB.setChecked(settingsPresenter.isWindChecked());

        tempUnitSp.setSelection(settingsPresenter.getTempUnitIndex());
    }

    private void setListeners() {
        humidityCB.setOnCheckedChangeListener((btnView, isChecked) ->
                settingsPresenter.setHumidityChecked(isChecked));
        pressureCB.setOnCheckedChangeListener((btnView, isChecked) ->
                settingsPresenter.setPressureChecked(isChecked));
        windCB.setOnCheckedChangeListener((btnView, isChecked) ->
                settingsPresenter.setWindChecked(isChecked));

        tempUnitSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settingsPresenter.setTempUnitIndex(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                tempUnitSp.setSelection(settingsPresenter.getTempUnitIndex());
            }
        });

        doneBtn.setOnClickListener(v -> {
            if (onDoneListener != null) {
                onDoneListener.onDone();
            }
        });

    }

    public void setOnDoneListener(OnDoneListener onDoneListener) {
        this.onDoneListener = onDoneListener;
    }

    public interface OnDoneListener {
        void onDone();
    }
}
