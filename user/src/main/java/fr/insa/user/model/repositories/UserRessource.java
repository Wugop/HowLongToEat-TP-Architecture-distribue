package fr.insa.user.model.repositories;


import fr.insa.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class UserRessource {

    @Autowired
    public UserRepository userRepository;

    @GetMapping("user")
    public List<UserModel> getUser() {
        return userRepository.findAll();
    }

}
