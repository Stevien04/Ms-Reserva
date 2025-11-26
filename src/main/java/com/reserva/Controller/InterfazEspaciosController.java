package com.reserva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InterfazEspaciosController {

    @GetMapping({"/", "/espacios"})
    public String mostrarInterfaz() {
        return "Interfazespacios";
    }
}