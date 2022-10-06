package br.univille.apidacs2022.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.univille.coredacs2022.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var resultConsult = repository.findByUser(username);
        if (resultConsult.isPresent()) {
            var user = resultConsult.get();
            return new User(user.getUser(), user.getSenha(), new ArrayList<>());
        }
        return null;
    }
    
}
