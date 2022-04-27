package fr.insa.user.repositories;


import Exceptions.ExecutionErrorException;
import fr.insa.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/userRessources")
public class UserRessource extends CommonRessource{

    @Autowired
    public UserRepository userRepository;


    /**
     * Permet la création d'un utilisateur à la base de donnée
     * @param userModel le corps du nouvel utilisateur
     * @return les infos du nouvel utilisateur créé
     */
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel) throws ExecutionErrorException {
        if(this.userRepository.getUserModelByMail(userModel.getMail()) != null)
            throw new ExecutionErrorException("Error creating user, this email is already used.",HttpStatus.CONFLICT);
        this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User created.");
    }

    /**
     * Permet de récupérer les informations d'un utilisateur en renseignant son adresse mail
     * @param mail adresse mail de l'utilisateur à récupérer
     * @return l'utilisateur s'il existe, NULL sinon
     */
    @GetMapping(params = {"mail"})
    public UserModel getUserByMail(@RequestParam(name = "mail") String mail) throws ExecutionErrorException {
        UserModel userModel = this.userRepository.getUserModelByMail(mail);
        if(userModel == null)
            throw new ExecutionErrorException("Error getting user, this email is non-existent.",HttpStatus.BAD_REQUEST);
        return UserModel.builder()
                .mail(userModel.getMail())
                .name(userModel.getName())
                .firstName(userModel.getFirstName())
                .build();
    }

    /**
     * Permet de récupérer les informations d'un utilisateur en renseignant son adresse mail et son mot de passe
     * @param mail adresse mail de l'utilisateur à récupérer
     * @param password mot de passe associé à l'adresse mail
     * @return l'utilisateur s'il existe, NULL sinon
     */
    @GetMapping(params = {"mail","password"})
    public UserModel getUserByMailAndPassword(@RequestParam(name = "mail") String mail, @RequestParam(name = "password") String password) throws ExecutionErrorException {
        UserModel userModel = this.userRepository.getUserModelByMailAndPassword(mail,password);
        if(userModel == null)
            throw new ExecutionErrorException("Error getting user, mail or password incorrect.",HttpStatus.BAD_REQUEST);
        return UserModel.builder()
                .mail(userModel.getMail())
                .name(userModel.getName())
                .firstName(userModel.getFirstName())
                .password(userModel.getPassword())
                .build();
    }
}
