package net.medrag.account_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Account Data
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOUNT_DATA")
public final class AccountData {

    @Id
    @Column(name = "ID")
    Integer id;

    @Column(name = "AMOUNT")
    Long amount;
}
