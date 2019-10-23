package com.nicosiput.chinesezodiac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicosiput.chinesezodiac.ChineseCalendar.Lunar;
import com.nicosiput.chinesezodiac.ChineseCalendar.LunarSolarConverter;
import com.nicosiput.chinesezodiac.ChineseCalendar.Solar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    Button btnFindZodiacByYear, btnConverterDate;
    TextView tvDateYesterday, tvDateToday, tvDateTomorrow, tvDesc;
    ImageView ivZodiac;


    FindZodiacActivity zodiac = new FindZodiacActivity();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDateYesterday = (TextView) findViewById(R.id.tv_dateYesterday);
        tvDateToday = (TextView) findViewById(R.id.tv_dateToday);
        tvDateTomorrow = (TextView) findViewById(R.id.tv_dateTomorrow);
        ivZodiac = (ImageView) findViewById(R.id.iv_zodiac);
        tvDesc = (TextView) findViewById(R.id.tv_desc);
        btnFindZodiacByYear = (Button) findViewById(R.id.btn_findZodiacByYear);
        btnConverterDate = (Button) findViewById(R.id.btn_converterDate);

        btnFindZodiacByYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), FindZodiacActivity.class);
                startActivity(in);
            }
        });

        btnConverterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), ConverterActivity.class);
                startActivity(in);
            }
        });
        setLunarSolar();
        getThisYearZodiac();
    }

    private void getThisYearZodiac() {
        Date nowdate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowdate);

        int thisYear = cal.get(Calendar.YEAR);
        thisYear = thisYear - 1900;

        int numZodiac = zodiac.getNumZodiac(thisYear);
        String yinyang = zodiac.getYinYang(numZodiac);
        int numElement = zodiac.getNumElement(thisYear);

        setYearZodiac(numZodiac, yinyang, numElement);
    }

    private void setYearZodiac(int numZodiac, String yinyang, int numElement) {
        String nameZodiac = zodiac.getNameZodiac(numZodiac);
        String nameElement = zodiac.getNameElement(numElement);
        int pictZodiac = zodiac.getPictZodiac(numZodiac);
        String text = "";
        text += nameZodiac + " - " + yinyang + " - " + nameElement;
        tvDesc.setText(text);
        ivZodiac.setImageResource(pictZodiac);
    }

    private void setLunarSolar() {
        LunarSolarConverter lsc = new LunarSolarConverter();
        Lunar lunarYesterday = new Lunar(); // china
        Lunar lunarToday = new Lunar(); // china
        Lunar lunarTomorrow = new Lunar(); // china
        Solar solar = new Solar(); // national
        Date nowdate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(nowdate);

        // set Date Yesterday
        cal.add(Calendar.DATE, -1);
        cal.getTime();
        Log.d("YESTERDAY", cal.getTime().toString());
        solar = setSolar(cal);
        lunarYesterday = lsc.SolarToLunar(solar);

        // set Date Today
        cal.add(Calendar.DATE, 1); // yesterday + 1
        cal.getTime();
        Log.d("TODAY", cal.getTime().toString());
        solar = setSolar(cal);
        lunarToday = lsc.SolarToLunar(solar);

        // set Date Tomorrow
        cal.add(Calendar.DATE, 1); // Today + 1
        cal.getTime();
        Log.d("TOMORROW", cal.getTime().toString());
        solar = setSolar(cal);
        lunarTomorrow = lsc.SolarToLunar(solar);

        String dateYesterday = setDateLunar(lunarYesterday);
        String dateToday = setDateLunar(lunarToday);
        String dateTomorrow = setDateLunar(lunarTomorrow);

        setDateText(dateYesterday, dateToday, dateTomorrow);
    }

    private void setDateText(String dateYesterday, String dateToday, String dateTomorrow) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMM yyyy");
            Date yesterday = new SimpleDateFormat("dd/MM/yyyy").parse(dateYesterday);
            Date today = new SimpleDateFormat("dd/MM/yyyy").parse(dateToday);
            Date tomorrow = new SimpleDateFormat("dd/MM/yyyy").parse(dateTomorrow);

            tvDateYesterday.setText(sdf.format(yesterday));
            tvDateToday.setText(sdf.format(today));
            tvDateTomorrow.setText(sdf.format(tomorrow));
        }
        catch (Exception e)
        {
            Log.e("setDate[MAIN]", e.getLocalizedMessage() );
        }

    }

    private String setDateLunar(Lunar object) {
        String result = "";
        try {
            int date = object.lunarDay;
            int month = object.lunarMonth;
            int year = object.lunarYear;

            result = String.valueOf(date) + "/" + String.valueOf(month) + "/" + String.valueOf(year);

        } catch (Exception e) {
            Log.d("setDateLunar", "setDateLunar: " + e.getLocalizedMessage());
        }

        return result;
    }

    private Solar setSolar(Calendar date) {
        Solar solar = new Solar();
        solar.solarDay = date.get(Calendar.DATE);
        solar.solarMonth = date.get(Calendar.MONTH) + 1;
        solar.solarYear = date.get(Calendar.YEAR);
        return solar;
    }
}
