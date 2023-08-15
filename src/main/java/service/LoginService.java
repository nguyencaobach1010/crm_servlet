package service;

import model.UserModel;
import repository.UserRepository;

import java.util.Optional;


public class LoginService {
    private final UserRepository userRepository = new UserRepository();

    public boolean checkLogin(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).isPresent();
    }

    public Optional<UserModel> getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

}
