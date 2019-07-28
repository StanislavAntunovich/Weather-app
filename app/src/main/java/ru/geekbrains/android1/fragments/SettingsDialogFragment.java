package ru.geekbrains.android1.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import ru.geekbrains.android1.R;
import ru.geekbrains.android1.presenters.SettingsPresenter;

public class SettingsDialogFragment extends BottomSheetDialogFragment {
    private SettingsPresenter presenter;
    private StartActivityListener startActivityListener;

    private DialogInterface.OnDismissListener onDismissListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = SettingsPresenter.getInstance();
        View v = inflater.inflate(R.layout.bottom_sheet_settings, container, false);
        setViews(v);
        return v;
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    private void setViews(View view) {
        MaterialButton button = view.findViewById(R.id.settings_cities);

        CheckBox cbHumidity = view.findViewById(R.id.cb_humidity);
        CheckBox cbPressure = view.findViewById(R.id.cb_pressure);
        CheckBox cbWind = view.findViewById(R.id.cb_wind);

        cbHumidity.setChecked(presenter.isHumidityChecked());
        cbPressure.setChecked(presenter.isPressureChecked());
        cbWind.setChecked(presenter.isWindChecked());

        cbHumidity.setOnCheckedChangeListener(this::onCheckChanged);
        cbPressure.setOnCheckedChangeListener(this::onCheckChanged);
        cbWind.setOnCheckedChangeListener(this::onCheckChanged);
        button.setOnClickListener(v -> {
            startActivityListener.startActivity();
            dismiss();
        });
    }

    private void onCheckChanged(CompoundButton view, boolean isChecked) {
        int id = view.getId();
        switch (id) {
            case R.id.cb_humidity:
                presenter.setHumidityChecked(isChecked);
                break;
            case R.id.cb_pressure:
                presenter.setPressureChecked(isChecked);
                break;
            case R.id.cb_wind:
                presenter.setWindChecked(isChecked);
                break;
        }

    }


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setStartActivityListener(StartActivityListener listener) {
        this.startActivityListener = listener;
    }

    public interface StartActivityListener {
        void startActivity();
    }
}
