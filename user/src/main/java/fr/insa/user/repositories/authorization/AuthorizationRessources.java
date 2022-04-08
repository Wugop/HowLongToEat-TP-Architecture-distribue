package fr.insa.user.repositories.authorization;

import fr.insa.user.model.UserModel;
import fr.insa.user.repositories.UserRepository;
import fr.insa.user.repositories.authorization.payload.LoginRequest;
import fr.insa.user.repositories.authorization.payload.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/authorization")
public class AuthorizationRessources {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userRepository.findByMailAndPassword(loginRequest.getEmail(), loginRequest.getPassword())
                .map(utilisateur -> ResponseEntity.ok(jwtTokenProvider.generateToken(utilisateur.getIdUser())))
                .orElse(new ResponseEntity("Email ou mot de passe invalide", HttpStatus.UNAUTHORIZED));
    }


    @GetMapping("is-authorized")
    public String isAuthorize(@RequestParam(name = "jwt") String jwt) {
        if (jwtTokenProvider.validateToken(jwt)) {
            int userId = jwtTokenProvider.getUserIdFromJWT(jwt);
            Optional<UserModel> user = userRepository.findById(userId);
            if (user.isPresent()) {
                return String.valueOf(user.get().getIdUser());
            }
        }
        return "-1";
    }

    @GetMapping("not-authorized")
    public ResponseEntity<String> notAuthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("access denied, error with Token");
    }

}

