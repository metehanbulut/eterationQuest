package com.eteration.simplebanking.Controller;

import com.eteration.simplebanking.Exceptions.InsufficientBalanceException;
import com.eteration.simplebanking.Exceptions.InvalidAccountNumberException;
import com.eteration.simplebanking.Model.TransactionBase;
import com.eteration.simplebanking.Utils.*;
import com.eteration.simplebanking.Model.Account;
import com.eteration.simplebanking.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/account" + "/${info.build.version}")
@RestController
@Validated
public class AccountController {

    /*** Not -->
     * Mock.verify Servisin kullandığı fonksiyonu görmüyor
     * Repo hep boş geliyor
     *
     * Constructor injection Autowired Injection vb. şeyler denedim.
     * Fakat TestClassını MockMvc ile değiştirmeden çözümünü bulamadım. Değişiklikleri geri alıp yolluyorum
     *
     *
     * @Author = Metehan Bulut
     * Saygılarımla..
     *
     *
     * https://stackoverflow.com/questions/20551926/exception-mockito-wanted-but-not-invoked-actually-there-were-zero-interaction
     *
     */

    @Autowired
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Extra for Db
     */
    @PostMapping(value = "/createAccount",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountInput createAccountInput) throws InvalidAccountNumberException {
        if (ProjectUtils.isAccountNumberValid(createAccountInput.getAccountNumber())) {
            Account account = accountService.createAccount(createAccountInput.getOwnerName());
            if (account == null) {
                return new ResponseEntity<>(ProjectUtils.ACCOUNT_NOT_VALID, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(account, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(ProjectUtils.ACCOUNT_NOT_VALID, HttpStatus.OK);
        }
    }


    @GetMapping(value = "/{accountNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAccount(
            @NotNull(message = "Account Number is required !")
            @NotBlank(message = "Account Number is required !")

            @PathVariable("accountNumber") String accountNumber) throws InvalidAccountNumberException {
        if (ProjectUtils.isAccountNumberValid(accountNumber)) {
            Account tmpAccount = accountService.findAccount(accountNumber);
            if (tmpAccount == null) {
                return new ResponseEntity<>(ProjectUtils.ACCOUNT_NOT_FOUND_MESSAGE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(tmpAccount, HttpStatus.OK);
            }
        } else return new ResponseEntity<>(ProjectUtils.ACCOUNT_NUMBER_NOT_VALID_MESSAGE, HttpStatus.BAD_REQUEST);
    }


    /**
     * Mock dolayısıyla kaldırıldı
     * , BindingResult bindingResult
     * */
/*        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(ProjectUtils.validateBindBody(bindingResult), HttpStatus.BAD_GATEWAY);
        }*/

    @PostMapping(value = "/credit/{accountNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> credit(
            @NotNull(message = "Account Number is required !")
            @NotBlank(message = "Account Number is required !")
            @PathVariable("accountNumber") String accountNumber,

            @Valid @RequestBody TransactionBase transactionBase) throws InvalidAccountNumberException {

        if (ProjectUtils.isAccountNumberValid(accountNumber)) {
            Account tmpAccount = accountService.findAccount(accountNumber);
            if (tmpAccount == null) {
                return new ResponseEntity<>(new TransactionStatus(false,false,ProjectUtils.ACCOUNT_NOT_FOUND_MESSAGE), HttpStatus.BAD_REQUEST);
            } else {
                TransactionStatus tmp =accountService.credit(tmpAccount,transactionBase);
                return new ResponseEntity<>(tmp, HttpStatus.OK);
            }
        } else return new ResponseEntity<>(new TransactionStatus(false,false,ProjectUtils.ACCOUNT_NUMBER_NOT_VALID_MESSAGE), HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/debit/{accountNumber}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionStatus> debit(
            @NotNull(message = "Account Number is required !")
            @NotBlank(message = "Account Number is required !")
            @PathVariable("accountNumber") String accountNumber,
            @Valid @RequestBody TransactionBase transactionBase
    ) throws InvalidAccountNumberException, InsufficientBalanceException {
        if (ProjectUtils.isAccountNumberValid(accountNumber)) {
            Account tmpAccount = accountService.findAccount(accountNumber);
            if (tmpAccount == null) {
                return new ResponseEntity<>(new TransactionStatus(false,true,ProjectUtils.ACCOUNT_NOT_FOUND_MESSAGE), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(accountService.debit(tmpAccount,transactionBase), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(new TransactionStatus(false,true,ProjectUtils.ACCOUNT_NUMBER_NOT_VALID_MESSAGE), HttpStatus.BAD_REQUEST);
    }


    /**
     * PathVariable Exception Convert TO 500 ResponseEntity
     * <p>
     * mBulut
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

}
