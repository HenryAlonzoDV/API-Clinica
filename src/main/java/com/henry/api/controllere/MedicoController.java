package com.henry.api.controllere;

import com.henry.api.domain.direccion.DatosDireccion;
import com.henry.api.domain.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;


    // @RequestBody para indicar "parametro" recibe el BODY del POST (JSON) @VALID inidca que debe aplicar las validaciones
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();    // metodo CREATED necesita retornar una URI
        return ResponseEntity.created(url).body(datosRespuestaMedico);    //retorna 201 CREATED + toda la informacion del medico + URL
    }

    /*
    //RETORNANDO UNA LISTA
    @GetMapping
    public List<DatosListadoMedico> listadoMedicos(){
        return medicoRepositoy.findAll().stream().map(DatosListadoMedico::new).toList(); // .stream().map RECORRE y CREA un nuevo listado de medico solo con los parametros pasados "DatosListadoMedico"
    }
    */

    //se creo un RECORD "DatosListadoMedico" para indicar solos los campos que queremos mostrar con el metodo GET
    // PAGE para mostrar los datos administrados por paginacion
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos (@PageableDefault(size = 10) Pageable pageable){ // PAGEABLEDEFAULT ajustamos los parametros a nuestras reglas de negocio
       // return medicoRepositoy.findAll(pageable).map(DatosListadoMedico::new); //MOSTRAR todos los medicos
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new)) ; // MAP RECORRE y CREA un nuevo listado de medico solo con los parametros pasados "DatosListadoMedico" + filtrados por los ACTIVOS
    }

    @PutMapping
    @Transactional // inicia y luego liberar la transaccion cuando termine el metodo y haga un commit
    public ResponseEntity actualizarMedico (@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){  //se crea un RECORD para indicar solo los parametros que se pueden modificar
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),            //retorna 200 OK + toda la informacion del medico
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }

    // DELETE solo en la LOGICA
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico (@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();  //RESPONSEENTITY spara devolver los codigos rest (200, 201, 400, 404, etc)
    }

    /*  // DELETE en la base de datos
    public void eliminarMedico (@PathVariable Long id) {
        Medico medico = medicoRepositoy.getReferenceById(id);
        medicoRepositoy.delete(medico);
    }
    */

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico (@PathVariable Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        var datrosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datrosMedicos);
    }





}
