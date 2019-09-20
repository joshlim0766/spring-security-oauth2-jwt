package josh0766.oauth2jwtexample.service;

import josh0766.oauth2jwtexample.model.User;
import josh0766.oauth2jwtexample.model.UserDetailsImpl;
import josh0766.oauth2jwtexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    enum ROLE {
        ADMIN(1, "ROLE_ADMIN"), USER(2, "ROLE_USER");

        private final int roleCode;

        private final String roleString;

        ROLE (int roleCode, String roleString) {
            this.roleCode = roleCode;
            this.roleString = roleString;
        }

        public int getRoleCode () {
            return roleCode;
        }

        public String getRoleString () {
            return roleString;
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found user : " + username);
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        if (user.getUserType() == ROLE.ADMIN.getRoleCode()) {
            userDetails.setAuthorities(Arrays.asList(ROLE.ADMIN.getRoleString()));
        }
        else {
            userDetails.setAuthorities(Arrays.asList(ROLE.USER.getRoleString()));
        }

        return userDetails;
    }
}
