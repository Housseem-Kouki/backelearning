package com.example.springjwt.services;

import java.sql.Timestamp;
import java.util.*;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;

import com.example.springjwt.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springjwt.model.User;
import com.example.springjwt.repositories.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	@Autowired final IUserRepository userRepository; // Updated
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;


    @Autowired
    private EmailUserService emailUserService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int quizId) {
        return userRepository.findById(quizId);
    }

    public User save(User user) {
    	String hashedPassword = passwordEncoder.encode(user.getPassword());
    	user.setPassword(hashedPassword);
        return userRepository.save(user); // Updated
    }

    public boolean existsById(int userId) {
        return userRepository.existsById(userId);
    }

    public void deleteUser(int userId) {
    	userRepository.deleteById(userId);
    }
    public User validateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(passwordEncoder.matches(password, user.getPassword())) {
        	return user;
        }else {
        	return null;
        } 
    }

    public ResponseEntity<String> requestPasswordReset(String email) throws MessagingException, jakarta.mail.MessagingException {
        User user = userRepository.findByEmail(email);

            String token = UUID.randomUUID().toString();
            verificationTokenService.affectUserToken(user , token);

            emailUserService.resetPasswordMail(user);



        return ResponseEntity.ok("Email de mot de passe oublié a été envoyé");
    }

    public String resetPassword(String token, String newPassword, String confirmPassword) {
        System.out.println(token);
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        System.out.println(verificationToken);
        if (!(newPassword.equals(confirmPassword))) {
            return "Les deux mots de passe ne correspondent pas. Veuillez réessayer.";
        }
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (verificationToken.getExpiryDate().before(currentTimestamp)) {
            return "Lien expiré. Veuillez renvoyer votre demande";
        }
        if (verificationToken == null) {
            return "Lien erroné. Veuillez vérifier le lien de modification";
        }
        User user = userRepository.findById((int) verificationToken.getUser().getUserId()).orElse(null);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "Mot de passe a été modifié";
    }
    public void suspendUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(false); // suspend the user
            userRepository.save(user);
        } else {
            System.out.println("User with ID " + userId + " not found. Unable to suspend.");
        }
    }

    public void reactivateUser(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setStatus(true); // reactivate the user
            userRepository.save(user);
        } else {
            System.out.println("User with ID " + userId + " not found. Unable to reactivate.");
        }
    }



    public ResponseEntity<Map<String, String>> updatePasswor(int idUserconnected,
                                                             String currentPassword,
                                                             String newPassword,
                                                             String confirmPassword){
        Map<String, String> response = new HashMap<>();
        if (!(newPassword.equals(confirmPassword))) {
            String errorMessage = "New password and confirm password do not match.";
            System.err.println(errorMessage);
            response.put("message", errorMessage);
            return ResponseEntity.badRequest().body(response);
        }
        User user = userRepository.findById(idUserconnected).orElse(null);

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            String errorMessage = "Current password is incorrect.";
            System.err.println(errorMessage);
            response.put("message", errorMessage);
            return ResponseEntity.badRequest().body(response);
        }


        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        String errorMessage = "Password changed successfully.";
        System.err.println(errorMessage);
        response.put("message", errorMessage);
        return ResponseEntity.ok().body(response) ;
    }

}
