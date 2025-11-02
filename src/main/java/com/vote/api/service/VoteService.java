package com.vote.api.service;

import com.vote.api.model.Poll;
import com.vote.api.model.PollOption;
import com.vote.api.model.User;
import com.vote.api.model.Vote;
import com.vote.api.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PollService pollService;

    public Vote castVote(Long userId, Long pollId, Long optionId) {
        User user = userService.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Poll poll = pollService.getPollById(pollId)
            .orElseThrow(() -> new RuntimeException("Poll not found"));

        PollOption option = poll.getOptions().stream()
            .filter(o -> o.getId().equals(optionId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Option not found"));

        // Check if user already voted on this poll
        Optional<Vote> existingVote = voteRepository.findByUserIdAndPollId(userId, pollId);
        if (existingVote.isPresent()) {
            // Update existing vote
            Vote vote = existingVote.get();
            vote.setOption(option);
            return voteRepository.save(vote);
        } else {
            // Create new vote
            Vote vote = new Vote();
            vote.setUser(user);
            vote.setPoll(poll);
            vote.setOption(option);
            return voteRepository.save(vote);
        }
    }

    public Map<Long, Long> getPollResults(Long pollId) {
        List<Object[]> results = voteRepository.countVotesByPollId(pollId);
        Map<Long, Long> voteCount = new HashMap<>();
        for (Object[] result : results) {
            Long optionId = (Long) result[0];
            Long count = (Long) result[1];
            voteCount.put(optionId, count);
        }
        return voteCount;
    }

    public Optional<Vote> getUserVoteForPoll(Long userId, Long pollId) {
        return voteRepository.findByUserIdAndPollId(userId, pollId);
    }

    public List<Vote> getAllUserVotes(Long userId) {
        return voteRepository.findByUserId(userId);
    }
}
