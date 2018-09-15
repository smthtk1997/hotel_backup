package Store;

import Model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDBControl {
    private Connection connection;
    private ResultSet resultSet = null;
    private Statement stmt = null;

    public UserDBControl(Connection connection) {
        this.connection = connection;
    }

    public boolean addUser(User user){
        boolean addResult = false;
        try {
            User newUser = user;
            String sqlText = "INSERT INTO user_data VALUES (?,?,?,?,?,?)";
            PreparedStatement prepare = connection.prepareStatement(sqlText);
            prepare.setInt(1,newUser.getiDentify());
            prepare.setString(2,newUser.getUserName());
            prepare.setString(3,newUser.getFirstName());
            prepare.setString(4,newUser.getLastName());
            prepare.setString(5,newUser.geteMail());
            prepare.setString(6,newUser.getPassWord());

            if (prepare.executeUpdate() == 1){
                addResult = true;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return addResult;
    }



    public ArrayList<User> readUser(){ // Review User //
        ArrayList<User> usersArray = new ArrayList<>();
        User user = null;
        try{
            stmt = connection.createStatement();
            String query = "SELECT * FROM user_data";
            resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                user = new User();
                user.setiDentify(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setFirstName(resultSet.getString(3));
                user.setLastName(resultSet.getString(4));
                user.seteMail(resultSet.getString(5));
                user.setPassWord(resultSet.getString(6));
                usersArray.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }
        return usersArray;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}