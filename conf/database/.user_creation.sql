CREATE USER 'conektor_dev_app'@'localhost'
  IDENTIFIED BY '%8FYRyg6oH0xLe@p!ca^fUNkKHNUp^QqF3u%ahJCziCsh^HQ0qrSfy3RaIIs8ld9QWUL7FpT!Jd5SbDp$3ZxjDwm0AsXD8ShT7Iy';

GRANT SELECT, UPDATE, DELETE, INSERT ON conektor_dev.* TO 'conektor_dev_app'@'localhost';