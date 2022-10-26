package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select u from Role u where u.name = :name")
    Role findByName(@Param("name") String name);

}
