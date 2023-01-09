# run.sh
# 切换到jar包目录下
cd /www/wwwroot/freechat.bilinstore.com/springboot
#第二步是根据jar包的名字获取运行的pid，并且将该进程杀死
ps -ef | grep freechat-0.0.1.jar | grep -v grep | awk '{printf $2}' | xargs kill -9
# 停5秒
sleep 5s
# 使环境变量生效
source /etc/profile
# 运行项目
nohup java -jar freechat-0.0.1.jar >/www/wwwroot/freechat.bilinstore.com/springboot/cicd.log 2>&1 &
#输出内容，可不加
echo "启动完成"