package com.medicare.repository;

import com.medicare.domain.CitaMedica;
import com.medicare.domain.Especialidad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {

    // Buscar citas por paciente y estado
    @Query("""
           SELECT c
           FROM CitaMedica c
           WHERE c.paciente.id = :pacienteId
           AND c.estado = :estado
           """)
    List<CitaMedica> buscarPorPacienteEstado(
            @Param("pacienteId") Long pacienteId,
            @Param("estado") String estado
    );

    // Historial completo del paciente
    List<CitaMedica> findByPacienteId(Long pacienteId);

    // Contar citas por especialidad
    @Query("""
           SELECT COUNT(c)
           FROM CitaMedica c
           WHERE c.especialidad = :especialidad
           AND c.fechaHora BETWEEN :inicio AND :fin
           """)
    Long contarPorEspecialidadFecha(
            @Param("especialidad") Especialidad especialidad,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    // Evitar citas duplicadas
    boolean existsByPacienteIdAndFechaHora(
            Long pacienteId,
            LocalDateTime fechaHora
    );

}
