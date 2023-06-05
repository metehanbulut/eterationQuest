package com.eteration.simplebanking.Exceptions;

public class InvalidAccountNumberException extends Exception{

    public InvalidAccountNumberException()
    {
        super();
        fillInStackTrace();
    }

    @Override
    public String getMessage() {

        return "Hatalı Account Number Verisi , Böyle Bir Numara Olamaz !";
    }
}
