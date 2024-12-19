package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
    private final String fullName;
    private final String avatarUrl;
    private final String logoUrl;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String fullName, String avatarUrl, String logoUrl) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return this.fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}