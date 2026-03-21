package com.dcj.ecommerce_platform_be.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-YYYY")
    private Date releaseDate;
    private boolean isAvailable;
    private int quantity;
    private String imageName;
    private String imageType;
    // @Lob -> Large Object
    @Lob
    private byte[] imageData;

    public Product(int i) {
        this.productId = i;
    }
}
