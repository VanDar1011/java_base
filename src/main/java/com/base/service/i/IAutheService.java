package com.base.service.i;

import com.base.dto.IntroSpectRequest;
import com.base.dto.IntrospectResponse;
import com.base.entity.User;
import com.nimbusds.jose.KeyLengthException;

public interface IAutheService {
    public String buildScope(User user);
    public String generateToken(User user) throws KeyLengthException;
    public IntrospectResponse verifyToken(IntroSpectRequest introSpectRequest);
}
