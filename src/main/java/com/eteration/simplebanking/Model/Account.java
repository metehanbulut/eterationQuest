package com.eteration.simplebanking.Model;

import com.eteration.simplebanking.Exceptions.InsufficientBalanceException;
import com.eteration.simplebanking.Services.AccountService;
import com.eteration.simplebanking.Utils.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "account", schema = "eteration")
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue
    private long id;
    private String owner;
    private String accountNumber;
    private double balance=0d;
    private transient List<TransactionBase> transactions= new ArrayList<>();

    public Account() {}
    public Account(String ownerName, String accountNumber) {
        this.owner = ownerName;
        this.accountNumber = accountNumber;
    }

    /**
     * Vt.de bağ ile ara tablo ile de kurulabilir NoRelationship Veritabanında Transient de kullanılabilir
     */


    @Override
    public String toString() {
        return "Account{" + ", Hesap Numarası='" + accountNumber + '\'' + ", Bakiye =" + balance + ", Sahibi='" + owner + '\'' + '}';
    }

    public Double withdraw(Double amount) throws InsufficientBalanceException {
        if (this.balance >= amount) {
            return this.balance = this.balance - amount;
        } else
            throw new InsufficientBalanceException(this.owner, this.balance); /** create Excp.  Dışarıya bağlanabilirdi **/
    }

    public Double  deposit(Double amount) {
        return this.balance = this.balance + amount;
    }

    public void post(Object transaction)
    {
        if(transaction.getClass().equals(WithdrawalTransaction.class))
        {
            WithdrawalTransaction tmp = (WithdrawalTransaction) transaction;
            try{
                Double lastBalance =this.withdraw(tmp.getAmount());
                this.transactions.add(tmp);
            }
            catch (InsufficientBalanceException e)
            {
                TransactionStatus x = new TransactionStatus(false,true,null);
                x.setApprovalCode(e.getMessage());
            }
        }
        else if(transaction.getClass().equals(DepositTransaction.class)) {
            DepositTransaction tmp = (DepositTransaction) transaction;
            Double lastBalance = deposit(tmp.getAmount());
            this.transactions.add(tmp);
        }
    }

    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public List<TransactionBase> getTransactions() {
        return transactions;
    }
}
