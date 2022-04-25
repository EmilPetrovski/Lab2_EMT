package mk.ukim.finki.library.service;

import mk.ukim.finki.library.model.User;

public interface AuthService {

    User login(String username, String password);

}
