#!\bin\bash

SPARK_SUBMIT_SCRIPT=${SPARK_HOME}/bin/spark-submit
ASSEMBLY_JAR=../target/*0.0.1.jar

# Scala
echo "Running Scala programs"

${SPARK_SUBMIT_SCRIPT} \
    --class com.git.huanghaifeng.spark.sql.hive.SQLHiveContext \
    ${ASSEMBLY_JAR} ./files/sql-hive-context.json

echo "Done running all programs :)"
