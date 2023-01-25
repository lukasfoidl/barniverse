package at.barniverse.backend.barniverse_backend.services;

import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.dto.OfferDto;
import at.barniverse.backend.barniverse_backend.enums.OfferState;
import at.barniverse.backend.barniverse_backend.model.Offer;
import at.barniverse.backend.barniverse_backend.repository.OfferRepository;
import at.barniverse.backend.barniverse_backend.transformer.OfferTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.DATABASE_ERROR;

/**
 * service with offer related functionality
 */
@Service
public class OfferService extends BaseService {

    @Autowired private OfferRepository offerRepository;
    @Autowired private OfferTransformer offerTransformer;
    @Autowired private OfferValidationService offerValidationService;

    /**
     * add an offer to the database
     * @param offerDto dto which should be saved
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void addOffer(OfferDto offerDto) throws BarniverseException {
        offerDto.setState(OfferState.running);
        addEntity(offerRepository, offerTransformer, offerValidationService, offerDto);
    }

    /**
     * get all saved offers from a specific auction
     * @param id id of the specific auction
     * @return loaded offer dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    public List<OfferDto> getOffersFromAuction(int id) throws BarniverseException {
        List<Offer> offers;
        try {
            offers = offerRepository.findAllByAuction_Id(id);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(offerTransformer, offers);
    }

    /**
     * get all saved offers from a specific user
     * @param id id of the specific user
     * @return loaded offer dtos
     * @throws BarniverseException in case of failure which includes error messages
     */
    public List<OfferDto> getOffersFromUser(int id) throws BarniverseException {
        List<Offer> offers;
        try {
            offers = offerRepository.findAllByUser_Id(id);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }

        return convertToDto(offerTransformer, offers);
    }

    /**
     * update specific offer state to accepted, <br>
     * update offer state to rejected for all other offers of the accepted offers auction
     * @param id id of the specific offer
     * @throws BarniverseException in case of failure which includes error messages
     */
    public void acceptOffer(int id) throws BarniverseException {
        Offer offer = getEntity(offerRepository, id);
        List<Offer> offers;
        try {
            offers = offerRepository.findAllByAuction_Id(offer.getAuction().getId()); // existence check in getEntity()
            for (Offer item : offers) {
                if (item.getId() == id) {
                    item.setState(OfferState.accepted);
                } else {
                    item.setState(OfferState.rejected);
                }
            }
            offerRepository.saveAll(offers);
        } catch (Exception exception) {
            throw new BarniverseException(List.of(DATABASE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, exception);
        }
    }
}
