package coupons.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import coupons.dtos.UserDTO;
import coupons.exceptions.DataException;
import coupons.exceptions.MyException;
import coupons.mappers.UserMapper;
import coupons.models.User;
import coupons.repos.UserRepository;


import javax.security.auth.login.LoginException;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public UserDTO create(String firstName, String lastName, String privilege, String username, String password) {
        User user = userRepository.create(firstName, lastName, privilege.toUpperCase(), username, password);
        return UserMapper.instance.map(user);
    }

    public String login(String username, String password) throws DataException, MyException {
        User user = userRepository.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new MyException("Wrong username and password combination.", Response.Status.UNAUTHORIZED);
        }

        LocalDate date = LocalDate.now().plusDays(2);
        Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());
        Algorithm algorithm = Algorithm.HMAC256("secret");

        return JWT.create()
                .withIssuer("auth0")
                .withExpiresAt(new Date(timestamp.getTime()))
                .withClaim("user", user.getId())
                .sign(algorithm);
    }

    public User parseJwt(String token) throws DataException {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        return userRepository.getId(jwt.getClaim("user").asString());
    }


    public UserDTO get(String username) throws DataException {
        return UserMapper.instance.map(userRepository.get(username));
    }

    public UserDTO getId(String id) throws DataException{
        return UserMapper.instance.map(userRepository.getId(id));
    }

}
