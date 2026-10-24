package com.example.monolithic.user;
import com.example.monolithic.common.CrudController;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/users")
public class UserController extends CrudController<AppUser> {
    public UserController(UserRepository repo) { super(repo); }
}
