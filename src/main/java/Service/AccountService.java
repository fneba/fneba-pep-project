package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account registerNewAccount(Account account){
        return accountDAO.registerAccount(account);
    }

    public Account loginToAccount(String username, String password){
        return accountDAO.loginAccount(username, password);
    }

    public Account getAccountByUsername(String username){
        return accountDAO.findAccountByUsername(username);
    }
}
