package com.authentication.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accesses")
public class Access {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accessName;

    @ManyToMany(mappedBy = "accesses")
    private Set<Roles> roles = new HashSet<>();

}
