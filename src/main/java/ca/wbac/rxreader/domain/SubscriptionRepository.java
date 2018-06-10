package ca.wbac.rxreader.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface SubscriptionRepository extends JpaRepository<Feed, String> {
}
