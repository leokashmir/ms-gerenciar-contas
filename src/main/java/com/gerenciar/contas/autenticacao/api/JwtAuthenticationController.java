package com.gerenciar.contas.autenticacao.api;


import com.gerenciar.contas.autenticacao.domain.model.JwtRequest;
import com.gerenciar.contas.autenticacao.domain.model.JwtResponse;
import com.gerenciar.contas.autenticacao.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
@Data
@RequestMapping(value = "/api")
public class JwtAuthenticationController {



    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtToken;

    private UserDetailsService jwtUserDataService;


    public JwtAuthenticationController(JwtTokenUtil jwtToken, UserDetailsService jwtUserDataService,AuthenticationManager authenticationManager ) {
        this.jwtToken = jwtToken;
        this.jwtUserDataService = jwtUserDataService;
        this.authenticationManager = authenticationManager;


    }
    @PostMapping(path = "/auth")
    public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest request)
            throws Exception {

        this.authenticate(request.getUsername(), request.getPassword());

        final UserDetails userDetails = jwtUserDataService
                .loadUserByUsername(request.getUsername());

        final String token = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate (String user, String pass) throws Exception {
        Objects.requireNonNull(user);
        Objects.requireNonNull(pass);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user, pass) );
        } catch (DisabledException e) {
            throw new Exception("USUARIO DESABILITADO ", e);
        } catch (BadCredentialsException e) {
            throw new Exception("CREDENCIAIS INVALIDAS", e);
        }
    }

}
