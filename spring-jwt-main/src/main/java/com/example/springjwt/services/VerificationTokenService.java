package com.example.springjwt.services;

import com.example.springjwt.model.User;
import com.example.springjwt.model.VerificationToken;

public interface VerificationTokenService {
    public VerificationToken findByToken(String token);
    public VerificationToken findByUser(User user);

    public void affectUserToken(User user , String token);
}
