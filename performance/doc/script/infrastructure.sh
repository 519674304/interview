docker run -d -p 3306:3306 \
  -v ~/soft/mysql/log:/var/log/mysql \
  -v ~/soft/mysql/data:/var/lib/mysql \
  -v ~/soft/mysql/conf:/etc/mysql \
  -v ~/soft/mysql/mysql-files:/var/lib/mysql-files \
  -e MYSQL_ROOT_PASSWORD=root --name mysql mysql

docker run --restart=always \
-p 6379:6379 \
--name redis-master \
-v ~/soft/redis/master/redis.conf:/etc/redis/redis.conf \
-v ~/soft/redis/master/data:/data \
-d redis redis-server /etc/redis/redis.conf

docker run --restart=always \
-p 6380:6380 \
--name redis-replica \
-v ~/soft/redis/replica/redis.conf:/etc/redis/redis.conf \
-v ~/soft/redis/replica/data:/data \
-d redis redis-server /etc/redis/redis.conf


docker run --restart=always \
-p 6480:6480 \
--name redis-replica \
-v ~/soft/redis/replica/redis.conf:/etc/redis/redis.conf \
-v ~/soft/redis/replica/data:/data \
-d redis redis-server /etc/redis/redis.conf


docker run --restart=always \
-p 26379:26379 \
--name redis-sentinel01 \
-v ~/soft/redis/sentinel01.conf:/etc/redis/sentinel01.conf \
-d redis redis-server /etc/redis/sentinel01.conf --sentinel

docker run --restart=always \
-p 26380:26380 \
--name redis-sentinel02 \
-v ~/soft/redis/sentinel02.conf:/etc/redis/sentinel02.conf \
-d redis redis-server /etc/redis/sentinel02.conf --sentinel

docker run --restart=always \
-p 26381:26381 \
--name redis-sentinel03 \
-v ~/soft/redis/sentinel03.conf:/etc/redis/sentinel03.conf \
-d redis redis-server /etc/redis/sentinel03.conf --sentinel

