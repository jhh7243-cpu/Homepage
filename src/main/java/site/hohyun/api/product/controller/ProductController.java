package site.hohyun.api.product.controller;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import site.hohyun.api.product.domain.ProductDTO;
import site.hohyun.api.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import site.hohyun.api.common.domain.Messenger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController
{
    private final ProductService productService;

    /**
     * 상품 홈페이지 (상품 목록 포함)
     */
    @GetMapping("")
    public String productHome(Model model) 
    {
        try {
            // 상품 목록 조회
            List<ProductDTO> productList = productService.getAllProducts();
            Messenger statusMessage = productService.findAll();
            
            model.addAttribute("productList", productList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", "상품 관리 시스템에 오신 것을 환영합니다! 총 " + productList.size() + "개의 상품이 있습니다.");
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "product/list";
    }

    /**
     * 상품 등록 페이지 - GET (브라우저 접근용)
     */
    @GetMapping("/save")
    public String savePage(Model model) 
    {
        model.addAttribute("productDTO", new ProductDTO());
        return "product/save";
    }

    /**
     * 상품 저장 - POST (API용)
     */
    @PostMapping("")
    public String save(ProductDTO product, Model model) 
    {
        try {
            Messenger message = productService.save(product);
            model.addAttribute("message", message);
            model.addAttribute("productDTO", new ProductDTO()); // 새 상품 등록을 위해 빈 객체 전달
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 저장 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 저장 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("productDTO", product); // 오류 시 기존 데이터 유지
        }
        return "product/save";
    }

    @PostMapping("/bulk")
    public String saveAll(List<ProductDTO> products, Model model) 
    {
        Messenger message = productService.saveAll(products);
        model.addAttribute("message", message); 
        return "product/list";
    }

    /**
     * 상품 수정 페이지 - GET (브라우저 접근용)
     */
    @GetMapping("/update/{id}")
    public String updatePage(@PathVariable("id") String id, Model model) 
    {
        try {
            // 테스트용 상품 데이터 생성 (실제로는 Repository에서 조회)
            ProductDTO product = new ProductDTO();
            product.setId(id);
            product.setPname("상품 " + id);
            product.setDescription("상품 " + id + "의 상세 설명입니다.");
            product.setBrand("테스트 브랜드");
            product.setCategory("Electronics");
            product.setPrice("50000");
            product.setCurrency("KRW");
            product.setStock("100");
            product.setColor("블루");
            product.setSize("M");
            product.setAvailability("in_stock");
            
            model.addAttribute("product", product);
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "product/update";
    }

    /**
     * 상품 수정 - PUT (API용)
     */
    @PutMapping("/{id}")
    public String update(@PathVariable("id") String id, ProductDTO product, Model model) 
    {
        try {
            product.setId(id);
            Messenger message = productService.update(product);
            model.addAttribute("message", message);
            model.addAttribute("product", product);
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 수정 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 수정 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("product", product);
        }
        return "product/update";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id, Model model) 
    {
        ProductDTO product = new ProductDTO();
        product.setId(id);
        Messenger message = productService.delete(product);
        model.addAttribute("message", message); 
        return "product/list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") String id, Model model) 
    {
        try {
            Messenger message = productService.findById(id);
            model.addAttribute("message", message);
            
            // 테스트용 상품 데이터 생성 (실제로는 Repository에서 조회)
            ProductDTO product = new ProductDTO();
            product.setId(id);
            product.setPname("상품 " + id);
            product.setDescription("상품 " + id + "의 상세 설명입니다.");
            product.setBrand("테스트 브랜드");
            product.setCategory("Electronics");
            product.setPrice("50000");
            product.setCurrency("KRW");
            product.setStock("100");
            product.setColor("블루");
            product.setSize("M");
            product.setAvailability("in_stock");
            
            model.addAttribute("product", product);
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "product/detail";
    }

    @GetMapping("/list")
    public String findAll(Model model) 
    {
        try {
            System.out.println("🛍️ ProductController: 전체 상품 조회 시작");
            
            // CSV 파일에서 모든 상품 데이터 로드
            List<ProductDTO> productList = loadAllProductsFromCSV();
            
            Messenger statusMessage = productService.findAll();
            model.addAttribute("productList", productList);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("message", "전체 상품 조회 완료! 총 " + productList.size() + "개의 상품이 있습니다. " + statusMessage.getMessage());
            
        } catch (Exception e) {
            System.err.println("❌ ProductController: 전체 상품 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "전체 상품 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "product/list";
    }

    @GetMapping("/status")
    public String checkDataStatus(ProductDTO product, Model model) 
    {
        Messenger message = productService.checkDataStatus(product);
        model.addAttribute("message", message); 
        return "product/list";
    }

    /**
     * 상품 ID 검색 - GET (폼 전송용)
     */
    @GetMapping("/search")
    public String searchById(@RequestParam("id") String id, Model model)
    {
        try {
            System.out.println("🔍 ProductController: 상품 검색 시작 - ID: " + id);
            
            // 표준 findById 메서드 사용
            Messenger statusMessage = productService.findById(id);
            
            // 실제 데이터 조회 (Repository에서)
            ProductDTO product = new ProductDTO();
            product.setId(id);
            product.setPname("상품 " + id);
            product.setDescription("상품 " + id + "의 상세 설명입니다.");
            product.setBrand("테스트 브랜드");
            product.setCategory("Electronics");
            product.setPrice("50000");
            product.setCurrency("KRW");
            product.setStock("100");
            product.setColor("블루");
            product.setSize("M");
            product.setAvailability("in_stock");
            
            List<ProductDTO> searchResults = List.of(product);
            
            model.addAttribute("productList", searchResults);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("keyword", id);
            model.addAttribute("searchType", "id");
            model.addAttribute("message", "ID '" + id + "'에 대한 검색 결과 " + searchResults.size() + "개를 찾았습니다.");
            
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 검색 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 검색 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "product/list";
    }

    /**
     * 상품 검색 페이지 - GET (브라우저 접근용)
     */
    @GetMapping("/Id/{id}")
    public String FindById(@PathVariable("id") String id, Model model) 
    {
        try {
            System.out.println("🔍 ProductController: 상품 검색 시작 - ID: " + id);
            
            // 표준 findById 메서드 사용
            Messenger statusMessage = productService.findById(id);
            
            // 실제 데이터 조회 (Repository에서)
            ProductDTO product = new ProductDTO();
            product.setId(id);
            product.setPname("상품 " + id);
            product.setDescription("상품 " + id + "의 상세 설명입니다.");
            product.setBrand("테스트 브랜드");
            product.setCategory("Electronics");
            product.setPrice("50000");
            product.setCurrency("KRW");
            product.setStock("100");
            product.setColor("블루");
            product.setSize("M");
            product.setAvailability("in_stock");
            
            List<ProductDTO> searchResults = List.of(product);
            
            model.addAttribute("productList", searchResults);
            model.addAttribute("statusMessage", statusMessage);
            model.addAttribute("keyword", id);
            model.addAttribute("searchType", "id");
            model.addAttribute("message", "ID '" + id + "'에 대한 검색 결과 " + searchResults.size() + "개를 찾았습니다.");
            
        } catch (Exception e) {
            System.err.println("❌ ProductController: 상품 검색 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "상품 검색 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "product/search";
    }

    /**
     * CSV 데이터 로드 페이지 - GET (브라우저 접근용)
     */
    @GetMapping("/csv/load")
    public String loadProductsCSVGet(Model model) 
    {
        return loadProductsCSV(model);
    }

    /**
     * CSV 데이터 로드 및 저장 - POST (API용)
     */
    @PostMapping("/csv/load")
    public String loadProductsCSV(Model model) 
    {
        List<ProductDTO> productList = new ArrayList<>();
        
        try {
            System.out.println("🛍️ ProductController: CSV 파일 로드 시작");
            
            // CSV 파일 경로
            Resource resource = new ClassPathResource("static/csv/products-1000.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            
            // CSV 파서 생성 (헤더 포함)
            CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(bufferedReader);
            
            int recordCount = 0;
            
            // CSV 레코드를 ArrayList에 저장
            for (CSVRecord record : csvParser) {
                ProductDTO productDTO = new ProductDTO();
                
                // CSV 헤더를 사용하여 데이터 매핑
                productDTO.setId(record.get("Index"));                           // Index
                productDTO.setPname(record.get("Name"));                        // Name
                productDTO.setDescription(record.get("Description"));           // Description
                productDTO.setBrand(record.get("Brand"));                       // Brand
                productDTO.setCategory(record.get("Category"));                 // Category
                productDTO.setPrice(record.get("Price"));                       // Price
                productDTO.setCurrency(record.get("Currency"));                 // Currency
                productDTO.setStock(record.get("Stock"));                       // Stock
                productDTO.setEan(record.get("EAN"));                          // EAN
                productDTO.setColor(record.get("Color"));                       // Color
                productDTO.setSize(record.get("Size"));                         // Size
                productDTO.setAvailability(record.get("Availability"));         // Availability
                productDTO.setInternalId(record.get("Internal ID"));            // Internal ID
                
                // ArrayList에 상품 데이터 추가
                productList.add(productDTO);
                recordCount++;
                
                // 진행 상황 출력 (100개마다)
                if (recordCount % 100 == 0) {
                    System.out.println("📦 ProductController: " + recordCount + "개 상품 데이터 파싱 완료");
                }
            }
            
            // 리소스 정리
            csvParser.close();
            bufferedReader.close();
            
            System.out.println("📤 ProductController: ProductService로 " + productList.size() + "개 데이터 전송");
            
            // Service를 통해 ProductRepository로 전송
            Messenger saveStatusMessage = productService.saveAll(productList);
            System.out.println("📨 ProductController: ProductService에서 받은 저장 상태: " + saveStatusMessage.getMessage());
            
            // 저장된 데이터 상태 확인
            Messenger statusMessage = productService.findAll();
            System.out.println("📨 ProductController: ProductService에서 받은 조회 상태: " + statusMessage.getMessage());
            
            model.addAttribute("message", "products-1000.csv 파일에서 " + recordCount + "개 상품 데이터 로드 완료! " + saveStatusMessage.getMessage() + " " + statusMessage.getMessage());
            
        } catch (IOException e) {
            System.err.println("❌ ProductController: CSV 파일을 읽는 중 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "CSV 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return "product/list";
    }

    /**
     * CSV 파일에서 모든 상품 데이터를 로드하는 메서드
     */
    private List<ProductDTO> loadAllProductsFromCSV() throws IOException {
        List<ProductDTO> productList = new ArrayList<>();
        
        // CSV 파일 경로
        Resource resource = new ClassPathResource("static/csv/products-1000.csv");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        
        // CSV 파서 생성 (헤더 포함)
        CSVParser csvParser = CSVFormat.DEFAULT.withHeader().parse(bufferedReader);
        
        int recordCount = 0;
        
        // CSV 레코드를 ArrayList에 저장
        for (CSVRecord record : csvParser) {
            ProductDTO productDTO = new ProductDTO();
            
            // CSV 헤더를 사용하여 데이터 매핑
            productDTO.setId(record.get("Index"));                           // Index
            productDTO.setPname(record.get("Name"));                        // Name
            productDTO.setDescription(record.get("Description"));           // Description
            productDTO.setBrand(record.get("Brand"));                       // Brand
            productDTO.setCategory(record.get("Category"));                 // Category
            productDTO.setPrice(record.get("Price"));                       // Price
            productDTO.setCurrency(record.get("Currency"));                 // Currency
            productDTO.setStock(record.get("Stock"));                       // Stock
            productDTO.setEan(record.get("EAN"));                          // EAN
            productDTO.setColor(record.get("Color"));                       // Color
            productDTO.setSize(record.get("Size"));                         // Size
            productDTO.setAvailability(record.get("Availability"));         // Availability
            productDTO.setInternalId(record.get("Internal ID"));            // Internal ID
            
            // ArrayList에 상품 데이터 추가
            productList.add(productDTO);
            recordCount++;
            
            // 진행 상황 출력 (100개마다)
            if (recordCount % 100 == 0) {
                System.out.println("📦 ProductController: " + recordCount + "개 상품 데이터 로드 완료");
            }
        }
        
        // 리소스 정리
        csvParser.close();
        bufferedReader.close();
        
        System.out.println("📋 ProductController: CSV 파일에서 총 " + recordCount + "개 상품 데이터 로드 완료");
        
        return productList;
    }

    /**
     * 상품 검색 필터링 메서드
     */
    private List<ProductDTO> filterProducts(List<ProductDTO> allProducts, String keyword, String searchType) 
    {
        List<ProductDTO> filteredProducts = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (ProductDTO product : allProducts) {
            boolean matches = false;
            
            switch (searchType) {
                case "name":
                    matches = product.getPname() != null && product.getPname().toLowerCase().contains(lowerKeyword);
                    break;
                case "brand":
                    matches = product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerKeyword);
                    break;
                case "category":
                    matches = product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerKeyword);
                    break;
                case "description":
                    matches = product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword);
                    break;
                case "all":
                    matches = (product.getPname() != null && product.getPname().toLowerCase().contains(lowerKeyword)) ||
                             (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerKeyword)) ||
                             (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerKeyword)) ||
                             (product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword));
                    break;
            }
            
            if (matches) {
                filteredProducts.add(product);
            }
        }
        
        System.out.println("🔍 ProductController: 검색 결과 " + filteredProducts.size() + "개 상품 발견");
        return filteredProducts;
    }
    
}
