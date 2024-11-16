public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private String brand;
    private String model;
    private Integer age;

    public User(Long id, String firstName, String lastName, String city, String brand, String model, Integer age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.brand = brand;
        this.model = model;
        this.age = age;
    }

    public User(String firstName, String lastName, String city, String brand, String model, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.brand = brand;
        this.model = model;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCity() {
        return city;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Integer getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{id" + id + ", ='" + firstName + "', lastName='" + lastName + "', city='" + city + "', brand='" + brand + "', model='" + model + "', age=" + age + '}';
    }

}
