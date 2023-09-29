package com.henry.api.controllere;


import com.henry.api.domain.consulta.AgendaDeConsultaService;
import com.henry.api.domain.consulta.DatosAgendaConsulta;
import com.henry.api.domain.consulta.DatosDetalleConsulta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar (@RequestBody @Valid DatosAgendaConsulta datos) { //tomar lo datos del body + validar

        service.agendar(datos);

        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }

}
