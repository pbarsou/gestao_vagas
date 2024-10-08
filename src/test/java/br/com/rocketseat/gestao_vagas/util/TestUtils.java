package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generatedToken(UUID companyId) {
        Algorithm algorithm = Algorithm.HMAC256("vagas_@123#");

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create()
                .withExpiresAt(expiresIn)
                .withIssuer("vagasnet")
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withSubject(companyId.toString())
                .sign(algorithm);

        return token;
    }
}
