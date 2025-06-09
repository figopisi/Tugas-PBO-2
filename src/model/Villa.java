package model;

public class Villa {
    private int id;
    private String name;
    private String description;
    private String address;

    public Villa(int id, String name, String description, String address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public Villa(String name, String description, String address) {
        this(0, name, description, address);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }
}
