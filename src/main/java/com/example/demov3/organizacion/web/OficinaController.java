package com.example.demov3.organizacion.web;

import com.example.demov3.organizacion.web.dto.OficinaDto;
import com.example.demov3.organizacion.service.OficinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oficinas")
@Tag(name = "Oficina", description = "Gestion de Oficinas")
public class OficinaController {

    private static final Logger log = LoggerFactory.getLogger(OficinaController.class);

    private final OficinaService oficinaService;

    public OficinaController(OficinaService oficinaService) {
        this.oficinaService = oficinaService;
    }

    @PostMapping
    @Operation(summary = "Crear Oficina", description = "Registra una nueva Oficina")
    public OficinaDto createOficina(@RequestBody OficinaDto dto) {
        return oficinaService.create(dto);
    }

    @GetMapping
    @Operation(summary = "Listar Oficinas", description = "Obtiene todas las Oficinas")
    public List<OficinaDto> getAllOficinas() {
        return oficinaService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener Oficina", description = "Obtiene una Oficina por ID")
    @ApiResponse(responseCode = "404", description = "Registro no Encontrado")
    public ResponseEntity<OficinaDto> getOficinaById(@PathVariable Long id) {
        return oficinaService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Oficina", description = "Actualiza una Oficina existente")
    @ApiResponse(responseCode = "404", description = "Registro no Encontrado")
    public ResponseEntity<OficinaDto> updateOficina(@PathVariable Long id, @RequestBody OficinaDto dto) {
        return oficinaService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar Oficina", description = "Elimina una Oficina existente")
    @ApiResponse(responseCode = "204", description = "Eliminacion Exitosa")
    @ApiResponse(responseCode = "404", description = "Registro no Encontrado")
    public ResponseEntity<Void> deleteOficina(@PathVariable Long id) {
        if (!oficinaService.delete(id)) {
            log.warn("Oficina {} doesn't exist", id);
            return ResponseEntity.notFound().build();
        }
        log.info("Oficina {} was deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
