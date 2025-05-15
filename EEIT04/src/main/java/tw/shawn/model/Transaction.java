package tw.shawn.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private int userId;
    private Date date;
    private String description;
    private BigDecimal amount;
    private String type; // "income" 或 "expense"

    // 建構子
    public Transaction() {
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // 你可以在這裡加入其他與交易相關的屬性和方法
}