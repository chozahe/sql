import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;

    private static final String SQL_SELECT_FROM_DRIVER = "SELECT * FROM driver";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_FROM_DRIVER);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            );
            result.add(user);
        }

        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            );
            resultSet.close();
            preparedStatement.close();
            return Optional.of(user);
        }

        resultSet.close();
        preparedStatement.close();
        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {
        String sqlInsert = "INSERT INTO driver (name, surname, city, brand, model, age) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);

        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setString(3, entity.getCity());
        preparedStatement.setString(4, entity.getBrand());
        preparedStatement.setString(5, entity.getModel());
        preparedStatement.setInt(6, entity.getAge());

        int affectedRows = preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void save(List<User> users) throws SQLException {
        StringBuilder sqlInsert = new StringBuilder("INSERT INTO driver (name, surname, city, brand, model, age) VALUES");

        for (int i = 0; i < users.size(); i++) {
            sqlInsert.append(" (?, ?, ?, ?, ?, ?)");
            if (i < users.size() - 1) {
                sqlInsert.append(",");
            }
        }

        PreparedStatement pstmt = connection.prepareStatement(sqlInsert.toString());
        int paramIndex = 1;
        for (User user : users) {
            pstmt.setString(paramIndex++, user.getFirstName());
            pstmt.setString(paramIndex++, user.getLastName());
            pstmt.setString(paramIndex++, user.getCity());
            pstmt.setString(paramIndex++, user.getBrand());
            pstmt.setString(paramIndex++, user.getModel());
            pstmt.setInt(paramIndex++, user.getAge());
        }

        int affectedRows = pstmt.executeUpdate();
        System.out.println("Было добавлено " + affectedRows + " строк.");
        pstmt.close();
    }

    @Override
    public void update(User entity) throws SQLException {
        String sqlUpdate = "UPDATE driver SET name = ?, surname = ?, city = ?, brand = ?, model = ?, age = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sqlUpdate);

        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setString(3, entity.getCity());
        statement.setString(4, entity.getBrand());
        statement.setString(5, entity.getModel());
        statement.setInt(6, entity.getAge());
        statement.setLong(7, entity.getId());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void remove(User entity) throws SQLException {
        removeById(entity.getId());
    }

    @Override
    public void removeById(Long id) throws SQLException {
        String sqlDelete = "DELETE FROM driver WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete);
        preparedStatement.setLong(1, id);

        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows != 0) {
            System.out.println("Удалено " + affectedRows + " строк.");
        } else {
            System.out.println("Пользователь с id = " + id + " не найден.");
        }

        preparedStatement.close();
    }

    @Override
    public void insert(int number) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные пользователей (имя, фамилия, город, бренд, модель, возраст):");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            System.out.println("Пользователь №" + (i + 1) + ":");

            String firstName = scanner.nextLine();
            String lastName = scanner.nextLine();
            String city = scanner.nextLine();
            String brand = scanner.nextLine();
            String model = scanner.nextLine();
            int age = Integer.parseInt(scanner.nextLine());

            users.add(new User(firstName, lastName, city, brand, model, age));
        }

        this.save(users);
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE age > ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setInt(1, age);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }

        resultSet.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<User> findAllByCity(String city) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE city = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, city);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        preparedStatement.close();
        return result;
    }

    @Override
    public List<User> findAllByCarBrand(String brand) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE brand = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, brand);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        preparedStatement.close();
        return result;
    }

    @Override
    public List<User> findAllByCarModel(String model) throws SQLException {
        String sqlSelect = "SELECT * FROM driver WHERE model = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect);
        preparedStatement.setString(1, model);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getInt(7)
            ));
        }
        preparedStatement.close();
        return result;
    }
}
