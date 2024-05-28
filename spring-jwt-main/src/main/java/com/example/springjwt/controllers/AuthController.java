package com.example.springjwt.controllers;

import com.example.springjwt.auth.JwtUtil;
import com.example.springjwt.dto.UserConverter;
import com.example.springjwt.dto.UserDTO;
import com.example.springjwt.model.User;
import com.example.springjwt.model.request.LoginReq;
import com.example.springjwt.model.response.ErrorRes;
import com.example.springjwt.model.response.LoginRes;
import com.example.springjwt.repositories.IUserRepository;
import com.example.springjwt.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    private IUserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    @Autowired
	private UserService userService;

    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }
    @GetMapping("/userrole/{email}")
    public User getUserrole(@PathVariable("email") String email){
        User user = userRepository.findByEmail(email);
        return user;
    }
    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {
        try {
           /* Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
           */
        	if(userService.validateUser(loginReq.getEmail(), loginReq.getPassword())!=null) {
                User user1 = userService.validateUser(loginReq.getEmail(), loginReq.getPassword());
        		String email = loginReq.getEmail();
                User user = new User(email,"");
                String token = jwtUtil.createToken(user);
                LoginRes loginRes = new LoginRes(email,token,user1.isStatus());
                return ResponseEntity.ok(loginRes);
        	}else {
        		ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        	}
            

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
    @PostMapping(path = "/signin")
	public ResponseEntity<?> addUser(@RequestBody UserDTO userDto) {
        User existingUser = userRepository.findByEmail(userDto.getEmail());
        if (existingUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        }
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
}