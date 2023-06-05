package com.eteration.simplebanking.Model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

// This class is a place holder you can change the complete implementation
@Data
public class TransactionBase { //** abstract olabilirdi . @Author = mbulut

    public Date date= new Date();

    @Min(value = 0,message = "İşlem gerçekleşmesi için miktar belirtmelisiniz !")
    public Double amount=0d; // null geçilme durumu oluşursa hataya düşmesin diye 0 setlenebilir -- deprec.

    @NotNull(message = "İşlem Tipini Belirtiniz ! ")
    public  String type;
    public String approvalCode;

    public TransactionBase(@Valid Double amount,String type) /** prm Date alınıp ileri tarihli işlem bekletme vs yapılırsa buradan param. geçilebilir @Author >= mbulut */
    {
        this.amount=amount;
        this.type=type;
    }

    public Double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
