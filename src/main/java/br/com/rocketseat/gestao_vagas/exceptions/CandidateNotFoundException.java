package br.com.rocketseat.gestao_vagas.exceptions;

public class CandidateNotFoundException extends RuntimeException {

    public CandidateNotFoundException() {
        super("Candidate not found.");
    }
}
