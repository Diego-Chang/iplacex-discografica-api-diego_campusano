package org.iplacex.discografia.discos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.iplacex.discografia.artistas.Artista;
import org.iplacex.discografia.artistas.IArtistaRepository;
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
public class DiscoController {

  @Autowired
  private IDiscoRepository discoRepo;
  @Autowired
  private IArtistaRepository artistaRepo;

  //POST
  @CrossOrigin(methods = RequestMethod.POST)
  @PostMapping(
    value = "/disco",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Disco> HandleInsertDiscoRequest(@RequestBody Disco disco) {
    if (!artistaRepo.existsById(disco.idArtista)){
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    Disco temp = discoRepo.insert(disco);
    return new ResponseEntity<>(temp, null, HttpStatus.CREATED);
  }

  //GET all
  @CrossOrigin(methods = RequestMethod.GET)
  @GetMapping(
    value = "/discos",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
    List<Disco> discos = discoRepo.findAll();

    if (discos.size() == 0) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(discos, null, HttpStatus.OK);
  }

  //GET by id
  @CrossOrigin(methods = RequestMethod.GET)
  @GetMapping(
    value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id) {
    Optional<Disco> temp = discoRepo.findById(id);

    if(!temp.isPresent()) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(temp.get(), null, HttpStatus.OK);
  }

  //GET by artist
  @CrossOrigin(methods = RequestMethod.GET)
  @GetMapping(
    value = "/artista/{id}/discos",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<ArrayList<Object>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id) {
    if (!artistaRepo.existsById(id)) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    List<Disco> discos = discoRepo.findDiscosByIdArtista(id);
    Optional<Artista> artista = artistaRepo.findById(id);

    ArrayList<Object> respuesta = new ArrayList<>();
    respuesta.add(artista);
    respuesta.add(discos);

    return new ResponseEntity<>(respuesta, null, HttpStatus.OK);
  }

   //UPDATE
  @CrossOrigin(methods = RequestMethod.PUT)
  @PutMapping(
    value = "/disco/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Disco> HandleUpdateDiscoRequest(@PathVariable("id") String id, 
                                                            @RequestBody Disco disco) {
    if(!discoRepo.existsById(id)) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    disco._id = id;
    Disco temp = discoRepo.save(disco);

    return new ResponseEntity<>(temp, null, HttpStatus.OK);
  }

  //DELETE
  @CrossOrigin(methods = RequestMethod.DELETE)
  @DeleteMapping(
    value = "/disco/{id}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Disco> HandleDeleteDiscoRequest(@PathVariable("id") String id) {
    if(!discoRepo.existsById(id)) {
      return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    Disco temp = discoRepo.findById(id).get();
    discoRepo.deleteById(id);

    return new ResponseEntity<>(temp, null, HttpStatus.GONE);
  }
}
