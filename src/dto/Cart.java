package dto;

import java.time.LocalDateTime;

public class Cart {
    private long cartId;
    private long buyerId;
    private long phoneId;
    private int quantity;
    private LocalDateTime createdAt;

    public Cart() {}

    public Cart(long cartId, long buyerId, long phoneId, int quantity, LocalDateTime createdAt) {
        this.cartId = cartId;
        this.buyerId = buyerId;
        this.phoneId = phoneId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
