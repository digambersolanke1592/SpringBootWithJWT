package com.Commercialsite.AppController;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Commercialsite.AppModel.ERole;
import com.Commercialsite.AppModel.Role;
import com.Commercialsite.AppModel.User;
import com.Commercialsite.AppPayload.Request.SignupRequest;
import com.Commercialsite.AppPayload.Response.MessageResponse;
import com.Commercialsite.AppRepository.RoleRepository;
import com.Commercialsite.AppRepository.UserRepository;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	@Autowired
	UserRepository userRepository;//used  when creating signup 

	@Autowired
	PasswordEncoder encoder;//used  when creating signup 

	@Autowired
	RoleRepository roleRepository;//used  when creating signup 

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error:this user is already present!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {

			return ResponseEntity.badRequest().body(new MessageResponse("Error:this user is already present!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRole = signUpRequest.getRole();
		Set<Role> rolle = new HashSet<>();

		if (strRole == null) {

			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error:Role is not found"));
			rolle.add(userRole);
		}

		else {
			strRole.forEach(rol -> {

				switch (rol) {
				case ("admin"):
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error:Role is not found"));
					rolle.add(adminRole);
					break;

				case ("mod"):
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error:Role is not found"));
					rolle.add(modRole);
					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					rolle.add(userRole);
				}

			});
		}

		user.setRoles(rolle);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("The user is save successfuly!!!"));

	}
}
