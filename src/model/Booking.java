package model;

public class Booking {
    private int id;
    private int customerId;
    private int roomTypeId;
    private String checkinDate;
    private String checkoutDate;
    private double price;
    private Integer voucherId;
    private double finalPrice;
    private String paymentStatus;
    private boolean hasCheckedIn;
    private boolean hasCheckedOut;

    public Booking() {}

    public Booking(int id, int customerId, int roomTypeId, String checkinDate, String checkoutDate,
                   double price, Integer voucherId, double finalPrice,
                   String paymentStatus, boolean hasCheckedIn, boolean hasCheckedOut) {
        this.id = id;
        this.customerId = customerId;
        this.roomTypeId = roomTypeId;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.price = price;
        this.voucherId = voucherId;
        this.finalPrice = finalPrice;
        this.paymentStatus = paymentStatus;
        this.hasCheckedIn = hasCheckedIn;
        this.hasCheckedOut = hasCheckedOut;
    }

    public Booking(int customerId, int roomTypeId, String checkinDate, String checkoutDate,
                   double price, Integer voucherId, double finalPrice,
                   String paymentStatus, boolean hasCheckedIn, boolean hasCheckedOut) {
        this(0, customerId, roomTypeId, checkinDate, checkoutDate, price, voucherId, finalPrice, paymentStatus, hasCheckedIn, hasCheckedOut);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public boolean isHasCheckedIn() {
        return hasCheckedIn;
    }

    public void setHasCheckedIn(boolean hasCheckedIn) {
        this.hasCheckedIn = hasCheckedIn;
    }

    public boolean isHasCheckedOut() {
        return hasCheckedOut;
    }

    public void setHasCheckedOut(boolean hasCheckedOut) {
        this.hasCheckedOut = hasCheckedOut;
    }
}
