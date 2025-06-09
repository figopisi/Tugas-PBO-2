package model;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer() {} // Required for JSON deserialization

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(String name, String email, String phone) {
        this(0, name, email, phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
