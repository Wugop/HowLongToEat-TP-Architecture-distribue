package fr.insa.user.repositories;

import fr.insa.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,String> {
    public UserModel getUserModelByMailAndPassword(String mail,String password);
    public UserModel getUserModelByMail(String mail);
}
