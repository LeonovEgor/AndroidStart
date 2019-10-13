package ru.leonov.a1l3_weather.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import ru.leonov.a1l3_weather.R;
import ru.leonov.a1l3_weather.WeatherDetailActivity;


public class SelectCityFragment extends Fragment {
    private static final String TAG = "WEATHER";
    private static final String CITY_VALUE_KEY = "cityKey";
    private static final String BACK_STACK_KEY = "backStackKey";

    private boolean isExistWeatherDetail;  // Можно ли расположить рядом фрагмент данными погоды
    private int currentPosition = 0;    // Текущая позиция (выбранный город)

    private ListView listView;
    private TextView emptyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, this.getClass().getName() + " - onCreateView");
        return inflater.inflate(R.layout.fragment_select_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(TAG, this.getClass().getName() + " - onViewCreated");

        initViews(view);
        initList();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.d(TAG, this.getClass().getName() + " - onActivityCreated");

        isExistWeatherDetail = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CITY_VALUE_KEY, 0);
        }

        if (isExistWeatherDetail) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeatherDetail();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, this.getClass().getName() + " - onSaveInstanceState");

        outState.putInt(CITY_VALUE_KEY, currentPosition);
        super.onSaveInstanceState(outState);
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    private void initList() {
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()), R.array.cities,
                        android.R.layout.simple_list_item_activated_1);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick");
                currentPosition = position;
                showSnackbar(listView);

            }
        });
    }

    // Задание: 3. Добавьте snackBar для подтверждения действий пользователя.
    private void showSnackbar(View view) {
        String city;
        try {
            String[] cities = getResources().getStringArray(R.array.cities);
            city = cities[currentPosition];
        }
        catch (Exception e) {
            Log.d(TAG, "Ошибка получения города из списка: " + e.getMessage());
            return;
        }

        Snackbar.make(view, city, Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showWeatherDetail();
                    }
                }).show();
    }

    private void showWeatherDetail() {
        if (isExistWeatherDetail) {
            listView.setItemChecked(currentPosition, true);
            WeatherDetailFragment detail = (WeatherDetailFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.weatherDetailContainer);

            if (detail == null || detail.getIndex() != currentPosition) {
                detail = WeatherDetailFragment.create(currentPosition);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.weatherDetailContainer, detail)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(BACK_STACK_KEY)
                        .commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherDetailActivity.class);
            intent.putExtra(CITY_VALUE_KEY, currentPosition);
            startActivity(intent);
        }
    }

    // для задания


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.d(TAG, this.getClass().getName() + " - onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, this.getClass().getName() + " - onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, this.getClass().getName() + " - onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, this.getClass().getName() + " - onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, this.getClass().getName() + " - onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, this.getClass().getName() + " - onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.d(TAG, this.getClass().getName() + " - onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.d(TAG, this.getClass().getName() + " - onDestroyView");
    }
}
