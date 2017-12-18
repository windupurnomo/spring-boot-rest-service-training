package id.co.angkasapura2.repositories;

import id.co.angkasapura2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);


    String myQuery = "select u.* from user u inner join " +
            "user_role ur on ur.user_id = u.id " +
            "inner join role r on r.id = ur.role_id " +
            "where r.name like :xxx and u.email = :email and u.name like :yyy";

    @Query(nativeQuery = true, value = myQuery)
    List<User> findCustomUsers(@Param("xxx") String xxx,
                               @Param("email") String y,
                               @Param("yyy") String z);
}
