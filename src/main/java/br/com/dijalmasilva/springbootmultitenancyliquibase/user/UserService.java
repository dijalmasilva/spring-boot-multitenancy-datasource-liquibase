package br.com.dijalmasilva.springbootmultitenancyliquibase.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="dijalmasilva.github.io" target="_blank">Dijalma Silva</a>
 * @date 12/05/2019 02:03
 **/
public interface UserService {

    User save(User user);

    Page<User> findAll(Pageable pageable);
}
