package com.occ.demo.services;


import com.occ.demo.Entities.UserCredential;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.Getter;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SecurityUtil {

    @Inject
    private QueryService queryService;

    public static final String HASHED_PASSWORD_KEY = "hashedPassword";
    public static final String SALT_KEY = "salt";
    public static final String BEARER = "Bearer";

    @Getter
    private SecretKey securityKey;

    @PostConstruct
    private void init() {

        securityKey = generateKey();
    }


    //used on token creation to give the token an expiryDate
    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    //use to compare the password of a user during login. It compares the cleartext password with the hashed password in
    //the database using the corresponding salt
    public boolean passwordsMatch(String dbStoredHashedPassword, String saltText, String clearTextPassword) {
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(saltText));
        String hashedPassword = hashAndSaltPassword(clearTextPassword, salt);
        return hashedPassword.equals(dbStoredHashedPassword);
    }

    //used on user creation to hash a plaintext password before persisting into the database
    public Map<String, String> hashPassword(String clearTextPassword) {
        ByteSource salt = getSalt();
        Map<String, String> credMap = new HashMap<>();
        credMap.put(HASHED_PASSWORD_KEY, hashAndSaltPassword(clearTextPassword, salt));
        credMap.put(SALT_KEY, salt.toHex());
        return credMap;


    }
    //the main hashing algorithm
    private String hashAndSaltPassword(String clearTextPassword, ByteSource salt) {
        return new Sha512Hash(clearTextPassword, salt, 2000000).toHex();
    }

    private ByteSource getSalt() {
        return new SecureRandomNumberGenerator().nextBytes();
    }


    //used by user login to authenticate users
    public boolean authenticateUser(String username, String password) {

        UserCredential user = queryService.findUserByUsername(username);
        if (user == null) {
            return false;
        }

        return passwordsMatch(user.getPassword(), user.getSalt(), password);

    }

    private SecretKey generateKey() {

        return MacProvider.generateKey(SignatureAlgorithm.HS512);


    }


}
