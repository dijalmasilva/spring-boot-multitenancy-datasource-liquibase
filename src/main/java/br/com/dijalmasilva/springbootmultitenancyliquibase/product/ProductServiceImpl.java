package br.com.dijalmasilva.springbootmultitenancyliquibase.product;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 01:55
 **/
@Service
@AllArgsConstructor
class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Page<Product> findAll(Pageable pageable) {
        return this.productRepository.findAll(pageable);
    }
}
