package com.eteration.simplebanking.Utils;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CreateAccountInput {

    @NotBlank
    private String accountNumber;
    @NotBlank(message = "Owner name is mandatory")
    private String ownerName;


    public CreateAccountInput() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


}
