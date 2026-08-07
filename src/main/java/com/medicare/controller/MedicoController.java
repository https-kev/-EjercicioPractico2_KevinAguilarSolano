package com.medicare.controller;

import com.medicare.service.CitaMedicaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    private final CitaMedicaService citaService;

    public MedicoController(
            CitaMedicaService citaService
    ) {

        this.citaService = citaService;

    }

    @GetMapping
    public String inicio() {

        return "medico/inicio";

    }

    @GetMapping("/citas")
    public String citas(
            Model model
    ) {

        model.addAttribute(
                "citas",
                citaService.listarTodas()
        );

        return "medico/citas";

    }

    @GetMapping("/completar/{id}")
    public String completar(
            @PathVariable Long id
    ) {

        citaService.cambiarEstado(
                id,
                "COMPLETADA"
        );

        return "redirect:/medico/citas";

    }

    @GetMapping("/cancelar/{id}")
    public String cancelar(
            @PathVariable Long id
    ) {

        citaService.cambiarEstado(
                id,
                "CANCELADA"
        );

        return "redirect:/medico/citas";

    }

}
