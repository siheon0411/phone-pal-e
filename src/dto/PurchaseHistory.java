package dto;

import java.time.LocalDateTime;

public class PurchaseHistory {
    private long purchaseId;
    private long buyerId;
    private long phoneId;
    private int purchasePrice;
    private int purchaseQuantity;
    private LocalDateTime purchasedAt;

    public PurchaseHistory() {}

    public PurchaseHistory(long purchaseId, long buyerId, long phoneId, int purchasePrice, int purchaseQuantity, LocalDateTime purchasedAt) {
        this.purchaseId = purchaseId;
        this.buyerId = buyerId;
        this.phoneId = phoneId;
        this.purchasePrice = purchasePrice;
        this.purchaseQuantity = purchaseQuantity;
        this.purchasedAt = purchasedAt;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(long phoneId) {
        this.phoneId = phoneId;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }
}
