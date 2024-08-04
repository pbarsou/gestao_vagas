package br.com.rocketseat.gestao_vagas.exceptions;

public class JobApplicationFoundException extends RuntimeException {

    public JobApplicationFoundException() { super("Candidate has already applied for this job.");}
}
