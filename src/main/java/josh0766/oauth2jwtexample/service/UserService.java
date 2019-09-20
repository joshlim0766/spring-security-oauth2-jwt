package josh0766.oauth2jwtexample.service;

import josh0766.oauth2jwtexample.controller.dto.LoginResponse;
import josh0766.oauth2jwtexample.controller.dto.ResetTokenResponse;
import josh0766.oauth2jwtexample.controller.dto.SingupResponse;
import josh0766.oauth2jwtexample.model.User;
import josh0766.oauth2jwtexample.model.UserDetailsImpl;
import josh0766.oauth2jwtexample.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    private DefaultTokenServices tokenService;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected User createUser (MultiValueMap<String, String> signupInformation) {
        String userName = signupInformation.getFirst("user_name");

        if (userRepository.countByUserName(userName) != 0) {
            throw new RuntimeException("User(" + userName + ") already exists.");
        }

        String password = signupInformation.getFirst("password");

        User user = new User();

        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setUserType(UserDetailServiceImpl.ROLE.USER.getRoleCode());
        user.setCreatedAt(new Date());

        userRepository.saveAndFlush(user);

        return user;
    }

    @Transactional(readOnly = true)
    protected OAuth2AccessToken issueAccessToken (User user, String clientId) {
        HashMap<String, String> authorizationParameters = new HashMap<String, String>();

        //authorizationParameters.put("scope", "read");
        authorizationParameters.put("grant", "password");
        authorizationParameters.put("username", user.getUserName());
        authorizationParameters.put("client_id", clientId);

        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(jdbcClientDetailsService);

        AuthorizationRequest authorizationRequest = requestFactory.createAuthorizationRequest(authorizationParameters);

        authorizationRequest.setApproved(true);

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_TRUSTED_CLIENT"));
        authorizationRequest.setAuthorities(authorities);

        HashSet<String> resourceIds = new HashSet<>();
        resourceIds.add("resource-server");
        authorizationRequest.setResourceIds(resourceIds);

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

        OAuth2Request authRequest = requestFactory.createOAuth2Request(authorizationRequest);

        OAuth2Authentication authentication = new OAuth2Authentication(authRequest, authenticationToken);
        authentication.setAuthenticated(true);

        OAuth2AccessToken accessToken = tokenService.createAccessToken(authentication);

        accessToken = accessTokenConverter.enhance(accessToken, authentication);

        log.debug(accessToken.getValue());

        // Refresh test
        TokenRequest tokenRequest = requestFactory.createTokenRequest(authorizationRequest, "password");

        accessToken = tokenService.refreshAccessToken(accessToken.getRefreshToken().getValue(), tokenRequest);

        accessToken = accessTokenConverter.enhance(accessToken, authentication);

        log.debug(accessToken.getValue());

        return accessToken;
    }

    public SingupResponse signup (MultiValueMap<String, String> signupInformation) {
        User user = createUser(signupInformation);
        String clientId = signupInformation.getFirst("client_id");

        OAuth2AccessToken token = issueAccessToken(user, clientId);

        log.info("DEBUG : " + token.toString() + ", " + token.getValue());

        SingupResponse response = null;

        return response;
    }

    public LoginResponse login (MultiValueMap<String, String> loginInformation) {
        String userName = loginInformation.getFirst("user_name");
        String password = loginInformation.getFirst("password");

        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new RuntimeException(("User not exist"));
        }

        LoginResponse response = null;

        return response;
    }

    public ResetTokenResponse resetToken (String authorization) {
        ResetTokenResponse response = null;

        return response;
    }
}
