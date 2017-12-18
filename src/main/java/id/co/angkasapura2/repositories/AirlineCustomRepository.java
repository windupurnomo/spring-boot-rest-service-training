package id.co.angkasapura2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AirlineCustomRepository {

    @Autowired
    private EntityManager entityManager;

    public int activeAirline() {
        String raw = "SELECT count(*) from airline where status = true";
        Object result = entityManager.createNativeQuery(raw).getSingleResult();
        return (Integer) result;
    }

}
