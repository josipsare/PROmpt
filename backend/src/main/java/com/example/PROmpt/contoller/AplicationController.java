package com.example.PROmpt.contoller;


import com.example.PROmpt.configuration.JwtService;
import com.example.PROmpt.models.User;
import com.example.PROmpt.repository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Controller
public abstract class AplicationController {
    User currentUser = null;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo;

    @ModelAttribute("currentUser")
    public User getCurrentUser(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
            String token = authorizationHeader.substring(7);

            try {
                System.out.println("testing");
                String username = jwtService.extractUsername(token);
                Optional<User> korisnikOpt = userRepo.findByEmail(username);
                User korisnik = null;
                if (korisnikOpt.isPresent()) {
                    korisnik = korisnikOpt.get();
                    this.currentUser = korisnik;
                }
                return korisnik;
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }
}

