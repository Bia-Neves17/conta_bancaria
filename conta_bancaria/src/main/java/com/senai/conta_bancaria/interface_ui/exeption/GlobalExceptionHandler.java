package com.senai.conta_bancaria.interface_ui.exeption;

import com.senai.conta_bancaria.domain.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaxaInvalidaException.class)
    public ProblemDetail handlerTaxaInvalida (TaxaInvalidaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
           HttpStatus.BAD_REQUEST,
           "Taxa não encontrada ou inválida.",
           ex.getMessage(),
           request.getRequestURI()
        );
    }

    @ExceptionHandler(PagamentoInvalidoException.class)
    public ProblemDetail handlerPagamentoInvalido (PagamentoInvalidoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Pagamento inválido. Erro ao processar.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(PagamentoNaoPendenteException.class)
    public ProblemDetail handlerPagamentoNaoPendente (PagamentoNaoPendenteException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "O pagamento não está pendente",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(PagamentoNaoAssociadoTaxaException.class)
    public ProblemDetail handlerPagamentoNaoAssociadoTaxa (PagamentoNaoAssociadoTaxaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST, //arrumar status
                "O pagamento deve ter pelo menos uma taxa associada",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(BoletoVencidoException.class)
    public ProblemDetail handlerBoletoVencido (BoletoVencidoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST, //arrumar status
                "o boleto está vencido e não pode ser pago",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ValoresNegativosExeption.class)
    public  ProblemDetail handlerValoresNegativos (ValoresNegativosExeption ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Valores negativos não são permitidos.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ContaMesmoTipoException.class)
    public ProblemDetail handlerContaMesmoTipo (ContaMesmoTipoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.CONFLICT,
                "O cliente já possui uma conta desse tipo.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ProblemDetail handlerEntidadeNaoEncontrada (EntidadeNaoEncontradaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.NOT_FOUND,
                "Entidade não existente ou inativo(a)",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(RendimentoInvalidoException.class)
    public ProblemDetail handlerRendimentoInvalido (RendimentoInvalidoException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Rendimento deve ser aplicado apenas em conta poupança.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ProblemDetail handlerSaldoInsuficiente (SaldoInsuficienteException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Saldo insuficiente para realizar a operação.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TipoDeContaInvalidaException.class)
    public ProblemDetail handlerTipoDeContaInvalida (TipoDeContaInvalidaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Tipo de conta inválida.",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(TransferirParaMesmaContaException.class)
    public ProblemDetail handlerTransferirParaMesmaConta (TransferirParaMesmaContaException ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Não é possível transferir para a mesma conta",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)//
    public ProblemDetail handlerException (Exception ex, HttpServletRequest request){
        return ProblemDetailUtils.buildProblem(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Exception",
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail badRequest(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetailUtils.buildProblem(
                HttpStatus.BAD_REQUEST,
                "Erro de validação",
                "Um ou mais campos são inválidos",
                request.getRequestURI()
        );

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        problem.setProperty("errors", errors);
        return problem;
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Tipo de parâmetro inválido");
        problem.setDetail(String.format(
                "O parâmetro '%s' deve ser do tipo '%s'. Valor recebido: '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido",
                ex.getValue()
        ));
        problem.setInstance(URI.create(request.getRequestURI()));
        return problem;
    }
    @ExceptionHandler(ConversionFailedException.class)
    public ProblemDetail handleConversionFailed(ConversionFailedException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Falha de conversão de parâmetro");
        problem.setDetail("Um parâmetro não pôde ser convertido para o tipo esperado.");
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("error", ex.getMessage());
        return problem;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problem.setTitle("Erro de validação nos parâmetros");
        problem.setDetail("Um ou mais parâmetros são inválidos");
        problem.setInstance(URI.create(request.getRequestURI()));

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String campo = violation.getPropertyPath().toString();
            String mensagem = violation.getMessage();
            errors.put(campo, mensagem);
        });
        problem.setProperty("errors", errors);
        return problem;
    }
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<?> handleValidacao(ValidacaoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("erro", ex.getMessage()));
    }
}
