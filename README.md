**blog**

This is a spring boot app. In order for it to work, you need to create a database:

Run docker: 
docker run --name postgresql-container -p 5000:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=blog -d postgres
-- grant all privileges on database "blog" to postgres;
Run BlogApplication. Tables will be created automatically

Request examples and what they do:

PostController:

    GET http://localhost:8080/api/v1/posts  - get all posts;

    GET http://localhost:8080/api/v1/posts?title={post_name} - get all posts by title;

    GET http://localhost:8080/api/v1/posts?sort=asc  - get all posts sorted by ASC;

    POST http://localhost:8080/api/v1/posts  - add post;
        example of json for post creation :
            {
            "title": "poSt from GR",
            "content": "GR we will test insert of new post",
            "star":true
            }

    PUT http://localhost:8080/api/v1/posts/{id} - edit post by id;

    DELETE http://localhost:8080/api/v1/posts/{id}  - delete post by id;

    GET http://localhost:8080/api/v1/posts/star   - get posts with star;

    PUT http://localhost:8080/api/v1/posts/{id}/star   - add star to post;

    DELETE http://localhost:8080/api/v1/posts/{id}/star  - delete star from post;

    GET http://localhost:8080/api/v1/posts/{id}/full  - get post with comments;
  
CommentController:

    GET http://localhost:8080/api/v1/posts/{postId}/comments - get comments by post id;

    GET http://localhost:8080/api/v1/posts/{postId}/comments/{commentId} - get comment by id;

    POST http://localhost:8080/api/v1/posts/{postId}/comments - add comment;
        example of json for comment creation :
            {
            "text": "second comment , which added to post"
            }

TagController: 

    GET http://localhost:8080/api/v1/posts/tags - get all existed tags;

    POST http://localhost:8080/api/v1/posts/2/tags - add tag to post;
        example of json for comment creation :
            {
            "name": "java"
            }