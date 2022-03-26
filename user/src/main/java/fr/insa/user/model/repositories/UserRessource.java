package fr.insa.user.model.repositories;


import fr.insa.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserRessource {

    @Autowired
    public UserRepository userRepository;


    /**
     * Permet la création d'un utilisateur à la base de donnée
     * @param userModel le corps du nouvel utilisateur
     * @return les infos du nouvel utilisateur créé
     */
    @PostMapping
    public UserModel addUser(@RequestBody UserModel userModel) {
        return userRepository.save(userModel);
    }

    /**
     * Permet de récupérer les informations d'un utilisateur en renseignant son adresse mail
     * @param mail adresse mail de l'utilisateur à récupérer
     * @return l'utilisateur s'il existe, NULL sinon
     */
    @GetMapping(params = {"mail"})
    public UserModel getUserByMail(@RequestParam(name = "mail") String mail) {
        return userRepository.getUserModelByMail(mail);
    }

    /**
     * Permet de récupérer les informations d'un utilisateur en renseignant son adresse mail et son mot de passe
     * @param mail adresse mail de l'utilisateur à récupérer
     * @param password mot de passe associé à l'adresse mail
     * @return l'utilisateur s'il existe, NULL sinon
     */
    @GetMapping(params = {"mail","password"})
    public UserModel getUserByMailAndPassword(@RequestParam(name = "mail") String mail, @RequestParam(name = "password") String password) {
        return userRepository.getUserModelByMailAndPassword(mail,password);
    }


    /**
     * Permet de récupérer tous les utilisateurs présents dans la base de donnée
     * @return tous les utilisateurs enregistrés dans la base de donnée
     */
    @GetMapping
    public List<UserModel> getUser() {
        return userRepository.findAll();
    }

}
