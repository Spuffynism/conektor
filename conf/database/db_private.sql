INSERT INTO user_usr (usr_id, usr_username, usr_email, usr_password, usr_permission) VALUES
  (1, 'admin', 'admin.conektor@ndlr.xyz',
   '$argon2i$v=19$m=65536,t=10,p=4$7H1XM8VGOs3fccpWgKkSPpm17ub7HPa6D/zCux8vuG8$f3Dx+Gv6Uuf50XCEQFFx73ytA9+tXA0f1zmPIkp7XcrB8yYHuI9EazmPw4Xpkt0dxUSG14QFHGl6MwOloMEHXA',
   0x1 | 0x2);

INSERT INTO user_usr (usr_id, usr_username, usr_email, usr_password, usr_permission) VALUES
  (2, 'user', 'user.conektor@ndlr.xyz',
   '$argon2i$v=19$m=65536,t=10,p=4$7H1XM8VGOs3fccpWgKkSPpm17ub7HPa6D/zCux8vuG8$f3Dx+Gv6Uuf50XCEQFFx73ytA9+tXA0f1zmPIkp7XcrB8yYHuI9EazmPw4Xpkt0dxUSG14QFHGl6MwOloMEHXA',
   0x1);

INSERT INTO user_usr (usr_id, usr_username, usr_email, usr_password, usr_permission) VALUES
  (3, 'none', 'none.conektor@ndlr.xyz',
   '$argon2i$v=19$m=65536,t=10,p=4$7H1XM8VGOs3fccpWgKkSPpm17ub7HPa6D/zCux8vuG8$f3Dx+Gv6Uuf50XCEQFFx73ytA9+tXA0f1zmPIkp7XcrB8yYHuI9EazmPw4Xpkt0dxUSG14QFHGl6MwOloMEHXA',
   0x0);