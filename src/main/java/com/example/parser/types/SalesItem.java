package com.example.parser.types;

import com.example.parser.annotations.ParserType;
import com.fasterxml.jackson.annotation.*;
import com.google.common.base.Objects;

import java.time.LocalDateTime;

@ParserType(key = "sales-item")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonPropertyOrder(alphabetic = true)
public class SalesItem {
    @JsonProperty("Transaction_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/u H:m")
    private final LocalDateTime transactionDate;

    @JsonProperty("Product")
    private String product;

    @JsonProperty("Price")
    private double price;


    @JsonCreator
    public SalesItem(
            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "M/d/u H:m")
            @JsonProperty("Transaction_date") LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }


    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }

    @JsonSetter("Price")
    private void setPrice(String price) {
        this.price = Double.parseDouble(price.replace(",", ""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesItem salesItem = (SalesItem) o;
        return Objects.equal(getPrice(), salesItem.getPrice()) &&
                Objects.equal(getTransactionDate(), salesItem.getTransactionDate()) &&
                Objects.equal(getProduct(), salesItem.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getTransactionDate(), getProduct(), getPrice());
    }

    @Override
    public String toString() {
        return "SalesItem{" +
                "transactionDate='" + transactionDate + '\'' +
                ", product='" + product + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
