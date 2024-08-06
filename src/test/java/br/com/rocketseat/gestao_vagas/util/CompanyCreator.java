package br.com.rocketseat.gestao_vagas.util;

import br.com.rocketseat.gestao_vagas.modules.company.dto.ProfileCompanyResponseDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;

import java.util.UUID;

public class CompanyCreator {

    public static CompanyEntity createCompanyToBeSaved() {

        return CompanyEntity.builder()
                .name("Ambev")
                .username("ambev")
                .email("ambev@ab-inbev.com")
                .password("ambev@1234")
                .website("https://www.ambev.com.br")
                .description("Maior cervejaria da América Latina.")
                .build();
    }

    public static CompanyEntity createValidCompany() {

        return CompanyEntity.builder()
                .id(UUID.fromString("a62fb649-dec4-44d8-9a7a-faf78388137a"))
                .name("Ambev")
                .username("ambev")
                .email("ambev@ab-inbev.com")
                .password("ambev@1234")
                .website("https://www.ambev.com.br")
                .description("Maior cervejaria da América Latina.")
                .build();
    }

    public static CompanyEntity createValidUpdateCompany() {

        return CompanyEntity.builder()
                .name("Ab-inBev")
                .username("ab-inbev")
                .email("ambev@ab-inbev.com")
                .password("ambev@1234")
                .website("https://www.ambev.com.br")
                .description("Maior cervejaria da América Latina.")
                .build();
    }

    public static ProfileCompanyResponseDTO createCompanyResponse() {

        return ProfileCompanyResponseDTO.builder()
                .name("Ambev")
                .username("ambev")
                .email("ambev@ab-inbev.com")
                .website("https://www.ambev.com.br")
                .description("Maior cervejaria da América Latina.")
                .build();
    }
}
