package src.com.library.system.application.exception;
// Excepciones simples de tipo Runtime
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) { super("Usuario no encontrado con ID: " + userId); }
}