package ke.vincent.cards.services.status;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ke.vincent.cards.exceptions.NotFoundException;
import ke.vincent.cards.models.EStatus;
import ke.vincent.cards.repositories.StatusDAO;

@Service
public class SStatus implements IStatus {

    @Autowired
    private StatusDAO statusDAO;

    @Override
    public Optional<EStatus> getById(Integer statusId) {
        return statusDAO.findById(statusId);
    }

    @Override
    public EStatus getById(Integer statusId, Boolean handleException) {
        Optional<EStatus> status = getById(statusId);
        if (!status.isPresent() && handleException) {
            throw new NotFoundException("status with specified id not found", "statusId");
        }
        return status.get();
    }
    
}
