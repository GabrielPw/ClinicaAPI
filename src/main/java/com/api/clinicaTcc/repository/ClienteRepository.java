package com.api.clinicaTcc.repository;

import com.api.clinicaTcc.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE cliente c SET c.telefone = :number where c.id = :id")    // <-- Atualiza Telefone por Id;
    void updatePhone(@Param("id") Long id, @Param("number") String phoneNumber);
}
