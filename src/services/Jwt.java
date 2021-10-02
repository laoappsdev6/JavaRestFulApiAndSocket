package services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

public class Jwt {
    static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public static String jwtEncode(String name, String username, String phoneNumber){

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("jwt.secret_key");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return  Jwts.builder()
                .setHeaderParam("type","JWT")
                .setIssuer("https://laoapps.com/")
                .setSubject("users/1300819380")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(6, ChronoUnit.HOURS)))
                .signWith(signatureAlgorithm,signingKey)

                .claim("name",name)
                .claim("username",username)
                .claim("phoneNumber",phoneNumber)

                .compact();
    }

    public static boolean jwtValidation(String token){
        if(!token.isEmpty()){
            try {
                String[] chunks = token.split("\\.");
                Base64.Decoder decoder = Base64.getDecoder();
                String header = new String(decoder.decode(chunks[0]));
                String payload = new String(decoder.decode(chunks[1]));
                return  true;
            }catch(Exception e){
                System.out.println(e.getMessage());
                return false;
            }
        }else{
            return false;
        }

    }
}
