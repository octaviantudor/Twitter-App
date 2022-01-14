package com.unibuc.twitterapp.persistence.repository;

import com.unibuc.twitterapp.persistence.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
}
