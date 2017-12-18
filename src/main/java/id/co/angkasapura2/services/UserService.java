package id.co.angkasapura2.services;

import id.co.angkasapura2.entities.Role;
import id.co.angkasapura2.entities.User;
import id.co.angkasapura2.enums.RoleEnum;
import id.co.angkasapura2.repositories.RoleRepository;
import id.co.angkasapura2.repositories.UserRepository;
import id.co.angkasapura2.utils.PasswordUtil;
import id.co.angkasapura2.utils.Response;
import id.co.angkasapura2.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    public User create(User user){
        user.setId(UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }

    public User update(User user){
        User userDb = userRepository.findOne(user.getId());
        if (userDb == null){
            return null;
        }else {
            userDb.setEmail(user.getEmail());
            userDb.setName(user.getName());
            userDb.setPhone(user.getPhone());
            userRepository.save(userDb);
            return userDb;
        }
    }


    public Page<User> get(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public boolean delete(String id){
        User userDb = userRepository.findOne(id);
        if (userDb == null) return false;
        userRepository.delete(id);
        User userDb2 = userRepository.findOne(id);
        return userDb2 == null;
    }


    public Response register(User user) {
        user.setPassword(PasswordUtil.hash(user.getPassword()));
        return createUser(user, RoleEnum.CUSTOMER, null);
    }

    @Transactional
    public Response createAirlineUser(User user) {
        // generate password
        String plainPassword = PasswordUtil.generatePassword();
        String hashedPassword = PasswordUtil.hash(plainPassword);
        user.setPassword(hashedPassword);
        return createUser(user, RoleEnum.AIRLINE, plainPassword);
    }

    @Transactional
    public Response createAdmin(User user) {
        // generate password
        String plainPassword = PasswordUtil.generatePassword();
        String hashedPassword = PasswordUtil.hash(plainPassword);
        user.setPassword(hashedPassword);
        return createUser(user, RoleEnum.ADMIN, plainPassword);
    }

    private Response createUser(User user, RoleEnum roleEnum, String plainPassword){
        user.setId(UUID.randomUUID().toString());
        int roleId = roleEnum.getValue();
        Role roleCustomer = roleRepository.findOne(roleId);
        user.getRoles().add(roleCustomer);

        // validasi email: harus ada karakter @, tidak boleah kosong, harus unik
        // validasi password: tidak boleh kosong
        // validasi name: tidak boleh kosong

        if (user.getEmail().isEmpty()){
            return new Response("Email tidak boleh kosong");
        }else if (!user.getEmail().contains("@")){
            return new Response("Format email tidak valid");
        }
        User userDb = userRepository.findByEmail(user.getEmail());
        if (userDb != null){
            return new Response("Email sudah dipakai");
        } else if (user.getPassword().isEmpty()){
            return new Response("Password tidak boleh kosong");
        }else if (user.getName().isEmpty()){
            return new Response("Nama tidak boleh kosong");
        }

        userRepository.save(user);

        if (roleEnum == RoleEnum.ADMIN || roleEnum == RoleEnum.AIRLINE){
            // send email to them
            String to = user.getEmail();
            String subject = "Akun Angkasa Pura Anda";
            String body = String.format("Selamat akun anda berhasil dibuat. Silahkan login menggunakan email: %s dan password: %s",
                    user.getEmail(),
                    plainPassword);
            try{
                emailService.sendEmail(to, subject, body);
            } catch (Exception ex){
                throw new RuntimeException("Gagal kirim email");
            }
        }

        return new Response(user);
    }

    public Response login(Map param) {
        String email = (String) param.get("email");
        String password = (String) param.get("password");
        User user = userRepository.findByEmail(email);
        if (user != null){
            boolean isValidPassword = PasswordUtil.compare(password, user.getPassword());
            if (isValidPassword){
                //buat token dan kembalikan ke requester
                String token = TokenUtil.createToken(user);
                Map map = new HashMap();
                map.put("id", user.getId());
                map.put("name", user.getName());
                map.put("email", user.getEmail());
                map.put("token", token);
                return new Response(map, "Berhasil login");
            }else {
                return new Response("Password salah");
            }
        } else {
            return new Response("Email tidak ditemukan");
        }
    }
}
