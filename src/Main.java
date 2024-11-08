import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        DriverDatabase db = new DriverDatabase();

        db.insertSixUsers(); // Вставляем шесть пользователей
        db.displayDriversOlderThan(25); // Выводим водителей старше 25 лет

        db.close();
    }
}