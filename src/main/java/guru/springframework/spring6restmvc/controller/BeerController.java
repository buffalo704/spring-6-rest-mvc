package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

  public static final String BEER_PATH = "/api/v1/beer";
  public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";
  private final BeerService beerService;

  @PatchMapping(BEER_PATH_ID)
  public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
    beerService.patchBeerById(beerId, beer);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(BEER_PATH_ID)
  public ResponseEntity deleteBeer(@PathVariable("beerId") UUID beerId) {

    log.debug("Delete beer by id - in controller: {}", beerId);

    beerService.deleteById(beerId);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }


  @PutMapping(BEER_PATH_ID)
  public ResponseEntity updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {

    beerService.updateBeerById(beerId, beer);

    return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  @PostMapping(BEER_PATH)
  //@RequestMapping(method = RequestMethod.POST)
  public ResponseEntity handlePost(@RequestBody BeerDTO beer) {
    log.debug("Handle post - in controller: {}", beer);

    BeerDTO savedBeer = beerService.saveNewBeer(beer);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

    return new ResponseEntity(headers, HttpStatus.CREATED);
  }

  @GetMapping(value = BEER_PATH)
  public List<BeerDTO> listBeers() {
    log.debug("List beers - in controller");

    return beerService.listBeers();
  }

  @GetMapping(value=BEER_PATH_ID)
  public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {
    log.debug("Get beer by id - in controller: {}", beerId);

    return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
  }
}
