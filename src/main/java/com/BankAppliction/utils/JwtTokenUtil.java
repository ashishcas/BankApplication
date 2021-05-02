package com.BankAppliction.utils;

import ch.qos.logback.classic.Level;
import com.BankAppliction.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtTokenUtil {

    public String generateToken(User user){
        try {
            String token = Jwts
                    .builder()
                    .claim("id", user.get_id())
                    .claim("email",user.getEmailId())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .signWith(SignatureAlgorithm.HS256, "secret")
                    .compact();
            return token;
        } catch (SignatureException e){
//            Logger.getLogger(JwtTokenUtil.class.getName()).log(Level.ERROR, "exception");
                return "exp";
        }
    }
    
}
