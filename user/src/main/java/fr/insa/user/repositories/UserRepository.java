package fr.insa.user.repositories;

import fr.insa.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Integer> {
    Optional<UserModel> findByMailAndPassword(String mail, String password);
    UserModel getUserModelByMailAndPassword(String mail, String password);
    public UserModel getUserModelByMail(String mail);
    public UserModel getUserModelByIdUser(int id);
}
