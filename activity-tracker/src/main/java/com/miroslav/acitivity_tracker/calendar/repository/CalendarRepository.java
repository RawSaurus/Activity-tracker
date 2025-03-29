package com.miroslav.acitivity_tracker.calendar.repository;

import com.miroslav.acitivity_tracker.calendar.module.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
