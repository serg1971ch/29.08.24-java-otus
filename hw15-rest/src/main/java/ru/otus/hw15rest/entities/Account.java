package ru.otus.hw15rest.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw15rest.entities.Transfer;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "balance")
    private int balance;

    @Column(name = "is_blocked")
    private boolean blocked;

    @OneToMany(mappedBy = "accountSourceId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transfer> transfersSent = new ArrayList<>();

    @OneToMany(mappedBy = "accountTargetId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transfer> transfersReceived = new ArrayList<>();

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<Transfer> getTransfersReceived() {
        return transfersReceived;
    }

    public void setTransfersReceived(List<Transfer> transfersReceived) {
        this.transfersReceived = transfersReceived;
    }

    public String getId() {
        return id;
    }

    public void setTransfersSent(List<Transfer> transfersSent) {
        this.transfersSent = transfersSent;
    }

    public List<Transfer> getTransfersSent() {
        return transfersSent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isBlocked() {
        return blocked;
    }
}
