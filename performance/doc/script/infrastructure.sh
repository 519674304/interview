docker run -d -p 3306:3306 -v ~/soft/mysql/log:/var/log/mysql -v ~/soft/mysql/data:/var/lib/mysql -v ~/soft/mysql/conf:/etc/mysql -v ~/soft/mysql/mysql-files:/var/lib/mysql-files -e MYSQL_ROOT_PASSWORD=root --name mysql mysql

docker run -d --name redis -p 6379:6379 --restart unless-stopped -v ~/soft/redis/data:/data -v ~/soft/redis/conf/redis.conf:/etc/redis/redis.conf redis redis-server /etc/redis/redis.conf

docker run --restart=always \
-p 6379:6379 \
--name myredis \
-v ~/soft/redis/redis.conf:/etc/redis/redis.conf \
-v ~/soft/redis/data:/data \
-d redis redis-server /etc/redis/redis.conf
