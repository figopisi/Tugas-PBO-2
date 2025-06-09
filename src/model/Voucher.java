package model;

public class Voucher {
    private int id;
    private String code;
    private String description;
    private double discount;
    private String startDate;
    private String endDate;

    public Voucher() {}

    public Voucher(int id, String code, String description, double discount, String startDate, String endDate) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Voucher(String code, String description, double discount, String startDate, String endDate) {
        this(0, code, description, discount, startDate, endDate);
    }
    
    public int getId() {
        return id;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
