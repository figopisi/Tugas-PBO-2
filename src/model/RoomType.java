package model;

public class RoomType {
    private int id;
    private int villaId;
    private String name;
    private int quantity;
    private int capacity;
    private double price;
    private String bedSize;
    private boolean hasDesk;
    private boolean hasAC;
    private boolean hasWiFi;
    private boolean hasBathtub;

    public RoomType() {}

    public RoomType(int id, int villaId, String name, int quantity, int capacity, double price,
                    String bedSize, boolean hasDesk, boolean hasAC, boolean hasWiFi, boolean hasBathtub) {
        this.id = id;
        this.villaId = villaId;
        this.name = name;
        this.quantity = quantity;
        this.capacity = capacity;
        this.price = price;
        this.bedSize = bedSize;
        this.hasDesk = hasDesk;
        this.hasAC = hasAC;
        this.hasWiFi = hasWiFi;
        this.hasBathtub = hasBathtub;
    }

    public RoomType(int villaId, String name, int quantity, int capacity, double price,
                    String bedSize, boolean hasDesk, boolean hasAC, boolean hasWiFi, boolean hasBathtub) {
        this(0, villaId, name, quantity, capacity, price, bedSize, hasDesk, hasAC, hasWiFi, hasBathtub);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVillaId() {
        return villaId;
    }

    public void setVillaId(int villaId) {
        this.villaId = villaId;
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

    public boolean isHasAC() {
        return hasAC;
    }

    public void setHasAC(boolean hasAC) {
        this.hasAC = hasAC;
    }

    public boolean isHasWiFi() {
        return hasWiFi;
    }

    public void setHasWiFi(boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    public boolean isHasBathtub() {
        return hasBathtub;
    }

    public void setHasBathtub(boolean hasBathtub) {
        this.hasBathtub = hasBathtub;
    }
}
