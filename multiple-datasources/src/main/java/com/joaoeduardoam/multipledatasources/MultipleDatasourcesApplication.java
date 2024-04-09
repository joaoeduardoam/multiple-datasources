package com.joaoeduardoam.multipledatasources;

import com.joaoeduardoam.multipledatasources.comment.entities.Comment;
import com.joaoeduardoam.multipledatasources.comment.repositories.CommentRepository;
import com.joaoeduardoam.multipledatasources.post.entities.Post;
import com.joaoeduardoam.multipledatasources.post.repositories.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class MultipleDatasourcesApplication {

	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(MultipleDatasourcesApplication.class, args);
	}

	@Bean
	ApplicationRunner runner(){
		return args -> {
			var post1 = new Post(2L, "POST 1");
			var post2 = new Post(3L, "POST 2");
			var comment1 = new Comment(3L, "COMMENT 1", 1L);
			var comment2 = new Comment(4L, "COMMENT 2", 2L);

			postRepository.save(post1);
			postRepository.save(post2);
			commentRepository.save(comment1);
			commentRepository.save(comment2);

//			System.out.println(createPost(post1));
//			System.out.println(createPost(post2));
//			System.out.println(createComment(comment1));
//			System.out.println(createComment(comment2));

		};

	}
//
//	@Bean
//	RestClient crudClient(){
//		return RestClient.create("http://localhost:8080");
//	}
//
//	Post createPost(Post post){
//		return crudClient().post()
//				.uri("/posts")
//				.body(post)
//				.retrieve()
//				.body(Post.class);
//    }
//
//	Comment createComment(Comment comment){
//		return crudClient().post()
//				.uri("/comments")
//				.body(comment)
//				.retrieve()
//				.body(Comment.class);
//	}

}


@Service
@RequiredArgsConstructor
class PostService{
	private final PostRepository postRepository;
	public Post createPost(Post post){
		return postRepository.save(post);
	}

}

@Service
@RequiredArgsConstructor
class CommentService{
	private final CommentRepository commentRepository;
	public Comment createComment(Comment comment){
		return commentRepository.save(comment);
	}

}

@RestController
@RequestMapping
@RequiredArgsConstructor
class MultipleDataSourceController{

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	@GetMapping("/addData")
	public String addData2DB() {

		postRepository.saveAll(Stream.of(new Post(10L, "POST 10"), new Post(11L, "POST11")).collect(Collectors.toList()));
		commentRepository.saveAll(Stream.of(new Comment(10L, "COMMENT 10", 10L),
											new Comment(11L, "COMMENT 11", 11L)).collect(Collectors.toList()));
		return "Data Added Successfully";
	}

	@GetMapping("/getPosts")
	public List<Post> getPosts() {
		return postRepository.findAll();
	}

	@GetMapping("/getComments")
	public List<Comment> getComments() {
		return commentRepository.findAll();
	}


}

//@RestController
//@RequestMapping("comments")
//@RequiredArgsConstructor
//class CommentController{
//	private final CommentService commentService;
//
//	@PostMapping
//	public Comment createComment(@RequestBody Comment comment){
//		return commentService.createComment(comment);
//	}
//
//}