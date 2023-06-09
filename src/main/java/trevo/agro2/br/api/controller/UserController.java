package trevo.agro2.br.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import trevo.agro2.br.api.dto.user.UserTokenService;
import trevo.agro2.br.api.model.User;
import trevo.agro2.br.api.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping(value = "user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    @Operation(summary = "Login na api e fornecendo o JTW", tags = "User")
    public ResponseEntity<?> login(@RequestBody @Valid UserTokenService dto) {
        return userService.token(dto);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cadastra um novo usuario na api", tags = "User", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping(value = "/find/{id}")
    @Operation(summary = "Consulta um usuario especifico na api", tags = "User", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> findUser(@PathVariable UUID id) {
        return userService.findUser(id);
    }

    @GetMapping(value = "/list")
    @Operation(summary = "Lista os usuarios da api", tags = "User", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> list() {
        return userService.list();
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Atualiza os dados do usuario", tags = "User", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody @Valid User user) {
        return userService.update(id, user);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Deleta um usuario da api", tags = "User", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }
}
