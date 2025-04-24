package ru.otus.hw15rest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.Optional;

@Entity
@Table(name = "transfers")
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account accountSourceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    private Account accountTargetId;

    @Column(name = "message")
    private String message;

    @Column(name = "amount")
    private int amount;

    public Transfer(String id, Optional<Account> accountSource, Account accountTarget, String message, int amount) {
        this.id = id;
        this.accountSourceId = accountSource.get();
        this.accountTargetId = accountTarget;
        this.message = message;
        this.amount = amount;
    }

    public Account getAccountSource() {
        return accountSourceId;
    }

    public void setAccountSource(Account accountSource) {
        this.accountSourceId = accountSource;
    }

    public Account getAccountTarget() {
        return accountTargetId;
    }

    public void setAccountTarget(Account accountTarget) {
        this.accountTargetId = accountTarget;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
