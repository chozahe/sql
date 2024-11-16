import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;


    private static final String SQL_SELECT_FROM_DRIVER = "select * from driver";
    public UsersRepositoryJdbcImpl(Connection connection) {this.connection = connection;}
    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("surname"),
                    resultSet.getInt("age"));
            result.add(user);
        }

        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String sqlInsert = "SELECT * FROM driver WHERE id = " + "(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString("surname"),
                resultSet.getInt("age"));
        return Optional.ofNullable(user);

    }

    @Override
    public void save(User entity) throws SQLException {
        String sqlInsert = "INSERT INTO driver (name, surname, age) VALUES " +"(?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setInt(3, entity.getAge());
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("Было добавлено " + affectedRows + " строк.");
        preparedStatement.close();
    }

    @Override
    public void update(User entity) throws SQLException {
        String sqlUpdate = "UPDATE driver SET name = ?, surname = ?, age = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sqlUpdate);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setLong(4, entity.getId());
        statement.executeUpdate();
    }

    @Override
    public void remove(User entity) throws SQLException {
        /*String sqlInsert = "DELETE FROM driver WHERE name = " + "(?)" + "AND surname = " + "(?)" + "AND age = " + "(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setInt(3, entity.getAge());
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 0) {
            System.out.println("все удалилось");
        }else{
            System.out.println("нет такова");
        }
        preparedStatement.close();*/
        this.removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        String sqlInsert = "DELETE FROM driver WHERE id = " + "(?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);
        preparedStatement.setLong(1, id);
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 0) {
            System.out.println("все удалилось");
        }else{
            System.out.println("нет такова");
        }
        preparedStatement.close();
    }

    @Override
    public void insert(int number) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные ваших пользователей (имя, фамилия, возраст):");
        String[][] usersData = new String[number][3];
        String sqlInsert = "INSERT INTO driver (name, surname, age) VALUES";
        for (int i = 0; i < number; i++) {
            System.out.println("Пользователь №" + (i + 1) + ":");
            usersData[i][0] = scanner.nextLine(); // Имя
            usersData[i][1] = scanner.nextLine(); // Фамилия
            usersData[i][2] = scanner.nextLine(); // Возраст
            sqlInsert+=" (?, ?, ?)";
            if (i < number - 1) {
                sqlInsert += ",";
            }
        }
        PreparedStatement pstmt = connection.prepareStatement(sqlInsert);

        int paramIndex = 1;
        for (String[] user : usersData) {
            pstmt.setString(paramIndex++, user[0]); // Имя
            pstmt.setString(paramIndex++, user[1]); // Фамилия
            pstmt.setInt(paramIndex++, Integer.parseInt(user[2])); // Возраст
        }
        int affectedRows = pstmt.executeUpdate();
        System.out.println("Было добавлено " + affectedRows + " строк.");
        pstmt.close();

    }


    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE age > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, age);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString("surname"), resultSet.getInt("age")));
        }
        preparedStatement.close();
        return result;


    }

}