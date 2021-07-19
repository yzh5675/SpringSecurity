package com.cs.comment0719.config;

import com.cs.comment0719.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(123);
        System.out.println(s);

        com.cs.comment0719.entity.User u = userService.getUserByName(s);
        if (u == null) {
            throw new BadCredentialsException("用户名不存在，请注册!");
        } else {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.
                            getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.setAttribute("user", u);
        }

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("VIP1"));
        list.add(new SimpleGrantedAuthority("VIP2"));
        list.add(new SimpleGrantedAuthority("VIP3"));
        list.add(new SimpleGrantedAuthority("AddUser"));

        UserDetails userDetails = new User(u.getName(),
                new BCryptPasswordEncoder().encode(u.getPwd()), list);
        return userDetails;
    }
}
