package com.adri.pfm.transaction.model.transaction;

import com.adri.pfm.transaction.model.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="PTS_TRANSACTION")
@SequenceGenerator(name = "TRANSACTION_PK", sequenceName = "SEQ_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "TRANSACTION_PK")
    private long transactionId;

    @Column(nullable = false)
    private String concept;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountId")
    private Account account;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus currentStatus;

    private String category;
}
