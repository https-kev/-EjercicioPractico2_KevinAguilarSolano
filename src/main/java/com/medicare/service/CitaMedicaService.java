package com.medicare.service;

import com.medicare.domain.CitaMedica;
import com.medicare.repository.CitaMedicaRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaMedicaService {

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaService(
            CitaMedicaRepository citaMedicaRepository
    ) {

        this.citaMedicaRepository = citaMedicaRepository;

    }

    public CitaMedica registrarCita(
            CitaMedica cita
    ) {

        if (cita.getFechaHora()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "No se pueden registrar citas en fechas pasadas"
            );

        }

        boolean existeCita
                = citaMedicaRepository
                        .existsByPacienteIdAndFechaHora(
                                cita.getPaciente().getId(),
                                cita.getFechaHora()
                        );

        if (existeCita) {

            throw new RuntimeException(
                    "El paciente ya tiene una cita en ese horario"
            );

        }

        cita.setEstado(
                "PROGRAMADA"
        );

        return citaMedicaRepository.save(cita);

    }

    public List<CitaMedica> listarTodas() {

        return citaMedicaRepository.findAll();

    }

    public List<CitaMedica> historialPaciente(
            Long pacienteId
    ) {

        return citaMedicaRepository
                .buscarPorPacienteEstado(
                        pacienteId,
                        "PROGRAMADA"
                );

    }

    public void cambiarEstado(
            Long id,
            String estado
    ) {

        CitaMedica cita
                = citaMedicaRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Cita no encontrada"
                                )
                        );

        cita.setEstado(
                estado
        );

        citaMedicaRepository.save(
                cita
        );

    }

}
