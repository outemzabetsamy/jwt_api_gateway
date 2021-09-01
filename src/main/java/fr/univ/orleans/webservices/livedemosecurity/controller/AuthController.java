package fr.univ.orleans.webservices.livedemosecurity.controller;

import fr.univ.orleans.webservices.livedemosecurity.config.JwtTokens;
import fr.univ.orleans.webservices.livedemosecurity.dto.UtilisateurDTO;
import fr.univ.orleans.webservices.livedemosecurity.modele.Utilisateur;
import fr.univ.orleans.webservices.livedemosecurity.service.Services;
import fr.univ.orleans.webservices.livedemosecurity.service.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ServicesImpl services;
    @Autowired
    private JwtTokens jwtTokens;

    static class AuthDTO {
        public String username;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO auth) {
        System.out.println("loooooooooooooooooooooooooooooooooooooooooooooooooOOOOOOOOOOOOOOOOOOOOOOOOOOOGIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIINNNNNNN");
        if (auth.username==null||auth.password==null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("passwword avant: "+auth.password);
        //auth.password=passwordEncoder.encode(auth.password);
        
        
        Optional<UtilisateurDTO> user = services.findByLogin(auth.username);
       // user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
        Utilisateur u=new Utilisateur(user.get().getLogin(),user.get().getPassword(),user.get().isAdmin(),user.get().getIdHotel());
        System.out.println("passwword: "+auth.password);
        System.out.println("auth username: "+auth.username);
        System.out.println("username bdd: "+user.get().getLogin());
        System.out.println("password bdd: "+user.get().getPassword());
        if (user.isPresent()&&passwordEncoder.matches(auth.password, user.get().getPassword())) {
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, JwtTokens.TOKEN_PREFIX+jwtTokens.genereToken(u)).body(JwtTokens.TOKEN_PREFIX+jwtTokens.genereToken(u));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/register")
    public ResponseEntity<UtilisateurDTO> registerUser(@RequestBody UtilisateurDTO us){
        System.out.println("regiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiister");
    	UtilisateurDTO newUser= services.addUser(us);
    	if(newUser==null) {
    		return ResponseEntity.noContent().build();    	}
    else{
    	return ResponseEntity.status(HttpStatus.OK).body(newUser);
    }
}
}
