package id.co.angkasapura2.controllers;

import id.co.angkasapura2.entities.User;
import id.co.angkasapura2.enums.RoleEnum;
import id.co.angkasapura2.services.UserService;
import id.co.angkasapura2.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;


    @GetMapping("/user")
    public Page<User> getUsers(Pageable pageable) {
        return userService.get(pageable);
    }

    @PostMapping("/user")
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map param){
        return getHttpResponse(userService.login(param));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user){
        return getHttpResponse(userService.register(user));
    }

    @PostMapping("/user/airline")
    public ResponseEntity createAirlineUser(@RequestHeader("Authorization") String token,
                                            @RequestBody User user){
        if (!authorize(RoleEnum.ADMIN, token)) return FORBIDDEN;
        return getHttpResponse(userService.createAirlineUser(user));
    }

    @PostMapping("/user/admin")
    public ResponseEntity createAdmin(@RequestHeader("Authorization") String token,
                                      @RequestBody User user){
        if (!authorize(RoleEnum.ADMIN, token)) return FORBIDDEN;

        return getHttpResponse(userService.createAdmin(user));
    }

    @PutMapping("/user/{id}")
    public User update(@PathVariable("id") String id, @RequestBody User user){
        user.setId(id);
        return userService.update(user);
    }

    @DeleteMapping("/user/{id}")
    public boolean delete(@PathVariable("id") String id){
        return userService.delete(id);
    }

    @PostMapping("/user/import")
    public String importUser(@RequestParam("file") MultipartFile file){
        return "hello";
    }
}
