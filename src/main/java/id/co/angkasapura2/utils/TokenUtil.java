package id.co.angkasapura2.utils;

import id.co.angkasapura2.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtil {
    private static final String secret = "asduek84&4%6jkeu&ku7j9uel0+sd[jdli!jasdi76niry";

    private static final String DELIMITER = ",";

    private static final long EXPIRED_TIME = 5 * 60 * 1000;

    public static String createToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,
                signatureAlgorithm.getJcaName());
        String[] temp = new String[user.getRoles().size()];
        for (int i=0; i<user.getRoles().size(); i++)
            temp[i] = String.valueOf(user.getRoles().get(i).getId());
        JwtBuilder builder = Jwts.builder()
                .setId(user.getId())
                .setIssuedAt(now)
                .setSubject(implode(temp, DELIMITER))
                .signWith(signatureAlgorithm, signingKey);

        if (EXPIRED_TIME > 0){
            long l = now.getTime() + EXPIRED_TIME;
            builder.setExpiration(new Date(l));
        }

        return builder.compact();
    }

    private static String implode(String[] data, String delimiter){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<data.length; i++){
            sb.append(data[i]);
            if (i != data.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    public static boolean authenticate(String token) {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter
                    .parseBase64Binary(secret)).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Integer[] getRoles(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter
                .parseBase64Binary(secret)).parseClaimsJws(token).getBody();
        String roleString = claims.getSubject();
        String[] ss = roleString.split(DELIMITER);
        Integer [] nn = new Integer[ss.length];
        int i = 0;
        for(String s : ss){
            nn[i] = Integer.parseInt(s);
        }
        return nn;
    }

    public static String getUserId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
                    .getBody();
            return claims.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}