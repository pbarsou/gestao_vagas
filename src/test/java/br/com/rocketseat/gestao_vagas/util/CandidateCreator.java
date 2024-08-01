package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.candidate.CandidateEntity;
import br.com.rocketseat.gestao_vagas.modules.candidate.dto.ProfileCandidateResponseDTO;

import java.util.UUID;

public class CandidateCreator {

    public static CandidateEntity createCandidateToBeSaved() {

        return CandidateEntity.builder()
                .username("mihoazuki")
                .name("Azuki Miho")
                .email("miho.azuki@gmail.com")
                .password("azukizinha")
                .description("Dubladora. Meu objetivo é me tornar uma grande dubladora e ganhar o papel da protagonista em um anime do Ashirogi Muto.")
                .build();
    }

    public static CandidateEntity createValidCandidate() {

        return CandidateEntity.builder()
                .id(UUID.fromString("f90a52a2-1135-453c-974c-b54c5fe7b530"))
                .username("mihoazuki")
                .name("Azuki Miho")
                .email("miho.azuki")
                .password("azukizinha")
                .description("Dubladora. Meu objetivo é me tornar uma grande dubladora e ganhar o papel da protagonista em um anime do Ashirogi Muto.")
                .build();
    }

    public static CandidateEntity createValidUpdatedCandidate() {

        return CandidateEntity.builder()
                .id(UUID.fromString("f90a52a2-1135-453c-974c-b54c5fe7b530"))
                .username("moritakamashiro")
                .name("Mashiro Moritaka")
                .email("mashiro.moritaka@gmail.com")
                .password("azukizinha")
                .description("Mangaká. Meu objetivo é me tornar o maior mangaká de todos os tempos.")
                .build();
    }

    public static ProfileCandidateResponseDTO createProfileCandidateResponse() {

        return ProfileCandidateResponseDTO.builder()
                .id(UUID.fromString("f90a52a2-1135-453c-974c-b54c5fe7b530"))
                .username("moritakamashiro")
                .name("Mashiro Moritaka")
                .email("mashiro.moritaka@gmail.com")
                .description("Mangaká. Meu objetivo é me tornar o maior mangaká de todos os tempos.")
                .build();
    }
}
