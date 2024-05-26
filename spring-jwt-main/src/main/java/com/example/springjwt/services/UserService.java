package com.example.springjwt.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;

import com.example.springjwt.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ResponseEntity<String> resetPassword(String token, String NewPassword, String confirmPassword) {
        System.out.println(token);
        VerificationToken verificationToken = verificationTokenService.findByToken(token);
        System.out.println(verificationToken);
        if (!(NewPassword.equals(confirmPassword))){
            return  ResponseEntity.badRequest().body("Les deux mots de passe ne correspondent pas. Veuillez réessayer. ");

        }
        Timestamp cuurentTimesTamp = new Timestamp(System.currentTimeMillis());
        // check if the token is expired
        if(verificationToken.getExpiryDate().before(cuurentTimesTamp)) {
            return  ResponseEntity.badRequest().body("Lien expiré . Veuillez renvoyer votre demande  ");


        }
        if (verificationToken==null){
            return  ResponseEntity.badRequest().body("Lien erroné . Veuillez verifier le lien de modification   ");
        }
        User user = userRepository.findById((int) verificationToken.getUser().getUserId()).orElse(null);
        //prepare new password
        String encodedPassword = passwordEncoder.encode(NewPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return  ResponseEntity.ok("Mot de passe a été modifier  ");

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

}
