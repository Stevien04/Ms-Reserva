package com.reserva.controller;

import com.reserva.model.Espacio;
import com.reserva.service.EspacioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FormularioReservaController {

    private final EspacioService espacioService;

    public FormularioReservaController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping("/reservas/formulario")
    public String mostrarFormulario(
            @RequestParam(name = "espacioId", required = false) Integer espacioId,
            @RequestParam(name = "escuelaId", required = false) Integer escuelaId,
            Model model) {
        List<Espacio> espaciosDisponibles = espacioService.listarActivosPorEscuela(escuelaId);

        Espacio espacioSeleccionado = null;
        if (espacioId != null) {
            espacioSeleccionado = espacioService.buscarPorId(espacioId).orElse(null);
            if (espacioSeleccionado != null && espaciosDisponibles.stream().noneMatch(e -> e.getId().equals(espacioId))) {
                espaciosDisponibles.add(0, espacioSeleccionado);
            }

        }

        model.addAttribute("espacios", espaciosDisponibles);
        model.addAttribute("espacioIdSeleccionado", espacioId);
        model.addAttribute("espacioSeleccionDescripcion", espacioSeleccionado != null
                ? espacioSeleccionado.getCodigo() + " - " + espacioSeleccionado.getNombre()
                : null);

        return "FormularioReserva";
        }
    }
