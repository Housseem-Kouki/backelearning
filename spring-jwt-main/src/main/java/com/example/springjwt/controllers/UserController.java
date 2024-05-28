package com.example.springjwt.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.springjwt.model.Course;
import com.example.springjwt.model.PasswordResetModel;
import com.example.springjwt.repositories.IUserRepository;
import com.example.springjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.springjwt.dto.UserConverter;
import com.example.springjwt.dto.UserDTO;
import com.example.springjwt.model.User;
import com.example.springjwt.services.UserService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/rest/user")
public class UserController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	@GetMapping("/userrole/{email}")
	public User getUserrole(@PathVariable("email") String email){
		User user = userRepository.findByEmail(email);
		return user;
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable int userId) {
		try {
			User foundUser = userService.getUserById(userId)
					.orElseThrow(() -> new EntityNotFoundException("Requested user not found"));
			return ResponseEntity.ok().body(userService);
		} catch (EntityNotFoundException ex) {
			HashMap<String, String> message = new HashMap<>();
			message.put("message", "User not found for id: " + userId);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	@GetMapping("/getUserById/{userId}")
	public User getUserById2(@PathVariable int userId) {
		return userRepository.findById(userId).orElse(null);
	}

	@PostMapping(path = "/users")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
		try {
			// Convert UserDTO to user entity
			User user = UserConverter.toEntity(userDto);

			// Save the user entity using the user service
			User savedUser = userService.save(user);

			// Return ResponseEntity with the saved user
			return ResponseEntity.ok(savedUser);
		} catch (Exception e) {
			// Handle any exceptions and return an appropriate response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user");
		}
	}

	@PutMapping("/users/{userId}")
	public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int userId) {
		if (userService.existsById(userId)) {
			User existingUser = userService.getUserById(userId)
					.orElseThrow(() -> new EntityNotFoundException("Requested user not found"));
			// Update the existing user fields
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPassword(user.getPassword());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			existingUser.setRole(user.getRole());
			userService.save(existingUser);
			return ResponseEntity.ok().body(existingUser);
		} else {
			HashMap<String, String> message = new HashMap<>();
			message.put("message", user + " user not found or matched");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable int userId) {
		if (userService.existsById(userId)) {
			userService.deleteUser(userId);
			HashMap<String, String> message = new HashMap<>();
			message.put("message", "User with id " + userId + " was deleted successfully.");
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			HashMap<String, String> message = new HashMap<>();
			message.put("message", userId + " user not found or matched");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}

	@GetMapping("/requestPasswordReset/{email}")
	public ResponseEntity<String> requestPasswordReset(@PathVariable("email") String email) throws Exception {
		return userService.requestPasswordReset(email);
	}

	@PostMapping("/password-reset")
	public ResponseEntity<Map<String, String>> resetPassword(@RequestBody PasswordResetModel passwordResetModel, @QueryParam("token") String token) {
		Map<String, String> response = new HashMap<>();
		if (passwordResetModel == null) {
			String errorMessage = "Le modèle de réinitialisation du mot de passe est null.";
			System.err.println(errorMessage);
			response.put("message", errorMessage);
			return ResponseEntity.badRequest().body(response);
		} else {
			String newPassword = passwordResetModel.getNewPassword();
			String confirmPassword = passwordResetModel.getConfirmPassword();

			if (newPassword == null || confirmPassword == null) {
				String errorMessage = "Les champs NewPassword ou ConfirmPassword sont null.";
				System.err.println(errorMessage);
				response.put("message", errorMessage);
				return ResponseEntity.badRequest().body(response);
			} else {
				System.out.println(newPassword);
				System.out.println("token envoyéééé : " + token);
				String result = userService.resetPassword(token, newPassword, confirmPassword);
				response.put("message", result);
				return ResponseEntity.ok().body(response);
			}
		}
	}


	@GetMapping("/hello")
	public void resetPassword(@RequestBody String hello) {
		System.out.println(hello);
		//return userService.resetPassword(token , passwordResetModel.getNewPassword() , passwordResetModel.getConfirmPassword());
	}
	/*@PostMapping("/users/login")
    public String login(@RequestBody UserDTO user) {
        if (userService.validateUser(user.getEmail(), user.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid name or password";
        }
    }*/
	
/*	@PostMapping("/users/login")
	public String getToken(@RequestBody UserDTO userDTO) {
		User user = userService.validateUser(userDTO.getEmail(), userDTO.getPassword()); 
		if (user!=null) {
		//	boolean isAuthenticated = authenticationService.authenticate(user.getEmail(), user.getPassword());
			
			//Authentication authentication = new AuthenticationConfiguration();
				//	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("khalil", "$2a$10$euE.ZHCWpElh7GkQ0Ou5XeVUCyULe5BiMvOcfMjuZYhpQRSnZWtW."));
			//String token = jwtService.generateToken(authentication);
			return "valid name or password";
        } else {
            return "Invalid name or password";
        } 
		
	}*/




	@PutMapping("/users/{userId}/suspend")
	public ResponseEntity<?> suspendUser(@PathVariable int userId) {
		try {
			userService.suspendUser(userId);
			return ResponseEntity.ok().body("User with ID " + userId + " suspended successfully");
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to suspend user");
		}
	}

	@PutMapping("/users/{userId}/reactivate")
	public ResponseEntity<?> reactivateUser(@PathVariable int userId) {
		try {
			userService.reactivateUser(userId);
			return ResponseEntity.ok().body("User with ID " + userId + " reactivated successfully");
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reactivate user");
		}
	}




	@GetMapping("/getAllInstructor")
	public List<User> getAllInstructor(){
		List<User> userList = userRepository.findAll();
		// Filtrer les utilisateurs ayant le rôle égal à 2
		List<User> instructors = userList.stream()
				.filter(user -> user.getRole() == 2)
				.collect(Collectors.toList());
		return instructors;
	}




	@PutMapping("/changePassword")
	public ResponseEntity<Map<String, String>> changePassword(@RequestParam int idUserconnected,
												 @RequestParam String currentPassword,
												 @RequestParam String newPassword,
												 @RequestParam String confirmPassword) {
		return userService.updatePasswor(idUserconnected,currentPassword,newPassword,confirmPassword);
	}

	@PutMapping("/updateUser")
	public User updateUser(@RequestBody User user) {
		User user1 = userRepository.findById((int) user.getUserId()).orElse(null);
		user1.setBirthdate(user.getBirthdate());
		user1.setCountry(user.getCountry());
		user1.setLocation(user.getLocation());

		user1.setFirstName(user.getFirstName());
		user1.setLastName(user.getLastName());
		user1.setEmail(user.getEmail());
		user1.setProfession(user.getProfession());
		user1.setPhoneNumber(user.getPhoneNumber());
		return userRepository.save(user1);
	}

	@PutMapping(path = "/updateImageUser/{idUser}")
	public void addCoursesFinal(@RequestParam("file") MultipartFile file,
								  @PathVariable("idUser")int idUser) {
		User user = userRepository.findById(idUser).orElse(null);
		try {
			//Course course = objectMapper.readValue(courseJson, Course.class);
			user.setUserPhotoFile(file.getBytes());
			String fileName = file.getOriginalFilename();
			// Sauvegarder le fichier dans le dossier d'uploads avec un nom spécifique
			byte[] fileBytes = file.getBytes();
			Path filePath = Paths.get("src/main/resources/static/uploads/" + fileName);
			Files.write(filePath, fileBytes);
// Enregistrer le nom du fichier dans l'objet Course
			user.setFileName(fileName);
			// Sauvegarde du cours
			 userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
