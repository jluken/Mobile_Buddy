package com.example.cse5236.mobilebuddy;

import android.support.v7.app.AppCompatActivity;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Time;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SleepScheduleTest {

    SleepScheduleHelper helper = new SleepScheduleHelper();

    @Test
    public void TestSleepCalculation1Hour5Min()
    {
        int startHour = 4;
        int startMinute = 10;
        int startSecond = 0;
        Date startTime = new Time(startHour, startMinute, startSecond);
        int endHour = 5;
        int endMinute = 15;
        int endSecond = 0;
        Date endTime = new Time(endHour, endMinute, endSecond);
        int[] sleepCalculation = helper.CalculateTimeDifference(startTime, endTime);

        int[] expected = new int[]{1,5};
        Assert.assertArrayEquals(expected, sleepCalculation  );

    }

    @Test
    public void TestSleepCalculation14Hours10Min()
    {
        int startHour = 4;
        int startMinute = 10;
        int startSecond = 0;
        Date startTime = new Time(startHour, startMinute, startSecond);
        int endHour = 18;
        int endMinute = 20;
        int endSecond = 0;
        Date endTime = new Time(endHour, endMinute, endSecond);
        int[] sleepCalculation = helper.CalculateTimeDifference(startTime, endTime);

        int[] expected = new int[]{14,10};
        Assert.assertArrayEquals(expected, sleepCalculation  );

    }

    @Test
    public void TestCurrentTimeBeforeCurfewIsTrue()
    {
        int startHour = 4;
        int startMinute = 10;
        int startSecond = 0;
        Time currentTime = new Time(startHour, startMinute, startSecond);
        int endHour = 5;
        int endMinute = 15;
        int endSecond = 0;
        Time curfew = new Time(endHour, endMinute, endSecond);

        Assert.assertTrue(helper.CheckCurrentTimeBeforeCurfew(curfew, currentTime));

    }

    @Test
    public void TestCurrentTimeBeforeCurfewIsFalse()
    {
        int startHour = 4;
        int startMinute = 10;
        int startSecond = 0;
        Time currentTime = new Time(startHour, startMinute, startSecond);
        int endHour = 3;
        int endMinute = 15;
        int endSecond = 0;
        Time curfew = new Time(endHour, endMinute, endSecond);

        Assert.assertFalse(helper.CheckCurrentTimeBeforeCurfew(curfew, currentTime));

    }



}