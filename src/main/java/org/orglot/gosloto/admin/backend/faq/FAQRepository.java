package org.orglot.gosloto.admin.backend.faq;

import org.orglot.gosloto.domain.faq.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQ, Long> {
}
