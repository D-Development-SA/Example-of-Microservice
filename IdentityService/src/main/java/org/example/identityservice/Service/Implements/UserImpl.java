package org.example.identityservice.Service.Implements;

import lombok.Getter;
import org.example.identityservice.Controller.Exception.BDExcepcion.NotExistException;
import org.example.identityservice.Controller.Exception.ClassException.EmptyFieldException;
import org.example.identityservice.Entity.User;
import org.example.identityservice.Respository.UserRepository;
import org.example.identityservice.Service.Contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class UserImpl extends GenericImpl<User, UserRepository> implements UserService, UserDetailsService {
    @Getter
    private static UserRepository repository;

    @Autowired
    public UserImpl(UserRepository dao) {
        super(dao);
        repository = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.isEmpty()){
            throw new EmptyFieldException("username");
        }
        User user = dao.findByUsernameContains(username).orElseThrow(() -> new NotExistException("username", username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

}
