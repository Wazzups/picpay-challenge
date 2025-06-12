package com.wazzups.picpaychallengev2.infra.service.listener;

import com.wazzups.picpaychallengev2.application.dto.TransferCompleteEvent;
import com.wazzups.picpaychallengev2.infra.service.NotificationClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
public class TransferNotificationListener {

    private final NotificationClient notificationClient;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onTransferCompleted(TransferCompleteEvent event) {
        String msg = String.format("Received amount: " + event.amount() + "$ on the wallet");
        notificationClient.notifyUser(event.payeeId(), msg);
    }
}
