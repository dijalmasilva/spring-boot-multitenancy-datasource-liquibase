package br.com.dijalmasilva.springbootmultitenancyliquibase.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 02:03
 **/
interface UserRepository extends JpaRepository<User, Long> {
}
