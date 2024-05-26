package com.example.springjwt.services;


import com.example.springjwt.model.User;
import com.example.springjwt.model.VerificationToken;
import com.example.springjwt.repositories.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
@Transactional
public class VerificationTokenServiceImp implements VerificationTokenService{

    private VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenServiceImp(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public void affectUserToken(User user, String token) {
        VerificationToken existingToken = verificationTokenRepository.findByUser(user);
        if (existingToken != null) {
            // Si un jeton existe déjà pour cet utilisateur, mettez à jour sa date d'expiration
            existingToken.setExpiryDate(calculateExpiryDate(24 * 60));
            verificationTokenRepository.save(existingToken);
        } else {
            // Sinon, créez un nouveau jeton et insérez-le dans la base de données
            VerificationToken newToken = new VerificationToken(token, user);
            newToken.setExpiryDate(calculateExpiryDate(24 * 60));
            verificationTokenRepository.save(newToken);
        }
    }

    //Calcul expiry Date
    private Timestamp calculateExpiryDate(int expiryTimeInMinute){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,expiryTimeInMinute);
        return new Timestamp(cal.getTime().getTime());
    }


}
