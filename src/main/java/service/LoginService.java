package service;

import model.UserModel;
import repository.UserRepository;

import java.util.List;

public class LoginService {

    private UserRepository userRepository = new UserRepository();
    public boolean checkLogin(String email, String password){
       List<UserModel> list = userRepository.findByEmailAndPassword(email, password);
       return list.size() > 0;
    }
}
