package com.wncsl.auth.config;

import com.wncsl.auth.domain.logonhistory.LogonHistoryService;
import com.wncsl.auth.domain.user.User;
import com.wncsl.auth.domain.user.UserService;
import com.wncsl.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private LogonHistoryService logonHistoryService;

    @Override
    @Transactional
    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
        System.out.println(this.getClass().getSimpleName()+".loadUserByUsername: "+ username + ": "+ LocalTime.now());
        User user;
        try {
            user = userService.getByUsername(username);
            List<SimpleGrantedAuthority> lst = user.getPermissions()
                    .stream()
                    .map(p-> new SimpleGrantedAuthority(p.getRole()))
                    .collect(Collectors.toList());
            CustomUser customUser = new CustomUser(user.getUuid(), user.getUsername(), user.getPassword(), user.getType(), lst );
            return customUser;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }
    }
}