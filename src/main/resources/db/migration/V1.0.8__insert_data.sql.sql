insert into `authorities` (name) values ('ROLE_USER'), ('ROLE_ADMIN');

insert into `users` (username, first_name, last_name, mail, password) values ('admin', 'admin1', 'admin2', 'mail@admin.com', '$2a$10$qVQa98FcGYJJw9d6y8CgBusEyHKENBnzeY7cx9EwwfJfsaZ9ArNmi'),
                                                                             ('user', 'user', 'user', 'user@admin.com', '$2a$10$qVQa98FcGYJJw9d6y8CgBusEyHKENBnzeY7cx9EwwfJfsaZ9ArNmi')