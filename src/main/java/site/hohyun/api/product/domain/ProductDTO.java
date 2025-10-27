package site.hohyun.api.product.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO 
{
    //은닉화
    private String id;
    private String pname;
    private String description;
    private String brand;
    private String category;
    private String price;
    private String currency;
    private String stock;
    private String ean;
    private String color;
    private String size;
    private String availability;
    private String internalId;
    
}
