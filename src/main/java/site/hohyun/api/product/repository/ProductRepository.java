package site.hohyun.api.product.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.product.domain.ProductDTO;

@Repository
public class ProductRepository
{
    

    public Messenger save(ProductDTO product) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("Product saved successfully");
        return message;
    }

    public Messenger saveAll(List<ProductDTO> products) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("All products saved successfully");
        return message;
    }

    public Messenger update(ProductDTO product) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("Product updated successfully");
        return message;
    }

    public Messenger delete(ProductDTO product) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("Product deleted successfully");
        return message;
    }

    public Messenger findById(String id) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("Product found successfully");
        return message;
    }

    public Messenger findAll() {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("All products found successfully");
        return message;
    }

    public Messenger checkDataStatus(ProductDTO product) {
        Messenger message = new Messenger();
        message.setCode(200);
        message.setMessage("Data status checked successfully");
        return message;
    }
    
}
