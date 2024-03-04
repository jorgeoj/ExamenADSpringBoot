package com.example.examenadspringboot.controller;

import com.example.examenadspringboot.entity.Cliente;
import com.example.examenadspringboot.repository.RepositoryCliente;
import com.example.examenadspringboot.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private RepositoryCliente repositoryCliente;
    @Autowired
    private SecurityService securityService;


    // Crea un nuevo cliente en la lista
    @PostMapping("/post")
    public ResponseEntity<Cliente> nuevo(@RequestBody Cliente cliente, @RequestParam String token) {
        if (SecurityService.validateToken(token)) {
            return new ResponseEntity<>(repositoryCliente.save(cliente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // Devuelve los datos del cliente con el id introducido
    @GetMapping("/id/{id}")
    public Cliente getClientePorId(@PathVariable Long id){
        return repositoryCliente.getClienteById(id);
    }

    // Devuelve la lista de clientes activos con total mayor al indicado
    @GetMapping("/activosVentaMayor/{total}")
    public List<Cliente> getClientesActivosConVentasMayor(@PathVariable Double total){
        return repositoryCliente.getActivosPorVentaMayor(total);
    }

    // Obtiene resumen estadistico
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getClienteStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("sumaTotales:", repositoryCliente.getSumaTotales());
        stats.put("mediaTotalClientesActivos", repositoryCliente.getMediaTotalClientesActivos());
        stats.put("numeroClientesInactivosTotalMayoCero", repositoryCliente.getNumeroClientesInactivosTotalMayoCero());

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
