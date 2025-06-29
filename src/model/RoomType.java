package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomType {
    private int id;
    private int villa; // disamakan dengan nama kolom "villa"
    private String name;
    private int quantity;
    private int capacity;
    private double price;
    private String bedSize;
    private boolean hasDesk;
    private boolean hasAc;
    private boolean hasTv;
    private boolean hasWifi;
    private boolean hasShower;
    private boolean hasHotwater;
    private boolean hasFridge;

    // Constructor kosong
    public RoomType() {}

    // Constructor lengkap (dengan ID)
    public RoomType(int id, int villa, String name, int quantity, int capacity, double price,
                    String bedSize, boolean hasDesk, boolean hasAc, boolean hasTv, boolean hasWifi,
                    boolean hasShower, boolean hasHotwater, boolean hasFridge) {
        this.id = id;
        this.villa = villa;
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.price = price;
        this.bedSize = bedSize;
        this.hasDesk = hasDesk;
        this.hasAc = hasAc;
        this.hasTv = hasTv;
        this.hasWifi = hasWifi;
        this.hasShower = hasShower;
        this.hasHotwater = hasHotwater;
        this.hasFridge = hasFridge;
    }

    // Constructor tanpa ID
    public RoomType(int villa, String name, int quantity, int capacity, double price,
                    String bedSize, boolean hasDesk, boolean hasAc, boolean hasTv, boolean hasWifi,
                    boolean hasShower, boolean hasHotwater, boolean hasFridge) {
        this(0, villa, name, quantity, capacity, price, bedSize,
                hasDesk, hasAc, hasTv, hasWifi, hasShower, hasHotwater, hasFridge);
    }

    // Static method untuk parsing dari ResultSet
    public static List<RoomType> fromResultSet(ResultSet rs) throws SQLException {
        List<RoomType> rooms = new ArrayList<>();
        while (rs.next()) {
            RoomType room = new RoomType(
                    rs.getInt("id"),
                    rs.getInt("villa"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getInt("capacity"),
                    rs.getDouble("price"),
                    rs.getString("bed_size"),
                    rs.getBoolean("has_desk"),
                    rs.getBoolean("has_ac"),
                    rs.getBoolean("has_tv"),
                    rs.getBoolean("has_wifi"),
                    rs.getBoolean("has_shower"),
                    rs.getBoolean("has_hotwater"),
                    rs.getBoolean("has_fridge")
            );
            rooms.add(room);
        }
        return rooms;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVilla() {
        return villa;
    }

    public void setVilla(int villa) {
        this.villa = villa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBedSize() {
        return bedSize;
    }

    public void setBedSize(String bedSize) {
        this.bedSize = bedSize;
    }

    public boolean isHasDesk() {
        return hasDesk;
    }

    public void setHasDesk(boolean hasDesk) {
        this.hasDesk = hasDesk;
    }

    public boolean isHasAc() {
        return hasAc;
    }

    public void setHasAc(boolean hasAc) {
        this.hasAc = hasAc;
    }

    public boolean isHasTv() {
        return hasTv;
    }

    public void setHasTv(boolean hasTv) {
        this.hasTv = hasTv;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isHasShower() {
        return hasShower;
    }

    public void setHasShower(boolean hasShower) {
        this.hasShower = hasShower;
    }

    public boolean isHasHotwater() {
        return hasHotwater;
    }

    public void setHasHotwater(boolean hasHotwater) {
        this.hasHotwater = hasHotwater;
    }

    public boolean isHasFridge() {
        return hasFridge;
    }

    public void setHasFridge(boolean hasFridge) {
        this.hasFridge = hasFridge;
    }
}
