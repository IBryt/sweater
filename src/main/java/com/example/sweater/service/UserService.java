package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSendler mailSendler;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public boolean addUser(User user) {
        final User usernameDb = userRepo.findByUsername(user.getUsername());
        boolean res = false;
        if (usernameDb == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            sendMessage(user);
            res = true;
        }
        return res;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s%s. Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(), System.lineSeparator(), user.getActivationCode()
            );
            mailSendler.send(user.getEmail(), "Activation code", message);
        }
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user != null) {
            user.setActivationCode(null);
            userRepo.save(user);
        }
        return user == null ? false : true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        final Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        form.keySet().stream()
                .filter(roles::contains)
                .forEach(key -> user.getRoles().add(Role.valueOf(key)));
        userRepo.save(user);
    }

    public void updateProfile(User user, String password, String email) {
        final boolean isEmailChange = isChange(email, user.getEmail()) && !email.isEmpty();
        if (isEmailChange) {
            user.setEmail(email);
            user.setActivationCode(UUID.randomUUID().toString());
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        userRepo.save(user);
        if (isEmailChange) {
            sendMessage(user);
        }
    }

    private boolean isChange(String val1, String val2) {
        return (val1 != null && !val1.equals(val2)
                || val2 != null && !val2.equals(val1));
    }
}
