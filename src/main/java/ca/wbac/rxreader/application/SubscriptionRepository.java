package ca.wbac.rxreader.application;

import ca.wbac.rxreader.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface SubscriptionRepository extends JpaRepository<Feed, String> {
}
