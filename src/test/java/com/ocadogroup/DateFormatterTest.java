package com.ocadogroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

class DateFormatterTest {

    private SimpleDateFormat simpleDateFormat;

    @BeforeEach
    void initializeSimpleDateFormat() {
        simpleDateFormat = new SimpleDateFormat("HH:mm");
    }


    @Test
    void dateShouldBeUpdatedAfterUpdate() {
        //given
        String date = "09:00";
        long numberOfMinutes = 15;

        //when
        String dateUpdated = DateFormatter.updateDate(date, numberOfMinutes);

        //then
        assertThat(dateUpdated, equalTo("09:15"));
    }

    @Test
    void numberOfMinutesBetweenTwoDatesShouldBeCorrect() {
        //given
        String firstDate = "09:00";
        String secondDate = "10:00";

        //when
        long numberOfMinutes = DateFormatter.numberOfMinutesBetweenTwoDates(firstDate, secondDate);

        //then
        assertThat(numberOfMinutes, equalTo(60L));
    }

    @Test
    void getMinutesFromISOFormatShouldReturnDurationInMinutes() {
        //given
        String isoString = "PT15M";

        //when
        long minutes = DateFormatter.getMinutesFromISOFormat(isoString);

        //then
        assertThat(minutes, equalTo(15L));
    }

    @Test
    void getCalenderShouldReturnCalenderInstance() {
        //given
        String date = "10:00";

        //when
        Calendar calendar = DateFormatter.getCalendar(date);

        //then
        assertThat(calendar, instanceOf(Calendar.class));
        assertThat(simpleDateFormat.format(calendar.getTime()), equalTo("10:00"));
    }


}
