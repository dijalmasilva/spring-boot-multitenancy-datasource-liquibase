package br.com.dijalmasilva.springbootmultitenancyliquibase.product;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 01:52
 **/
@Data
@Entity
@Table(name = "PRODUCT_TB")
public class Product implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "VALUE", nullable = false)
    private BigDecimal value;
}
