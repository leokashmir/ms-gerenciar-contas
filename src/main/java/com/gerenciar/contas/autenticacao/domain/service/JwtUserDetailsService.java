package com.gerenciar.contas.autenticacao.domain.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("zeppelin".equals(username)) {
            return new User("zeppelin", "$2b$10$PXrJcVaDTApieu3n.bxlWOVzUV0.2jysJaLDndXobBl5kQyG/.Oru",
                    new ArrayList<>());
        } else   if ("master".equals(username)) {
            return new User("master", "$2b$10$8JKJcewZ1RgxSSG.5AuvUuDWDuZX8xX7rvXA8eenB.OiBoHxiFk3.",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario ou senha invalidos: " + username);
        }
    }
}
