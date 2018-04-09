package com.example.cse5236.mobilebuddy;

import android.util.Log;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepScheduleHelper {

    public SleepScheduleHelper()
    {

    }


    public int[] CalculateTimeDifference(Date startTime, Date endTime)
    {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

        long difference = endTime.getTime() - startTime.getTime();
        if(difference<0)
        {
            try
            {
                Date dateMax = simpleDateFormat.parse("24:00");
                Date dateMin = simpleDateFormat.parse("00:00");
                difference=(dateMax.getTime() -startTime.getTime() )+(endTime.getTime()-dateMin.getTime());

            }
            catch (Exception e)
            {
                Log.d("sleeptimecalculation", "CalculateTimeDifference: Exception occurred");
            }

        }
        int hours = (int) ((difference / (1000*60*60)));
        int min = (int) (difference - (1000*60*60*hours)) / (1000*60);

        int[] retVal = new int[2];
        retVal[0] = hours;
        retVal[1] = min;

        return retVal;
    }

    public boolean CheckCurrentTimeBeforeCurfew(Time mCurfew, Date currentTime)
    {

        boolean timeIsBeforeCurfew = false;
        int minutesCurrentTime = currentTime.getHours() * 60 + currentTime.getMinutes();
        int minutesForCurfew = mCurfew.getHours() * 60 + mCurfew.getMinutes();

        if (minutesCurrentTime < minutesForCurfew)
        {
            timeIsBeforeCurfew = true;
        }

        return timeIsBeforeCurfew;

    }
}
