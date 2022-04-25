package mk.ukim.finki.library.model.enumerations;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    LIBRARIAN;


    @Override
    public String getAuthority() {
        return name();
    }
}
