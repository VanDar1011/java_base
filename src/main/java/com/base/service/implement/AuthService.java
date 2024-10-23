package com.base.service.implement;

import com.base.dto.IntroSpectRequest;
import com.base.dto.IntrospectResponse;
import com.base.entity.User;
import com.base.service.i.IAutheService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
public class AuthService implements IAutheService {
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SCRET_KEY;

    @Override
    public String buildScope(User user) {
        StringJoiner scopes = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {scopes.add(role);});
        }
        return scopes.toString();
    }

    @Override
    public String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder().subject(user.getName()).issuer("test" +
                ".com").issueTime(new Date()).expirationTime(new Date(Instant.now().plus(10,
                ChronoUnit.DAYS).toEpochMilli())).claim("scope",buildScope(user)).build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SCRET_KEY.getBytes()));
        } catch (JOSEException e) {
            log.error("Can't create token " + e.getMessage());
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    @Override
    public IntrospectResponse verifyToken(IntroSpectRequest introSpectRequest) {
        var token = introSpectRequest.getToken();
        try {
            JWSVerifier verifier = new MACVerifier(SCRET_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);
            Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            return new IntrospectResponse(verified && expityTime.after(new Date()));

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }




}
