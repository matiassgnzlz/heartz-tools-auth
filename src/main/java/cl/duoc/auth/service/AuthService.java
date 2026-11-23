package cl.duoc.auth.service;

import cl.duoc.auth.dto.AuthResponse;
import cl.duoc.auth.dto.LoginRequest;
import cl.duoc.auth.dto.RegisterRequest;
import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import cl.duoc.auth.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder, JwtService jwt) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarios.findByCorreoIgnoreCase(request.correo())
            .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!encoder.matches(request.password(), usuario.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return new AuthResponse(
            jwt.generar(usuario),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getRol()
        );
    }

    public AuthResponse registrar(RegisterRequest request) {
        if (usuarios.existsByCorreoIgnoreCase(request.correo())) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(request.nombre().trim());
        nuevo.setCorreo(request.correo().trim().toLowerCase());
        nuevo.setPassword(encoder.encode(request.password()));
        nuevo.setRol("CLIENTE");

        Usuario guardado = usuarios.save(nuevo);

        return new AuthResponse(
            jwt.generar(guardado),
            guardado.getNombre(),
            guardado.getCorreo(),
            guardado.getRol()
        );
    }
}