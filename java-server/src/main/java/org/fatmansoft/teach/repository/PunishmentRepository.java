package org.fatmansoft.teach.repository;

import org.fatmansoft.teach.models.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PunishmentRepository extends JpaRepository<Punishment,Integer> {
}
