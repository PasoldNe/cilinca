package com.senai.cilinca.repositories;

import com.senai.cilinca.models.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    Optional<ConsultaEntity> findById(Long id);
}
