package com.senai.conta_bancaria.interface_ui.controller;

import com.senai.conta_bancaria.application.dto.ServicoDTO;
import com.senai.conta_bancaria.application.service.ServicoAppService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Serviços", description = "Gerenciamento de serviços da conta bancária")
@RestController
@RequestMapping("/servicos")
public class ServicoController {
    private final ServicoAppService service;

    public ServicoController(ServicoAppService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar um novo serviço",
            description = "Adiciona um novo serviço à base de dados após validações de preço e duração",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(name = "Exemplo válido", value = """
                                        {
                                          "numeroConta": "12345-6",
                                          "tipoConta": "CORRENTE",
                                          "saldo": 500.0,
                                          "clienteId": "1a2b3c"
                                        }
                                    """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Serviço cadastrado com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo inválido", value = "\"Saldo inicial deve ser maior ou igual a 0\""),
                                            @ExampleObject(name = "Tipo inválido", value = "\"Tipo da conta não reconhecido\"")
                                    }
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<ServicoDTO> criar(@Valid @org.springframework.web.bind.annotation.RequestBody ServicoDTO dto) {
        return ResponseEntity
                .status(201)
                .body(service.salvar(dto));
    }

    @Operation(
            summary = "Listar todas as contas",
            description = "Retorna todas as contas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ServicoDTO>>listar() {
        return ResponseEntity
                .ok(service.listar());

    }

    @Operation(
            summary = "Buscar conta por numero",
            description = "Retorna uma conta existente a partir do seu numero",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser buscada", example = "123-4")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta encontrada"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta não encontrada.\"")
                            )
                    )
            }
    )
    @GetMapping("/{numero}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.buscarPorId(id));
    }

    @Operation(
            summary = "Atualizar uma conta",
            description = "Atualiza os dados de uma conta existente com novas informações",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser atualizada", example = "123-4")
            },
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ServicoDTO.class),
                            examples = @ExampleObject(name = "Exemplo de atualização", value = """
                        {
                          "numeroConta": "12345-6",
                          "tipoConta": "CORRENTE",
                          "saldo": 500.0,
                          "clienteId": "1a2b3c"
                        }
                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Conta atualizada com sucesso"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Erro de validação",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(name = "Saldo inválido", value = "\"Saldo inicial deve ser maior ou igual a 0\""),
                                            @ExampleObject(name = "Tipo inválido", value = "\"Tipo da conta não reconhecido\"")
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"Conta não encontrada.\"")
                            )
                    )
            }
    )
    @PutMapping("/{numero")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id, @Valid @org.springframework.web.bind.annotation.RequestBody ServicoDTO dto) {
        return ResponseEntity
                .ok(service.atualizar(id, dto));
    }

    @Operation(
            summary = "Deletar uma conta",
            description = "Remove uma conta da base de dados a partir do seu numero",
            parameters = {
                    @Parameter(name = "numero", description = "numero da conta a ser deletada", example = "123-4")
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "conta removida com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "conta não encontrada",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "\"conta não encontrada.\"")
                            )
                    )
            }
    )
    @DeleteMapping("/{numero}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
