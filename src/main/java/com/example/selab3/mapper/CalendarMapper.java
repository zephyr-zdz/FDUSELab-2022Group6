package com.example.selab3.mapper;

import com.example.selab3.model.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarMapper extends JpaRepository<Calendar,Integer> {
    Calendar findCalendarByDayAndNumber(String day,String number);

    Calendar findCalendarById(Integer calendarid);
}
