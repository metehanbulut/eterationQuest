package com.eteration.simplebanking.Services;

import com.eteration.simplebanking.Exceptions.InsufficientBalanceException;
import com.eteration.simplebanking.Model.Account;
import com.eteration.simplebanking.Model.DepositTransaction;
import com.eteration.simplebanking.Model.TransactionBase;
import com.eteration.simplebanking.Model.WithdrawalTransaction;
import com.eteration.simplebanking.Utils.ProjectUtils;
import com.eteration.simplebanking.Utils.TransactionStatus;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface IAccountService {
    Account createAccount(String ownerName) ;
    TransactionStatus debit(@NotNull Account prmAccount, @NotNull TransactionBase trx) throws InsufficientBalanceException;
    TransactionStatus credit(@NotNull Account prmAccount, @NotNull TransactionBase trx);

    Account findAccount(@NotNull String accountNumber);
}
