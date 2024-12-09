package com.adri.pfm.transaction.model.account;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="PTS_ACCOUNT")
@SequenceGenerator(name = "ACCOUNT_PK", sequenceName = "SEQ_ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "ACCOUNT_PK")
    private long accountId;

    @Column(nullable = false)
    private long userAccountId;

    @Column(unique = true, nullable = false)
    private String iban;

    @Column(nullable = false)
    private BigDecimal balance;
}
