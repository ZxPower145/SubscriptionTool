package org.tn.subscriptiontool.core.models;

import jakarta.persistence.*;

@Entity
@Table
public class UserTools {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userID;
    private Long toolID;
}
