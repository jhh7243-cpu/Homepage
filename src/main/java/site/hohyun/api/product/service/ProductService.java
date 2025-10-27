package site.hohyun.api.product.service;

import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.product.domain.ProductDTO;
import java.util.List;

//추상화 인터페이스
public interface ProductService 
{
     Messenger save(ProductDTO productDTO);
     Messenger saveAll(List<ProductDTO> products);
     Messenger update(ProductDTO productDTO);
     Messenger delete(ProductDTO productDTO);
     Messenger findById(String id);   //쿼리
     Messenger findAll();   //쿼리
     List<ProductDTO> getAllProducts();   //상품 목록 조회
     Messenger checkDataStatus(ProductDTO productDTO);
}
