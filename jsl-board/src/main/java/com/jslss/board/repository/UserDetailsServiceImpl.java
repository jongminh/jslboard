package com.jslss.board.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.jslss.board.entity.Role;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository)
    {
    	this.userRepository = userRepository;
    }

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {

        com.jslss.board.entity.User user = userRepository.findByUserId(userId);
        if (user == null)
            throw new UsernameNotFoundException("user not found");
        user.getRoles().size();
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);

    }

    private User buildUserForAuthentication(com.jslss.board.entity.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getUserId(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

    	Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

    	// Build user's authorities
    	for (Role role : userRoles) {
    		setAuths.add(new SimpleGrantedAuthority(role.getRole()));
    	}

    	return new ArrayList<GrantedAuthority>(setAuths);
    }
}