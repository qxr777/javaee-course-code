由于本地9090端口被ClashX占用，所以prometheus宿主机端口映射从9090改为9091

grafana设置数据源用docker主机名和内部端口号  http://prometheus:9090

grafana登录admin，密码从admin改为123456
http://localhost:3000

在grafana dashboard页面，需要导入dashboard目录中的json文件
对于每个panel，可能需要Edit，刷新可见到折线图
