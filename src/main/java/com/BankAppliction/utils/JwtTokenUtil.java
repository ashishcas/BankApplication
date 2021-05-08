package com.BankAppliction.utils;

import ch.qos.logback.classic.Level;
import com.BankAppliction.controllers.Maincontroller;
import com.BankAppliction.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sun.awt.X11.XSystemTrayPeer;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private String privateKeyPath = "/home/ashish/private_key.pem";


    private String publicKeyPath = "/home/ashish/public_key.pem";

    private static final Logger logger= LoggerFactory.getLogger(JwtTokenUtil.class);

    public static RSAPublicKey readPublicKey(String publicKeyPath) throws Exception {


        System.out.println(publicKeyPath);
        String key = new String(Files.readAllBytes(Paths.get(publicKeyPath)), Charset.defaultCharset());

        String publicKeyPEM = key
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    public RSAPrivateKey readPrivateKey() throws Exception {


        System.out.println(privateKeyPath);

        String key = new String(Files.readAllBytes(Paths.get(privateKeyPath)), Charset.defaultCharset());

        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
    public String generateToken(User user){
        try {
            PrivateKey privateKey = readPrivateKey();
            String token = Jwts
                    .builder()
                    .setSubject(String.valueOf(UUID.randomUUID()))
                    .claim("id", user.get_id())
                    .claim("email",user.getEmailId())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .signWith(SignatureAlgorithm.RS256, privateKey)
                    .compact();
            return token;
        } catch (SignatureException e){
//            Logger.getLogger(JwtTokenUtil.class.getName()).log(Level.ERROR, "exception");
                return "exp";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "exp";
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return "exp";
        } catch (Exception e) {
            e.printStackTrace();
            return  "exp";
        }
    }

    public Claims getTokenClaims (String Token) throws Exception {

        PublicKey publicKey = readPublicKey(publicKeyPath);
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(Token)
                .getBody();
    }

    public Claims validateToken(String token) throws Exception {
        Claims claims;
        try {
             claims= getTokenClaims(token);
        } catch(ExpiredJwtException exp){
            logger.error("Jwt Token expired");
            claims = null;
        }
        return claims;
    }

}
