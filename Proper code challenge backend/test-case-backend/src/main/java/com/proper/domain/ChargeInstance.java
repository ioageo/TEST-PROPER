package com.proper.domain;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.joda.money.Money;

@Value
@Builder
@With
public class ChargeInstance {
  private Money amount;
  private LocalDate chargeDate;
  private String description;
  private Charge.Type type;
  
  
}
