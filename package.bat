call mvn clean
echo "清理完毕"
call mvn package -Dmaven.test.skip
echo "打包完毕"
scp target/newframe-1.0.0.jar root@172.16.102.51:/root/rent
echo "上传完毕"
