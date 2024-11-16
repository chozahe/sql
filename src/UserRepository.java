import java.sql.SQLException;
import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age) throws SQLException;
    List<User> findAllByCity(String city) throws SQLException;

    List<User> findAllByCarBrand(String brand) throws SQLException;

    List<User> findAllByCarModel(String model) throws SQLException;

}