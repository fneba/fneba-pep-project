package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;


public class AccountDAO {
    
    // little helper method for when I am making the controller
    public Account findAccountByUsername(String username){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int accountId = resultSet.getInt("account_id");
                String retrievedUsername = resultSet.getString("username");
                String retrievedPassword = resultSet.getString("password");
                return new Account(accountId, retrievedUsername, retrievedPassword);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    // register new account i.e. create account
    public Account registerAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());


            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = pkeyResultSet.getInt(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // account login
    public Account loginAccount(String username, String password) {
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? and password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int accountId = resultSet.getInt("account_id");
                String retrievedUsername = resultSet.getString("username");
                String retrievedPassword = resultSet.getString("password");
                return new Account(accountId, retrievedUsername, retrievedPassword);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
