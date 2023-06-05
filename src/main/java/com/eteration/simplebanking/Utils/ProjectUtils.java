package com.eteration.simplebanking.Utils;

import com.eteration.simplebanking.Exceptions.InvalidAccountNumberException;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProjectUtils {

    public static String generateApprovalCode(boolean isDebit)
    {
        if(isDebit)
        {
            return "";
        }
        else
        {
            return "";
        }

    }

    public static String generateAccountNumber()
    {
        return "";
    }

    public static boolean isAccountNumberValid(@NotNull String accountNumber) throws InvalidAccountNumberException {
        if(true) //  accountNumber.startsWith("1")örnek kontrol //todo Burada kontrol edilebilir diye ekledim.
        {
            return true;
        }
        else throw new InvalidAccountNumberException(); /** false da çekilebilirdi fakat ayrı bir mikroda olursa Exception Handle edilmeli @Author=mbBulut**/
    }
    public static List<String> validateBindBody(BindingResult result) {
        List<String> errors = new ArrayList<>();
        result
                .getFieldErrors()
                .forEach(f -> errors.add(f.getDefaultMessage())); //f.getField() + ": "  değişken adını vermedim resp. @author=mBulut
        return errors;
    }


    /** Exception Constructor'ları içerisinde de işlenebilir */
    public static final String ACCOUNT_NOT_FOUND_MESSAGE = "Bu banka numarasına ait bir banka adresi sistemimizde bulunamadı !";
    public static final String ACCOUNT_NUMBER_NOT_VALID_MESSAGE = "Girilen banka numarası geçersiz ! ";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Yapılması istenen işleme bakiye yetersiz !";
    public static final String ACCOUNT_NOT_VALID = "Geçersiz İşlem !";

    /** Exception Constructor'ları içerisinde de işlenebilir */
}
