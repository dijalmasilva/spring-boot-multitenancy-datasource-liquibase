package br.com.dijalmasilva.springbootmultitenancyliquibase.product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 01:55
 **/
interface ProductRepository extends JpaRepository<Product, Long> {
}
