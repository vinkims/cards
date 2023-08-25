package ke.vincent.cards.services.status;

import java.util.Optional;

import ke.vincent.cards.models.EStatus;

public interface IStatus {
    
    Optional<EStatus> getById(Integer statusId);

    EStatus getById(Integer statusId, Boolean handleException);
}
