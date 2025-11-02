package com.vote.api.controller;

import com.vote.api.model.Vote;
import com.vote.api.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin(origins = "*")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<?> castVote(@RequestBody Map<String, Long> request) {
        try {
            Long userId = request.get("userId");
            Long pollId = request.get("pollId");
            Long optionId = request.get("optionId");
            Vote vote = voteService.castVote(userId, pollId, optionId);
            return ResponseEntity.ok(vote);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/results/{pollId}")
    public ResponseEntity<Map<Long, Long>> getPollResults(@PathVariable Long pollId) {
        return ResponseEntity.ok(voteService.getPollResults(pollId));
    }

    @GetMapping("/user/{userId}/poll/{pollId}")
    public ResponseEntity<?> getUserVote(@PathVariable Long userId, @PathVariable Long pollId) {
        return voteService.getUserVoteForPoll(userId, pollId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
