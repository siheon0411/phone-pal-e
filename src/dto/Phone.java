package dto;

public class Phone {
    private long phoneId;   // auto-increment
    private String manufacturer;
    private String phoneName;
    private String cpu;
    private int ram;
    private int storage;
    private double screenSize;
    private int manufacturedYear;
    private int price;
    private int stockQuantity;
    private int salesQuantity;

    public Phone() {}

    public Phone(String manufacturer, String phoneName, String cpu, int ram, int storage, double screenSize, int manufacturedYear, int price, int stockQuantity, int salesQuantity) {
        this.manufacturer = manufacturer;
        this.phoneName = phoneName;
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.screenSize = screenSize;
        this.manufacturedYear = manufacturedYear;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.salesQuantity = salesQuantity;
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(double screenSize) {
        this.screenSize = screenSize;
    }

    public int getManufacturedYear() {
        return manufacturedYear;
    }

    public void setManufacturedYear(int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getSalesQuantity() {
        return salesQuantity;
    }

    public void setSalesQuantity(int salesQuantity) {
        this.salesQuantity = salesQuantity;
    }
}
