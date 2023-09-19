package com.henry.api.controllere;

import com.henry.api.medico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepositoy medicoRepositoy;


    // @RequestBody para indicar "parametro" recibe el BODY del POST (JSON) @VALID inidca que debe aplicar las validaciones
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRepositoy.save(new Medico(datosRegistroMedico));
    }

    /*
    //RETORNANDO UNA LISTA
    @GetMapping
    public List<DatosListadoMedico> listadoMedicos(){
        return medicoRepositoy.findAll().stream().map(DatosListadoMedico::new).toList(); // .stream().map RECORRE y CREA un nuevo listado de medico solo con los parametros pasados "DatosListadoMedico"
    }
    */

    //se creo un RECORD "DatosListadoMedico" para indicar solos los campos que queremos mostrar con el metodo GET
    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos (@PageableDefault(size = 10, sort = "nombre") Pageable pageable){ // PAGEABLEDEFAULT ajustamos los parametros a nuestras reglas de negocio
       // return medicoRepositoy.findAll(pageable).map(DatosListadoMedico::new);
        return medicoRepositoy.findByActivoTrue(pageable).map(DatosListadoMedico::new); // MAP RECORRE y CREA un nuevo listado de medico solo con los parametros pasados "DatosListadoMedico"
    }

    @PutMapping
    @Transactional // inicia y luego liberar la transaccion cuando termine el metodo y haga un commit
    public void actualizarMedico (@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){  //se crea un RECORD para indicar solo los parametros que se pueden modificar
        Medico medico = medicoRepositoy.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
    }

    // DELETE solo en la LOGICA
    @DeleteMapping("/{id}")
    @Transactional
    public void eliminarMedico (@PathVariable Long id) {
        Medico medico = medicoRepositoy.getReferenceById(id);
        medico.desactivarMedico();
    }

    /*  // DELETE en la base de datos
    public void eliminarMedico (@PathVariable Long id) {
        Medico medico = medicoRepositoy.getReferenceById(id);
        medicoRepositoy.delete(medico);
    }
*/
}
