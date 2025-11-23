package org.iplacex.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

  @Autowired
  private IArtistaRepository artistaRepo;

  //POST
  @CrossOrigin(methods = RequestMethod.POST)
  @PostMapping(
    value = "/artista",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
    Artista temp = artistaRepo.insert(artista);
    return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
  }

  //GET all
  @CrossOrigin(methods = RequestMethod.GET)
  @GetMapping(
    value = "/artistas",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
    List<Artista> artistas = artistaRepo.findAll();

    if (artistas.size() == 0) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(artistas, null, HttpStatus.OK);
  }

  //GET by id
  @CrossOrigin(methods = RequestMethod.GET)
  @GetMapping(
    value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id) {
    Optional<Artista> temp = artistaRepo.findById(id);

    if(!temp.isPresent()) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
  }

  //UPDATE
  @CrossOrigin(methods = RequestMethod.PUT)
  @PutMapping(
    value = "/artista/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable("id") String id, 
                                                            @RequestBody Artista artista) {
    if(!artistaRepo.existsById(id)) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    artista._id = id;
    Artista temp = artistaRepo.save(artista);

    return new ResponseEntity<>(temp, null, HttpStatus.OK);
  }

  //DELETE
  @CrossOrigin(methods = RequestMethod.DELETE)
  @DeleteMapping(
    value = "/artista/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Artista> HandleDeleteArtistaRequest(@PathVariable("id") String id) {
    if(!artistaRepo.existsById(id)) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    Artista temp = artistaRepo.findById(id).get();
    artistaRepo.deleteById(id);

    return new ResponseEntity<>(temp, null, HttpStatus.GONE);
  }
}
