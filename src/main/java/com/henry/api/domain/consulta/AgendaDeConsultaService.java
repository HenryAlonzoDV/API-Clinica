package com.henry.api.domain.consulta;

import com.henry.api.domain.medico.Medico;
import com.henry.api.domain.medico.MedicoRepository;
import com.henry.api.domain.paciente.Paciente;
import com.henry.api.domain.paciente.PacienteRepository;
import com.henry.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {

    // INCYECCION DE LOS REPOSITORIOS para poder hacer uso de sus pasametros
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar (DatosAgendaConsulta datos) {

        if (pacienteRepository.findById(datos.idPaciente()).isPresent()) { //ISPRESETE devuelve un boolean en caso de encontar al paciente
            throw new ValidacionDeIntegridad("El ID del Paciente no fue encontrado");  //se creo una exception personalisada
        }

        if (datos.idMedico()!= null && medicoRepository.existsById(datos.idMedico())) {
            throw new ValidacionDeIntegridad("El ID del Medico no fue encontrado");
        }

        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var medico = seleccionarMedico(datos);

        var consulta = new Consulta(null, medico, paciente, datos.fecha());

        consultaRepository.save(consulta);

    }

    private Medico seleccionarMedico(DatosAgendaConsulta datos) {

        if (datos.idMedico()!= null) {
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionar la especialidad a ser tratada");
        }
        return  medicoRepository.seleccionarMedicoConEspecialidadEnFecha (datos.especialidad(), datos.fecha());

    }

}
