#-n str，字符串不为null，长度大于零
#也就是说你输入的命令的第二参数不为空
if [ -n "$1" ]; then
  #${1}  #输入的第2个参数
  #${2}  #输入的第3个参数
  #${0}  #输入的第1个参数

  #0,1,2分别代表你的命令和后面的第一个，第二个参数
  #比如你的命令叫test.sh，然后需要填入参数-f test进行运行

  #完整命令
  #test.sh -f test
  #那{0}就是test.sh
  #{1}就是-f
  #{2}就是test

  #V $(date +%Y%m%d%H%I%S%N)__${1} .sql
  #V 普通字符
  #${1} =输入命令的第二个参数
  #date 普通字符
  #%Y%m%d%H%I%S%N 当前显示的年日月时分秒毫秒
  newSqlFile=../src/main/resources/db/migration/V$(date +%Y%m%d%H%I%S%N)__${1}.sql

  #  touch命令用于创建新的空文件
  touch $newSqlFile
  #echo：显示
  echo "Successful! A new migration script generated at: " $newSqlFile
else
  echo "Fail! 请输入迁移脚本名称"
fi
