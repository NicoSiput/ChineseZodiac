package com.nicosiput.chinesezodiac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FindZodiacActivity extends AppCompatActivity {

    Button btnSearch;
    EditText etYear;
    TextView tvDesc;
    ImageView ivZodiac;

    String listZodiac[] = {"Rat", "Ox", "Tiger", "Rabbit",
            "Dragon", "Snake", "Horse", "Sheep",
            "Monkey", "Rooster", "Dog", "Pig"};

    int pictZodiac[] = {R.drawable.rat, R.drawable.ox, R.drawable.tiger, R.drawable.rabbit,
            R.drawable.dragon, R.drawable.snake, R.drawable.horse, R.drawable.sheep,
            R.drawable.monkey, R.drawable.rooster, R.drawable.dog, R.drawable.pig};

    String listElement[] = {"Metal", "Water", "Wood", "Fire", "Earth"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_zodiac);

        btnSearch = (Button) findViewById(R.id.btn_search);
        etYear = (EditText) findViewById(R.id.et_year);
        ivZodiac = (ImageView) findViewById(R.id.iv_zodiac);
        tvDesc = (TextView) findViewById(R.id.tv_desc);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        if(TextUtils.isEmpty(etYear.getText().toString())) {
            etYear.setError("Please Input Birth of Year");
            return;
        }
        int year = Integer.parseInt(etYear.getText().toString());
        if (year > 1899) {
            year = year - 1900;

            int numZodiac = getNumZodiac(year);
            String yinyang = getYinYang(numZodiac);
            int numElement = getNumElement(year);

            setData(numZodiac, yinyang, numElement);
        } else {
            etYear.setError("Year Must More Than 1900");
            return;
        }
    }

    private void setData(int numZodiac, String yinyang, int numElement) {
        String text = "";
        text += listZodiac[numZodiac] + " - " + yinyang + " - " + listElement[numElement];
        tvDesc.setText(text);
        ivZodiac.setImageResource(pictZodiac[numZodiac]);
    }

    public int getNumElement(int year) {
        return year % 10 / 2;
    }

    public String getYinYang(int numZodiac) {
        String yinyang = "[Yang (+)]";
        if ((numZodiac + 1) % 2 == 0) {
            yinyang = "[Yin (-)]";
        }

        return yinyang;
    }

    public int getNumZodiac(int year) {
        return year % 12;
    }

    public String getNameElement(int numElemet) {
        return listElement[numElemet];
    }

    public String getNameZodiac(int numZodiac) {
        return listZodiac[numZodiac];
    }

    public int getPictZodiac(int numZodiac) {
        return pictZodiac[numZodiac];
    }

}
