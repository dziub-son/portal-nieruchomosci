package pl.twojafirma.nieruchomosci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.twojafirma.nieruchomosci.model.Advertisement;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}