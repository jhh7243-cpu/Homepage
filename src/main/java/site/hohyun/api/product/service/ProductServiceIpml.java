package site.hohyun.api.product.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import site.hohyun.api.common.domain.Messenger;
import site.hohyun.api.product.domain.ProductDTO;
import site.hohyun.api.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductServiceIpml implements ProductService
{
    private final ProductRepository productRepository;

    @Override
    public Messenger save(ProductDTO product) 
    {
        return productRepository.save(product);
    }

    @Override
    public Messenger saveAll(List<ProductDTO> products) 
    {
        return productRepository.saveAll(products);
    }

    @Override
    public Messenger update(ProductDTO product) {
        
        return productRepository.update(product);
    }

    @Override
    public Messenger delete(ProductDTO product) {
        return productRepository.delete(product);
    }

    @Override
    public Messenger findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Messenger findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        // 실제 구현에서는 Repository에서 상품 목록을 조회
        // 현재는 테스트용 더미 데이터 반환
        List<ProductDTO> productList = new ArrayList<>();
        
        // 테스트용 상품 데이터 생성
        for (int i = 1; i <= 5; i++) {
            ProductDTO product = new ProductDTO();
            product.setId(String.valueOf(i));
            product.setPname("테스트 상품 " + i);
            product.setDescription("이것은 테스트 상품 " + i + "입니다.");
            product.setBrand("테스트 브랜드");
            product.setCategory("Electronics");
            product.setPrice(String.valueOf(10000 + i * 1000));
            product.setCurrency("KRW");
            product.setStock(String.valueOf(100 - i * 10));
            product.setColor("블루");
            product.setSize("M");
            product.setAvailability("in_stock");
            productList.add(product);
        }
        
        return productList;
    }

    @Override
    public Messenger checkDataStatus(ProductDTO product) {
        return productRepository.checkDataStatus(product);
    }

}
