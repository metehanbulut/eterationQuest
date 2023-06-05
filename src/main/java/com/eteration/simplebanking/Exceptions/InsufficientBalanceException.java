package com.eteration.simplebanking.Exceptions;

public class InsufficientBalanceException extends Exception{
    private String ownerName="ErrorInInsufficientBalanceException !";

    private Double amount=0d;
    public InsufficientBalanceException(String ownerName , Double amount)
    {
        super();
        this.ownerName=ownerName;
        this.amount=amount;
        fillInStackTrace();
    }

    @Override
    public String getMessage() {

        return ownerName+ " hesabınızda  "+amount+" kadar para bulunmuyor .! ";
    }
}
