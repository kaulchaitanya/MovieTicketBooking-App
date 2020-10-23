package com.book.movieticketbooking.useractivity.model;

public class Account {
    String AccountHolderName;
    String AccountNumber;
    String AccountDate;
    String AccountMobile;
    String AccountPassword;
    String AccountMoney;

    public Account(){
    }

    public Account(String accountHolderName, String accountNumber, String accountDate, String accountMobile,String accountMoney,String accountPassword) {
        AccountHolderName = accountHolderName;
        AccountNumber = accountNumber;
        AccountDate = accountDate;
        AccountMobile = accountMobile;
        AccountMoney = accountMoney;
        AccountPassword = accountPassword;
    }

    public String getAccountHolderName() {
        return AccountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) { AccountHolderName = accountHolderName; }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public String getAccountDate() {
        return AccountDate;
    }

    public void setAccountDate(String accountDate) {
        AccountDate = accountDate;
    }

    public String getAccountMobile() {
        return AccountMobile;
    }

    public void setAccountMobile(String accountMobile) {
        AccountMobile = accountMobile;
    }

    public String getAccountMoney() {
        return AccountMoney;
    }

    public void setAccountMoney(String accountMoney) {
        AccountMoney = accountMoney;
    }

    public String getAccountPassword() {
        return AccountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        AccountPassword = accountPassword;
    }
}
