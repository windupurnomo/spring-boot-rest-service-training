package id.co.angkasapura2.repositories;

import id.co.angkasapura2.entities.Airline;
import id.co.angkasapura2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, String> {
}
