package com.eteration.simplebanking.Services;

import com.eteration.simplebanking.Exceptions.InsufficientBalanceException;
import com.eteration.simplebanking.Model.Account;
import com.eteration.simplebanking.Model.DepositTransaction;
import com.eteration.simplebanking.Model.TransactionBase;
import com.eteration.simplebanking.Model.WithdrawalTransaction;
import com.eteration.simplebanking.Repository.AccountRepository;
import com.eteration.simplebanking.Utils.ProjectUtils;
import com.eteration.simplebanking.Utils.TransactionStatus;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

// This class is a place holder you can change the complete implementation

@Service
public class AccountService implements IAccountService {


    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account createAccount(String ownerName) {
        Account newAccount = new Account(ownerName, ProjectUtils.generateAccountNumber());
        return accountRepository.save(newAccount);
    }

    @Override
    public TransactionStatus debit(@NotNull Account prmAccount, @NotNull TransactionBase trx) throws InsufficientBalanceException {
        if (trx.getClass().equals(WithdrawalTransaction.class)) {
            try {
                Double lastBalance = prmAccount.withdraw(trx.getAmount()); /**lastBalance kullanılabilir sonraki işlemler için **/
                return new TransactionStatus(true, true, null);
            } catch (InsufficientBalanceException e) {
                TransactionStatus x = new TransactionStatus(false, true, e.getMessage());
                return x;
            }
        } else if (trx.getClass().equals(DepositTransaction.class)) {

            Double lastBalance = prmAccount.deposit(trx.getAmount());
            return new TransactionStatus(true, true, null); /** Başka Exceptionlar kontrol edilebilir */
        } else
            return new TransactionStatus(false, true, "Bu tarafı yapmadım. @Author Mbulut Dinamik Class ile Generify edilerek burası yapılabilir. " + "Classlara gerek duyulmaz, Credit Ve Debit olarak iki panelde tek class ve işlem tipi ile işlemler görülür. "); // devam edilebilir ama benim belirlediğim yapı farklı.

    }

    @Override
    public TransactionStatus credit(@NotNull Account prmAccount, @NotNull TransactionBase trx) {
        System.out.println("----------------------");
        if (trx.getClass().equals(WithdrawalTransaction.class)) {
            try {
                Double lastBalance = prmAccount.withdraw(trx.getAmount()); /**lastBalance kullanılabilir sonraki işlemler için **/
                return new TransactionStatus(true, false, null);
            } catch (InsufficientBalanceException e) {
                TransactionStatus x = new TransactionStatus(false, false, e.getMessage());
                return x;
            }
        } else if (trx.getClass().equals(DepositTransaction.class)) {

            Double lastBalance = prmAccount.deposit(trx.getAmount());
            return new TransactionStatus(true, false, null); /** Başka Exceptionlar kontrol edilebilir */
        } else return new TransactionStatus(false, false, "-"); // devam edilebilir ama benim belirlediğim yapı farklı.
    }

    @Override
    public Account findAccount(@NotNull String accountNumber) {
        Optional<Account> account = accountRepository.findAccountByAccountNumber(accountNumber);
        return account.orElse(null);
    }
}
