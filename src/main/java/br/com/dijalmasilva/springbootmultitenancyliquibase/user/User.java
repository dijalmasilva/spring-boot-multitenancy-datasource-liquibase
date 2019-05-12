package br.com.dijalmasilva.springbootmultitenancyliquibase.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 02:02
 **/
@Data
@Entity
@Table(name = "USER_TB")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;
}
