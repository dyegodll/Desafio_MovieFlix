INSERT INTO tb_genre(name) VALUES ('ACTION');
INSERT INTO tb_genre(name) VALUES ('DRAMA');
INSERT INTO tb_genre(name) VALUES ('FANTASY');

INSERT INTO tb_movie(img_url, sub_title, synopsis, title, year, genre_id) VALUES ('https://lh4.googleusercontent.com/xbryw1wWK0SAxsKaNRX7QnMcaTWTZg9s24y_g-XtpPtOTNE6I5MWN_6rLrBgokcPYawGYMR8QMGurel-E4k5sn2qnjiV7Ru9ulQuBowuC3aUhbc0Y8FOzrnQGZRA-94wYr44ja1XH6M2306IDg', 'A Fantástica Fábrica de Chocolate', 'O dono da maior fábrica de chocolate do mundo decide criar um concurso para escolher o sucessor do seu império.','1-Charlie et la Chocolaterie', 2005, 1);
INSERT INTO tb_movie(img_url, sub_title, synopsis, title, year, genre_id) VALUES ('https://lh4.googleusercontent.com/xbryw1wWK0SAxsKaNRX7QnMcaTWTZg9s24y_g-XtpPtOTNE6I5MWN_6rLrBgokcPYawGYMR8QMGurel-E4k5sn2qnjiV7Ru9ulQuBowuC3aUhbc0Y8FOzrnQGZRA-94wYr44ja1XH6M2306IDg', 'A Fantástica Fábrica de Chocolate', 'O dono da maior fábrica de chocolate do mundo decide criar um concurso para escolher o sucessor do seu império.','2-Charlie et la Chocolaterie', 2005, 2);
INSERT INTO tb_movie(img_url, sub_title, synopsis, title, year, genre_id) VALUES ('https://lh4.googleusercontent.com/xbryw1wWK0SAxsKaNRX7QnMcaTWTZg9s24y_g-XtpPtOTNE6I5MWN_6rLrBgokcPYawGYMR8QMGurel-E4k5sn2qnjiV7Ru9ulQuBowuC3aUhbc0Y8FOzrnQGZRA-94wYr44ja1XH6M2306IDg', 'A Fantástica Fábrica de Chocolate', 'O dono da maior fábrica de chocolate do mundo decide criar um concurso para escolher o sucessor do seu império.','3-Charlie et la Chocolaterie', 2005, 3);

INSERT INTO tb_user (name, email, password) VALUES ('Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (name, email, password) VALUES ('Ana', 'ana@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');

INSERT INTO tb_role (authority) VALUES ('ROLE_VISITOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_MEMBER');

INSERT INTO tb_review(text, user_id, movie_id) VALUES ('Amei esse filme!', 2, 1);
INSERT INTO tb_review(text, user_id, movie_id) VALUES ('Fantástico!', 2, 2);
INSERT INTO tb_review(text, user_id, movie_id) VALUES ('Não gostei muito...', 2, 3);

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);