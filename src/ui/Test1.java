package ui;

import dao.CartDao;
import dao.PhoneDao;
import dao.UserDao;
import dto.Phone;
import dto.User;

public class Test1 {
    // user dao
    static UserDao userDao = new UserDao();
    static PhoneDao phoneDao = new PhoneDao();
    static CartDao cartDao = new CartDao();

    public static void main(String[] args) {

        // System.out.println(userDao.insertUser(new User("로버트", "buyer")));
        // System.out.println(phoneDao.insertPhone(new Phone("삼성", "갤럭시S24", "스냅드래곤8", 8, 256, 6.2, 2024, 1155000, 10, 1)));

        System.out.println(cartDao.insertCart(3, 1));
    }
}
