package fr.unice.polytech.si5.al.tfc.tdd.common.model;


import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    @NonNull
    private String id;

    @NonNull
    private String source;

    @NonNull
    private String receiver;

    @NonNull
    private Integer amount;

    @NonNull
    private LocalDateTime date;

    public Transaction(Transaction transaction) {
        this.source = transaction.getSource();
        this.amount = transaction.getAmount();
        this.receiver = transaction.getReceiver();
        this.date = transaction.getDate();
    }

    public Transaction(@NonNull String source, @NonNull String receiver, @NonNull Integer amount, @NonNull LocalDateTime date) {
        this.source = source;
        this.receiver = receiver;
        this.amount = amount;
        this.date = date;
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }
}
