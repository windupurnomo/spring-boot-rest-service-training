package id.co.angkasapura2;

import id.co.angkasapura2.entities.Role;
import id.co.angkasapura2.enums.RoleEnum;
import id.co.angkasapura2.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableTransactionManagement
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoleData(){
        for (RoleEnum re : RoleEnum.values()){
            Role role = roleRepository.findOne(re.getValue());
            if (role == null){
                roleRepository.save(new Role(re));
            }
        }
    }




    // HTTP Method: GET, POST, PUT, DELETE

    /**
     * GET: Get data
     * POST: create data
     * PUT: update
     * DELETE: delete
     */

//    private List<Map> users = new ArrayList<Map>();
//    @GetMapping("/user")
//    public List<Map> getUsers() {
//        return users;
//    }
//
//    @PostMapping("/user")
//    public Map createUser(@RequestBody Map user){
//        users.add(user);
//        return user;
//    }
//
//    @PutMapping("/user/{id}")
//    public Map updateUser(@PathVariable("id") int id, @RequestBody Map user){
//        boolean isFound = false;
//        for (Map db : users){
//            int dbid = (Integer)db.get("id");
//            if (dbid == id){
//                users.remove(db);
//                isFound = true;
//                break;
//            }
//        }
//        if (isFound){
//            users.add(user);
//            return user;
//        }else {
//            Map errorMap = new HashMap();
//            errorMap.put("message", "user tidak ditemukan");
//            return errorMap;
//        }
//    }
//
//    @DeleteMapping("/user/{id}")
//    public boolean deleteUser(@PathVariable("id") int id){
//        boolean isFound = false;
//        for (Map db : users){
//            int dbid = (Integer)db.get("id");
//            if (dbid == id){
//                users.remove(db);
//                isFound = true;
//                break;
//            }
//        }
//        return isFound;
//    }
}
