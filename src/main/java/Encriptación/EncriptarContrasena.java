//Externo al proyecto

package Encriptación;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarContrasena {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String[] contrasenas = {"123"};
     
        for (String contrasena : contrasenas) {
            String encriptada = encoder.encode(contrasena);
            System.out.println("Contraseña original: " + contrasena + " -> Encriptada: " + encriptada);
        }
    }
}
// En este archivo se encriptan las contraseñas para los inserts, es eso en el {"123"} ponen lo que quieran encriptar, ojo, para que funcione deben darle 
// click derecho a este mismo archivo y tocar "Run File" se mostrara un output con la contraseña encriptada y eso lo pegan en el espacio de contraseña de MySQL