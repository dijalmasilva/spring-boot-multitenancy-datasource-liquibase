package br.com.dijalmasilva.springbootmultitenancyliquibase.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 01:58
 **/
public interface ProductService {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);
}
