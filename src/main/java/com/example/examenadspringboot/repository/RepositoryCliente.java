package com.example.examenadspringboot.repository;

import com.example.examenadspringboot.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryCliente extends JpaRepository<Cliente, Long> {

    Cliente getClienteById(Long id);

    @Query("SELECT c FROM Cliente c WHERE c.estado = 'Activo' AND c.total > :total")
    List<Cliente> getActivosPorVentaMayor(Double total);

    @Query(value = "SELECT SUM(c.total) FROM Cliente c")
    Double getSumaTotales();

    @Query("SELECT AVG(c.total) FROM Cliente c WHERE c.estado = 'Activo'")
    Double getMediaTotalClientesActivos();

    @Query("SELECT COUNT(c) FROM Cliente c WHERE c.estado = 'Inactivo' AND c.total > 0")
    Integer getNumeroClientesInactivosTotalMayoCero();
}
