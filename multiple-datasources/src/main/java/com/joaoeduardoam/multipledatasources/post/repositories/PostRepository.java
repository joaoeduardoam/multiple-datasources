package com.joaoeduardoam.multipledatasources.post.repositories;

import com.joaoeduardoam.multipledatasources.post.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
