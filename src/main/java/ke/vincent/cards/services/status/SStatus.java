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
    public Optional<EStatus> getByName(String name) {
        return statusDAO.findByName(name);
    }

    @Override
    public EStatus getByName(String name, Boolean handleException) {
        Optional<EStatus> status = getByName(name);
        if (!status.isPresent() && handleException) {
            throw new NotFoundException("status with specified name not found", "status");
        }
        return status.get();
    }
    
}
