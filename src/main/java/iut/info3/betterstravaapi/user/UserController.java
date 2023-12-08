package iut.info3.betterstravaapi.user;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path="/all")
    public @ResponseBody Iterable<UserEntity> getAllUsers() {
        // This returns a JSON or XML with the users
        return userService.findAll();
    }

    @GetMapping(path = "/login")
    public UserEntity authenticate(@RequestParam String email,@RequestParam String password) {
        return userService.findByEmailAndPassword(email,password);
    }

}
