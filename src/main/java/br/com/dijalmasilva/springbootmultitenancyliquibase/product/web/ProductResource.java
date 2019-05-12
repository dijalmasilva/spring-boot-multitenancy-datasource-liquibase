package br.com.dijalmasilva.springbootmultitenancyliquibase.product.web;

import br.com.dijalmasilva.springbootmultitenancyliquibase.product.Product;
import br.com.dijalmasilva.springbootmultitenancyliquibase.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 01:57
 **/
@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductResource {

    private final ProductService productService;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return this.productService.save(product);
    }

    @GetMapping
    public Page<Product> findAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                 @RequestParam(defaultValue = "10", required = false) Integer size) {
        return this.productService.findAll(PageRequest.of(page, size));
    }
}
