package cl.duoc.auth.dto;

public record AuthResponse(
    String token,
    String nombre,
    String correo,
    String rol
) {}