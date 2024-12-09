package com.adri.pfm.transaction.model.transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="PTS_TRANSACTION_STATUS_DETAIL",
        uniqueConstraints = { @UniqueConstraint(columnNames = {"transactionId", "status"}) })
@SequenceGenerator(name = "TRANSACTION_STATUS_DETAIL_PK", sequenceName = "SEQ_TRANSACTION_STATUS_DETAIL")
public class TransactionStatusDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "TRANSACTION_STATUS_DETAIL_PK")
    private long transactionStatusId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "transactionId")
    private Transaction transaction;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date statusUpdateDate;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

}
