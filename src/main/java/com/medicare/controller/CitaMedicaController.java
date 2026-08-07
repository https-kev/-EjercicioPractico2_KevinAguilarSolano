package com.medicare.controller;

import com.medicare.domain.CitaMedica;
import com.medicare.domain.Usuario;
import com.medicare.domain.Especialidad;
import com.medicare.repository.UsuarioRepository;
import com.medicare.service.CitaMedicaService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citas")
public class CitaMedicaController {

    private final CitaMedicaService citaService;

    private final UsuarioRepository usuarioRepository;

    public CitaMedicaController(
            CitaMedicaService citaService,
            UsuarioRepository usuarioRepository
    ) {

        this.citaService = citaService;
        this.usuarioRepository = usuarioRepository;

    }

    // Mostrar formulario de cita
    @GetMapping("/nueva")
    public String nuevaCita(
            Model model
    ) {

        model.addAttribute(
                "cita",
                new CitaMedica()
        );

        // Enviar enum al formulario
        model.addAttribute(
                "especialidades",
                Especialidad.values()
        );

        return "citas/formulario";

    }

    // Guardar cita
    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute CitaMedica cita,
            Authentication authentication,
            Model model
    ) {

        Usuario paciente
                = usuarioRepository
                        .findByEmail(
                                authentication.getName()
                        )
                        .orElseThrow();

        // Asignación automática del paciente
        cita.setPaciente(paciente);

        try {

            citaService.registrarCita(cita);

        } catch (RuntimeException e) {

            model.addAttribute(
                    "error",
                    e.getMessage()
            );

            model.addAttribute(
                    "especialidades",
                    Especialidad.values()
            );

            return "citas/formulario";

        }

        return "redirect:/citas/historial";

    }

    // Historial del paciente
    @GetMapping("/historial")
    public String historial(
            Authentication authentication,
            Model model
    ) {

        Usuario paciente
                = usuarioRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow();

        model.addAttribute(
                "citas",
                citaService.historialPaciente(
                        paciente.getId()
                )
        );

        return "citas/historial";

    }

}
