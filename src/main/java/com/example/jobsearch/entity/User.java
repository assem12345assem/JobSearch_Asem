package com.example.jobsearch.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_table")
public class User implements UserDetails {
    @Id
    private String email;
    private String phoneNumber;
    private String userName;
    private String userType;
    private String password;
    private String photo;
    private boolean enabled;
    private String resetPasswordToken;


    @OneToOne(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Applicant applicant;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Employer employer;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("FULL"));
    }

    public String getUserName() {
        return userName;
    }
    @Override
    public String getUsername() {
        return email;
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

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    private Collection<Role> roles;
}
