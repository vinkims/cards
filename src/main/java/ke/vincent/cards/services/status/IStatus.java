package ke.vincent.cards.services.status;

import java.util.Optional;

import ke.vincent.cards.models.EStatus;

public interface IStatus {

    Optional<EStatus> getByName(String name);

    EStatus getByName(String name, Boolean handleException);
}
