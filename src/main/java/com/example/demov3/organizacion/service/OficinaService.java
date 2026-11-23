package com.example.demov3.organizacion.service;

import com.example.demov3.organizacion.web.dto.OficinaDto;
import com.example.demov3.organizacion.domain.Oficina;
import com.example.demov3.organizacion.repository.OficinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OficinaService {

    private final OficinaRepository oficinaRepository;

    public OficinaService(OficinaRepository oficinaRepository) {
        this.oficinaRepository = oficinaRepository;
    }

    public List<OficinaDto> getAll() {
        return oficinaRepository.findAll().stream().map(this::toDto).toList();
    }

    public Optional<OficinaDto> getById(Long id) {
        return oficinaRepository.findById(id).map(this::toDto);
    }

    public OficinaDto create(OficinaDto dto) {
        Oficina oficina = new Oficina();
        copyToEntity(dto, oficina);
        return toDto(oficinaRepository.save(oficina));
    }

    public Optional<OficinaDto> update(Long id, OficinaDto dto) {
        return oficinaRepository.findById(id).map(oficina -> {
            copyToEntity(dto, oficina);
            return toDto(oficinaRepository.save(oficina));
        });
    }

    public boolean delete(Long id) {
        if (!oficinaRepository.existsById(id)) {
            return false;
        }
        oficinaRepository.deleteById(id);
        return true;
    }

    private OficinaDto toDto(Oficina o) {
        return new OficinaDto(o.getId(), o.getEntidad(), o.getUnidad(), o.getCodofic(), o.getNomofic(), o.getObserv());
    }

    // FEULT, USUAR y API_ESTADO no se publican: no se tocan desde el DTO
    private void copyToEntity(OficinaDto dto, Oficina o) {
        o.setEntidad(dto.entidad());
        o.setUnidad(dto.unidad());
        o.setCodofic(dto.codofic());
        o.setNomofic(dto.nomofic());
        o.setObserv(dto.observ());
    }
}
