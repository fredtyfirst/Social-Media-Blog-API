package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account newUser (Account user){
        if((accountDAO.getAccountByUsername(user.getUsername())==null) && !(user.getUsername().isEmpty()) && (user.getPassword().length())>=4){
            return accountDAO.userRegistration(user);
        }
        else{
            return null;
        }
           
    }

    public List<Account>  existingUser(){

        return accountDAO.getAllAccount();
    }


    public Account loginUser (Account user){
        if(accountDAO.Login(user.getUsername(), user.getPassword())!=null) {
            return accountDAO.Login(user.getUsername(), user.getPassword()) ;
        }
        else{
            return null;
        }
           
    }


    
}
