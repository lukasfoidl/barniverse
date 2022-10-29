package at.barniverse.backend.barniverse_backend.repository;

import at.barniverse.backend.barniverse_backend.model.Offer;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called offerRepository
// CRUD refers Create, Read, Update, Delete

public interface OfferRepository extends CrudRepository<Offer, Integer> {

}
