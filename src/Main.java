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

        userRepository.insert(2);


        //save
        /*User hz = new User("Мяу", "Мяукович", 35);*/
        /*userRepository.save(hz);*/

        //update
        /*User hz1 = userRepository.findById(11L).get();
        hz1.setAge(36);
        userRepository.update(hz1);*/

        //проверяю find all
        /*List<User> users = userRepository.findAll();*/
        /*users.forEach(user -> System.out.println(user.getFirstName()));*/
        //проверяю findById

        /*System.out.println(userRepository.findById(11L).get().getFirstName());*/

        //remove, removebyID
        /*userRepository.removeById(12L);
        userRepository.remove(hz1);
*/




    }









}