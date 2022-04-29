package fr.insa.user.repositories;

import Exceptions.ExecutionErrorException;
import fr.insa.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/userRessourcesRestricted")
public class UserRessourceRestricted extends CommonRessource{

    @Autowired
    public UserRepository userRepository;


    /**
     * Permet de modifier les informations d'un utilisateur
     * @param userModel Les informations à modifier
     * @param id ID de l'utilisateur à modifier (id donner via la gateway et le token)
     * @return ResponseEntity avec un status "Accepted" si tous les changements ont bien été effectués
     * @throws ExecutionErrorException si l'utilisateur n'a pas été trouvé
     */
    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody UserModel userModel,
                                             @RequestHeader(name = "x-auth-user-id") String id) throws ExecutionErrorException {
        UserModel userUpdate = this.userRepository.getUserModelByIdUser(Integer.parseInt(id));
        if(userUpdate == null)
            throw new ExecutionErrorException("Error getting user, user not recognized.", HttpStatus.BAD_REQUEST);
        if (userModel.getFirstName() != null)
            userUpdate.setFirstName(userModel.getFirstName());
        if (userModel.getName() != null)
            userUpdate.setName(userModel.getName());
        if (userModel.getMail() != null)
            userUpdate.setMail(userModel.getMail());
        if (userModel.getPassword() != null)
            userUpdate.setPassword(userModel.getPassword());
        this.userRepository.save(userUpdate);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Changes made.");
    }

    /**
     * Permet de supprimer un utilisateur de la base de donnée via son ID
     * @param id ID de l'utilisateur à modifier (id donner via la gateway et le token)
     * @return ResponseEntity avec un status "Accepted" si l'utilisateur a bien été supprimé
     * @throws ExecutionErrorException si l'utilisateur n'a pas été trouvé
     */

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestHeader(name = "x-auth-user-id") String id) throws ExecutionErrorException {
        UserModel userModel = this.userRepository.getUserModelByIdUser(Integer.parseInt(id));
        if(userModel == null)
            throw new ExecutionErrorException("Error getting user, user not recognized.",HttpStatus.BAD_REQUEST);
        this.userRepository.delete(userModel);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("User successfully deleted");
    }
}
