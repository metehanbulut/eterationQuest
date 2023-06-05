package com.eteration.simplebanking.Utils;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AmountInput {

    @Size(min = 0,message = "Girilen Miktar 0 Olamaz !")
    Double amount;
}
