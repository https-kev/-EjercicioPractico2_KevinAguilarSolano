package com.medicare.controller;

import com.medicare.service.CitaMedicaService;
import com.medicare.repository.UsuarioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioRepository usuarioRepository;

    private final CitaMedicaService citaService;

    public AdminController(
            UsuarioRepository usuarioRepository,
            CitaMedicaService citaService
    ) {

        this.usuarioRepository = usuarioRepository;
        this.citaService = citaService;

    }

    //Panel principal administrador
    @GetMapping
    public String inicio() {

        return "admin/inicio";

    }

    //Gestión usuarios
    @GetMapping("/usuarios")
    public String usuarios(
            Model model
    ) {

        model.addAttribute(
                "usuarios",
                usuarioRepository.findAll()
        );

        return "admin/usuarios";

    }

    //Gestión citas
    @GetMapping("/citas")
    public String citas(
            Model model
    ) {

        model.addAttribute(
                "citas",
                citaService.listarTodas()
        );

        return "admin/citas";

    }

}
