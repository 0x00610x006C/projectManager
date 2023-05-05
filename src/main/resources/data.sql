INSERT into "users" values (1, 'admin', '$2a$10$zOsO/iNpXcZ/kpFFZdKN2e1k42TEfc8H/fGgPjp7GL2tWstMQy31W', 'ADMIN');
INSERT into "users" values (2, 'user', '$2a$10$zOsO/iNpXcZ/kpFFZdKN2e1k42TEfc8H/fGgPjp7GL2tWstMQy31W', 'USER');

INSERT into "projects" values (1, 'first project', null);
INSERT into "projects" values (2, 'first sub', 1);
INSERT into "projects" values (3, 'second sub', 1);
INSERT into "projects" values (4, 'another sub', 2);
INSERT into "projects" values (5, 'and another sub', 2);
INSERT into "projects" values (6, 'second project', null);
