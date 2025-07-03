package sailing.bootcamp.spring.user.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public enum Role {
    USER("ROLE_USER"),
    ADMIN("ROLE_USER", "ROLE_ADMIN");

    private final List<String> roles;

    Role(String... roles){
        this.roles = Arrays.asList(roles);
    }

    public Collection<? extends GrantedAuthority> getRoles(){
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
