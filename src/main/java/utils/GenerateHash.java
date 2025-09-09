package utils;

import org.mindrot.jbcrypt.BCrypt;

public class GenerateHash {
    public static void main(String[] args) {
        String password = "proprio123"; // ton mot de passe en clair
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(10));
        System.out.println("Hash pour proprio123 : " + hashed);

        String userPassword = "user123";
        String hashedUser = BCrypt.hashpw(userPassword, BCrypt.gensalt(10));
        System.out.println("Hash pour user123 : " + hashedUser);
    }
}
