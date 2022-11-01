package aubay.lu.projetrh.security;

import aubay.lu.projetrh.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SecurityUserDetails implements UserDetails {

    private UUID id;
    private String login;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public SecurityUserDetails(Utilisateur utilisateur){
        this.id = utilisateur.getId();
        this.login = utilisateur.getUserLogin();
        this.password = utilisateur.getUserPassword();
        this.active = true;

        authorities.add(new SimpleGrantedAuthority(utilisateur.getRole().getDenomination()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public UUID getId(){
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
