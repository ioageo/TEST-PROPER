package com.proper.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.With;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Value
@Builder
@With

public class Charge {
  public enum Type {Rent, Deposit, PrepaidRent, AdHoc}

  private Money amount;
  private LocalDate chargeDate;
  private String description;
  private Type type;

  // Either count or until must be set
  /** Specifies the number of times a charge is to occur. */
  @Builder.Default private Integer count = null;
  /** Specifies the end date of the charge recurrence. */
  @Builder.Default private LocalDate until = null;
  
}
