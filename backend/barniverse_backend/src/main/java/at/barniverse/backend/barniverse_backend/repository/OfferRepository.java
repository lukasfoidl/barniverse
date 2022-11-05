package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Offer;
import org.springframework.data.repository.CrudRepository;

/**
 * basic repository for offer entity
 * will be auto implemented by Spring into a Bean called offerRepository
 */
public interface OfferRepository extends CrudRepository<Offer, Integer> {

}
