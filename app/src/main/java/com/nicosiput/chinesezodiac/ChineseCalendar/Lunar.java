package com.nicosiput.chinesezodiac.ChineseCalendar;

public class Lunar {
    public boolean isleap;
    public int lunarDay;
    public int lunarMonth;
    public int lunarYear;

    @Override
    public String toString() {
        return "Lunar{" +
                "isleap=" + isleap +
                ", lunarDay=" + lunarDay +
                ", lunarMonth=" + lunarMonth +
                ", lunarYear=" + lunarYear +
                '}';
    }
}