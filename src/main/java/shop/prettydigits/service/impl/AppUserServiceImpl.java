package shop.prettydigits.service.impl;
/*
@Author hakim a.k.a. Hakim Amarullah
Java Developer
Created on 6/21/2024 10:44 PM
@Last Modified 6/21/2024 10:44 PM
Version 1.0
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.prettydigits.constant.role.Role;
import shop.prettydigits.dto.request.LoginRequest;
import shop.prettydigits.dto.request.RegisterRequest;
import shop.prettydigits.dto.response.ApiResponse;
import shop.prettydigits.dto.response.LoginResponse;
import shop.prettydigits.dto.response.RegisterResponse;
import shop.prettydigits.model.Cart;
import shop.prettydigits.model.User;
import shop.prettydigits.repository.CartRepository;
import shop.prettydigits.repository.UserRepository;
import shop.prettydigits.service.AppUserService;
import shop.prettydigits.utils.CommonUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppUserServiceImpl implements AppUserService {

    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthenticationProvider authenticationProvider;

    private final JwtEncoder jwtEncoder;


    private final ObjectMapper mapper;

    @Value("${jwt.token.age:3600}")
    private Long jwtExpiry;

    @Value("${jwt.issuer}")
    private String issuer;

    @Autowired
    public AppUserServiceImpl(UserRepository userRepository,
                              CartRepository cartRepository,
                              BCryptPasswordEncoder passwordEncoder,
                              AuthenticationProvider authenticationProvider,
                              JwtEncoder jwtEncoder,
                              ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.jwtEncoder = jwtEncoder;
        this.mapper = mapper;
    }


    @Override
    public ApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        Authentication authentication;
        loginRequest.setUsername(CommonUtils.formatPhoneNumber(loginRequest.getUsername()));
        try {
            authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException | DisabledException ex) {
            return ApiResponse.<LoginResponse>builder()
                    .code(401)
                    .message(ex.getMessage())
                    .build();
        }

        User user = (User) authentication.getPrincipal();

        Instant now = Instant.now();
        String scope = authentication.getAuthorities().parallelStream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claim = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(jwtExpiry))
                .subject(String.format("%s,%s", user.getUserId(), user.getUsername()))
                .claim("roles", scope)
                .build();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(claim)).getTokenValue();

        LoginResponse loginResponse = mapper.convertValue(user, LoginResponse.class);
        loginResponse.setAccessToken(token);
        return ApiResponse.<LoginResponse>builder()
                .code(200)
                .data(loginResponse)
                .message("login successful")
                .build();
    }

    @Transactional
    @Override
    public ApiResponse<RegisterResponse> registerUser(RegisterRequest request) {
        request.setPhone(CommonUtils.formatPhoneNumber(request.getPhone()));

        Optional<User> userDB = userRepository.findFirstByUsernameOrPhoneAndEnabledTrue(request.getUsername(), request.getPhone());

        ApiResponse<RegisterResponse> response = ApiResponse.setSuccess();
        RegisterResponse registerResponse = new RegisterResponse();

        if (userDB.isPresent() && userDB.get().isEnabled()) {
            response.setCode(409);
            response.setMessage("username or phone already registered");
            return response;
        } else if (userDB.isPresent()
                && !userDB.get().isEnabled()
                && userDB.get().getRole().equals(Role.USER)) {
            userDB.get().setEnabled(true);
            userRepository.save(userDB.get());

            registerResponse.setUsername(userDB.get().getUsername());
            registerResponse.setPhone(userDB.get().getPhone());
            registerResponse.setFullName(userDB.get().getFullName());
            response.setCode(200);
            response.setMessage("account already exist and has been reactivated");
            response.setData(registerResponse);
            return response;
        }

        User newUser = mapper.convertValue(request, User.class);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        Cart cart = new Cart();
        if (newUser.getRole().equals(Role.USER)) {
            newUser.setEnabled(true);
        }
        userRepository.save(newUser);
        cart.setUser(newUser);
        cartRepository.save(cart);


        registerResponse = mapper.convertValue(newUser, RegisterResponse.class);
        response.setMessage("success register user");
        response.setCode(201);
        response.setData(registerResponse);
        return response;
    }

    @Override
    public ApiResponse<User> getCurrentUserInfo(Long userId) {
        User user = userRepository.findByUserId(userId);
        return ApiResponse.<User>builder()
                .code(200)
                .data(user)
                .message("success get current user info")
                .build();
    }
}
