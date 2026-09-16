package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discRepo;
    @Autowired
    private IArtistaRepository artistRepo;

    @PostMapping(value = "/disco", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disc) {
        if (!artistRepo.existsById(disc.idArtista)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Object res = discRepo.insert(disc);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(value = "/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/disco/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> optDisc = discRepo.findById(id);

        if (!discRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optDisc.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}/discos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id) {
        List<Disco> discosPorArtista = discRepo.findDiscosByIdArtista(id);

        if (discosPorArtista.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(discRepo.findDiscosByIdArtista(id), HttpStatus.OK);
    }

}
