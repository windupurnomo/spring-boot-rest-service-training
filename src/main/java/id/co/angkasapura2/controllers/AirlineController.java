package id.co.angkasapura2.controllers;

import id.co.angkasapura2.entities.Airline;
import id.co.angkasapura2.enums.RoleEnum;
import id.co.angkasapura2.services.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AirlineController extends BaseController {

    @Autowired
    private AirlineService airlineService;


    @GetMapping("/airline")
    public List<Airline> getAirlines() {
        return airlineService.get();
    }

    @GetMapping("/airline/{id}")
    public Airline getAirlines(@PathVariable("id") String id) {
        return airlineService.get(id);
    }

    @PostMapping("/airline")
    public ResponseEntity create(@RequestHeader("Authorization") String token,
                          @RequestBody Airline airline){
        if (!authorize(RoleEnum.ADMIN, token)) return FORBIDDEN;
        return getHttpResponse(airlineService.create(airline));
    }

    @PutMapping("/airline/{id}")
    public ResponseEntity update(@RequestHeader("Authorization") String token,
                                 @PathVariable("id") String id, @RequestBody Airline airline){
        RoleEnum[] allowedRoles = {RoleEnum.ADMIN, RoleEnum.AIRLINE};
        if (!authorize(allowedRoles, token)) return FORBIDDEN;
        airline.setId(id);
        return getHttpResponse(airlineService.update(airline));
    }

    @DeleteMapping("/airline/{id}")
    public ResponseEntity delete(@RequestHeader("Authorization") String token,
                          @PathVariable("id") String id){
        if (!authorize(RoleEnum.ADMIN, token)) return FORBIDDEN;
        return getHttpResponse(airlineService.delete(id));
    }

}
