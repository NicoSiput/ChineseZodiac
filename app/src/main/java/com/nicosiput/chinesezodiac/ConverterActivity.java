package com.nicosiput.chinesezodiac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import com.nicosiput.chinesezodiac.ChineseCalendar.Lunar;
import com.nicosiput.chinesezodiac.ChineseCalendar.LunarSolarConverter;
import com.nicosiput.chinesezodiac.ChineseCalendar.Solar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConverterActivity extends AppCompatActivity {

    DatePicker dpLunarNational, dpNationalLunar;
    TextView tvResultLunar, tvResultNational;
    Lunar lunar;
    Solar solar;
    LunarSolarConverter lsc;

    SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        dpLunarNational = (DatePicker) findViewById(R.id.dp_lunarNational);
        dpNationalLunar = (DatePicker) findViewById(R.id.dp_nationalLunar);
        tvResultLunar = (TextView) findViewById(R.id.tv_resultLunar);
        tvResultNational = (TextView) findViewById(R.id.tv_resultNational);

        lunar = new Lunar();
        solar = new Solar();
        lsc = new LunarSolarConverter();

        dpLunarNational.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    lunar.lunarDay = dayOfMonth;
                    lunar.lunarMonth = monthOfYear + 1;
                    lunar.lunarYear = year;

                    String result = "";
                    solar = lsc.LunarToSolar(lunar);
                    result = solar.solarDay + "/" + solar.solarMonth + "/" + solar.solarYear;
                    Date lunarDate = new SimpleDateFormat("dd/MM/yyyy").parse(result);

                    tvResultNational.setText("National : " + sdf.format(lunarDate));
                } catch (Exception e) {
                    Log.e("LunarChangeListen", "onDateChanged: " + e.getLocalizedMessage());
                }
            }
        });

        dpNationalLunar.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                try {
                    solar.solarDay = dayOfMonth;
                    solar.solarMonth = monthOfYear + 1;
                    solar.solarYear = year;

                    String result = "";
                    lunar = lsc.SolarToLunar(solar);
                    result = lunar.lunarDay + "/" + lunar.lunarMonth + "/" + lunar.lunarYear;
                    Date nationalDate = new SimpleDateFormat("dd/MM/yyyy").parse(result);

                    tvResultLunar.setText("Lunar : " + sdf.format(nationalDate));
                } catch (Exception e) {
                    Log.e("NationalChangeListen", "onDateChanged: " + e.getLocalizedMessage());
                }
            }
        });
    }
}
