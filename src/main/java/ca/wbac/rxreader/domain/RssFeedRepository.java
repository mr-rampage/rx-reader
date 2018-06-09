package ca.wbac.rxreader.domain;

import ca.wbac.rxreader.domain.Rss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
public interface RssFeedRepository extends JpaRepository<Rss, String> {
}
