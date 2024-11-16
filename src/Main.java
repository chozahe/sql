import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Negry1488";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/testdb_1";
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);



        System.out.println("добавление двух пользователей");
        userRepository.insert(2);

        System.out.println("поиск пользователя по id = 1:");
        Optional<User> userById = userRepository.findById(1L);
        User userId = userById.get();
        System.out.println(userId);

        System.out.println("вставка нового пользователя:");
        User newUser = new User("глад", "валакас", "самара", "тойота", "каролла", 54);
        userRepository.save(newUser);

        System.out.println("список всех пользователей:");
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            System.out.println("id: " + user.getId() + ", имя: " + user.getFirstName() +
                    ", фамилия: " + user.getLastName() + ", возраст: " + user.getAge());
        }

        System.out.println("список пользователей из города 'самара':");
        List<User> usersByCity = userRepository.findAllByCity("самара");
        for (User user : usersByCity) {
            System.out.println(user);
        }

        System.out.println("список пользователей с маркой автомобиля 'тойота':");
        List<User> usersByBrand = userRepository.findAllByCarBrand("тойота");
        for (User user : usersByBrand) {
            System.out.println(user);
        }

        System.out.println("список пользователей с моделью автомобиля 'каролла':");
        List<User> usersByModel = userRepository.findAllByCarModel("каролла");
        for (User user : usersByModel) {
            System.out.println(user);
        }

        System.out.println("список пользователей старше 50");
        List<User> usersOlderThan = userRepository.findAllByAge(54);
        for (User user : usersOlderThan) {
            System.out.println(user);
        }

        System.out.println("обновление пользователя с id = 1:");
        Optional<User> userToUpdate = userRepository.findById(1L);
        User user = userToUpdate.get();
        user.setFirstName("обновленни");
        userRepository.update(user);


        System.out.println("удаление пользователя с id = 3:");
        userRepository.removeById(3L);



    }









}