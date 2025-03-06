package ui;

import dao.UserDao;
import dto.User;

public class Test1 {
    // user dao
    static UserDao userDao = new UserDao();

    public static void main(String[] args) {

        System.out.println(userDao.insertUser(new User("로버트", "buyer")));
    }
}
