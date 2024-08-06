package br.com.rocketseat.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.rocketseat.gestao_vagas.modules.company.dto.CreateUpdateJobDTO;
import br.com.rocketseat.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.gestao_vagas.util.CompanyCreator;
import br.com.rocketseat.gestao_vagas.util.JobCreator;
import br.com.rocketseat.gestao_vagas.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static br.com.rocketseat.gestao_vagas.util.TestUtils.generatedToken;
import static br.com.rocketseat.gestao_vagas.util.TestUtils.objectToJson;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void ShouldBeAbleToCreateANewJob() throws Exception {

        /*var company = CompanyEntity.builder()
                .name("COMPANY_NAME")
                .username("COMPANY_USERNAME")
                .password("1234567890")
                .email("email@company.com")
                .description("COMPANY_DESCRIPTION")
                .website("COMPANY_WEBSITE")
                .build();

        company = companyRepository.saveAndFlush(company);*/

        /*var createJobDTO = CreateUpdateJobDTO.builder()


                .description("DESCRIPTION_TEST")
                .benefits("BENEFITS_TEST")
                .level("LEVEL_TEST")
                .build();*/

        var companyTest = CompanyCreator.createCompanyToBeSaved();
        companyTest = companyRepository.saveAndFlush(companyTest);

        var createJobDTOTest = JobCreator.createJobToBeSaved();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createJobDTOTest))
                        .header("Authorization", TestUtils.generatedToken(companyTest.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void ShouldNotToBeAbleToCreateANewJob_IfCompanyNotFound() throws Exception {

        var createJobDTOTest = JobCreator.createJobToBeSaved();

        mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createJobDTOTest))
                .header("Authorization", TestUtils.generatedToken(UUID.randomUUID())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
