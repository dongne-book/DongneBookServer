package com.dongnaebook.domain.userrole;

import com.dongnaebook.domain.role.Role;
import com.dongnaebook.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;
}
