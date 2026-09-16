package org.iplacex.proyectos.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
@RequestMapping("/api")

public class ArtistaController {

    @Autowired
    private IArtistaRepository artistRepo;

    @PostMapping(value = "/artista", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista art) {
        if (artistRepo.existsById(art._id)) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        Object res = artistRepo.insert(art);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(value = "/artistas", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return new ResponseEntity<>(artistRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> optArtista = artistRepo.findById(id);
        if (!optArtista.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optArtista.get(), HttpStatus.OK);
    }

    @PutMapping(value = "/artista/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleUpdateArtistaRequest(@PathVariable("id") String id, @RequestBody Artista art) {
        if (!artistRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        art._id = id;

        Artista tempArtista = artistRepo.save(art);

        return new ResponseEntity<>(tempArtista, HttpStatus.OK);
    }

    @DeleteMapping(value = "/artista/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
        if (!artistRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Artista artista = artistRepo.findById(id).get();

        new ResponseEntity<>(artista, HttpStatus.OK);

        artistRepo.deleteById(id);

        return new ResponseEntity<>(artista, HttpStatus.OK);
    }

}
