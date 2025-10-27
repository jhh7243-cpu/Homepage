package site.hohyun.api.product.domain;
import lombok.Getter;

@Getter
public class ProductVO 
{
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
