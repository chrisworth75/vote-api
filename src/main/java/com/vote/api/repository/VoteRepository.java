package com.vote.api.repository;

import com.vote.api.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserIdAndPollId(Long userId, Long pollId);
    List<Vote> findByPollId(Long pollId);

    @Query("SELECT v.option.id, COUNT(v) FROM Vote v WHERE v.poll.id = :pollId GROUP BY v.option.id")
    List<Object[]> countVotesByPollId(@Param("pollId") Long pollId);
}
