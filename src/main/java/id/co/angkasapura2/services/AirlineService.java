package id.co.angkasapura2.services;

import id.co.angkasapura2.entities.Airline;
import id.co.angkasapura2.repositories.AirlineRepository;
import id.co.angkasapura2.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AirlineService {

    @Autowired
    private AirlineRepository airlineRepository;

    public Response create(Airline airline){
        airline.setId(UUID.randomUUID().toString());
        airline.setRegisterDate(new Date());
        airlineRepository.save(airline);
        return new Response(airline);
    }

    public Response update(Airline airline){
        Airline airlineDb = airlineRepository.findOne(airline.getId());
        if (airlineDb == null){
            return new Response("Airline tidak ditemukan");
        }else {
            airlineDb.setName(airline.getName());
            airlineDb.setPhone(airline.getPhone());
            airlineDb.setAddress(airline.getAddress());
            airlineDb.setPoint(airline.getPoint());
            airlineDb.setStatus(airline.isStatus());
            airlineRepository.save(airlineDb);
            return new Response(airlineDb);
        }
    }


    public List<Airline> get(){
        return airlineRepository.findAll();
    }

    public Airline get(String id){
        return airlineRepository.findOne(id);
    }

    public Response delete(String id){
        Airline airlineDb = airlineRepository.findOne(id);
        if (airlineDb == null) return new Response("Airline tidak ditemukan");
        airlineRepository.delete(id);
        Airline airlineDb2 = airlineRepository.findOne(id);
        return new Response(airlineDb2 == null);
    }


}
