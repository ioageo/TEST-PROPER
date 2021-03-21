package com.proper.lang;

import java.time.Duration;
import java.time.LocalDate;

public class DateInterval {
  private Duration duration;
  private LocalDate start;

  public static DateInterval from(LocalDate start, Duration duration) {
    DateInterval interval = new DateInterval();
    interval.setDuration(duration);
    interval.setStart(start);
    return interval;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public LocalDate getStart() {
    return start;
  }

  public void setStart(LocalDate start) {
    this.start = start;
  }

public LocalDate getLastDay() {
	return start.plusDays(duration.toDays());
}


}
