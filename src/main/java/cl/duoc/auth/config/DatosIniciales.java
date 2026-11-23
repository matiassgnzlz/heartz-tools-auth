package cl.duoc.auth.config;

import cl.duoc.auth.model.Usuario;
import cl.duoc.auth.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner usuariosIniciales(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            crearSiNoExiste(repo, encoder, "Administrador", "admin@heartztools.cl", "Admin123!", "ADMIN");
            crearSiNoExiste(repo, encoder, "Vendedor Tienda", "vendedor@heartztools.cl", "Vendedor123!", "VENDEDOR");
            crearSiNoExiste(repo, encoder, "Cliente Demo", "cliente@heartztools.cl", "Cliente123!", "CLIENTE");
        };
    }

    private void crearSiNoExiste(UsuarioRepository repo, PasswordEncoder encoder,
                                 String nombre, String correo, String clave, String rol) {
        if (repo.findByCorreoIgnoreCase(correo).isEmpty()) {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setCorreo(correo);
            u.setPassword(encoder.encode(clave));
            u.setRol(rol);
            repo.save(u);
        }
    }
}