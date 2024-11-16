import java.sql.*;
import java.util.Scanner;
public class DriverDatabase {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Negry1488";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_1";
    private Connection conn;

    public DriverDatabase() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public void insertSixUsers() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Ввод данных для шести пользователей
        System.out.println("Введите данные шести пользователей (имя, фамилия, возраст):");
        String[][] usersData = new String[6][3];
        for (int i = 0; i < 6; i++) {
            System.out.println("Пользователь №" + (i + 1) + ":");
            usersData[i][0] = scanner.nextLine(); // Имя
            usersData[i][1] = scanner.nextLine(); // Фамилия
            usersData[i][2] = scanner.nextLine(); // Возраст
        }

        // Подготовка запроса
        String sqlInsert = "INSERT INTO driver (name, surname, age) VALUES " +
                "(?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?), (?, ?, ?)";


        PreparedStatement pstmt = conn.prepareStatement(sqlInsert);

        // Заполнение параметров
        int paramIndex = 1;
        for (String[] user : usersData) {
            pstmt.setString(paramIndex++, user[0]); // Имя
            pstmt.setString(paramIndex++, user[1]); // Фамилия
            pstmt.setString(paramIndex++, user[2]); // Возраст
        }

        int affectedRows = pstmt.executeUpdate();
        System.out.println("Было добавлено " + affectedRows + " строк.");
        pstmt.close();
    }

    public void displayDriversOlderThan(int ageLimit) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE CAST(age AS INTEGER) > ?";
        PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
        pstmt.setInt(1, ageLimit);

        ResultSet result = pstmt.executeQuery();

        System.out.println("Водители старше " + ageLimit + " лет:");
        while (result.next()) {
            System.out.println(result.getInt("id") + " " + result.getString("name") +
                    " " + result.getString("surname") + " " + result.getString("age"));
        }
        pstmt.close();
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
