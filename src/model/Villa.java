package model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Villa {
    private int id;
    private String name;
    private String description;
    private String address;

    public Villa() {
    }

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

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setAddress(String address) { this.address = address; }

    public static List<Villa> fromResultSet(ResultSet resultSet) throws Exception {
        List<Villa> villas = new ArrayList<>();
        while (resultSet.next()) {
            villas.add(new Villa(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("address")
            ));
        }
        return villas;
    }
}
