package fr.insa.user.model.repositories;

import fr.insa.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel,String> {

}
