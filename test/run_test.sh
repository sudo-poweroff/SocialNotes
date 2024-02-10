#!/bin/bash

# To execute tests specify the following configurations:
# - Java Home;
# - Project Directory;
# - Test class in package.ClassName format. Standard folder for executing tests is "test". Example: TEST_CLASS="chat.ChatModelDSTest"

JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk-19.0.1.jdk/Contents/Home"
PROJECT_DIR="/Users/luigiallocca/IdeaProjects/SocialNotes"
TEST_CLASS="profilo.DepartmentModelDSTest"


BUILD_DIR="$PROJECT_DIR/build/classes"
LIB_DIR="$PROJECT_DIR/WebContent/WEB-INF/lib"


"$JAVA_HOME/bin/java" \
-ea \
-Dfile.encoding=UTF-8 \
-Dsun.stdout.encoding=UTF-8 \
-Dsun.stderr.encoding=UTF-8 \
-classpath \
"$BUILD_DIR:$LIB_DIR/*" \
org.junit.runner.JUnitCore \
$TEST_CLASS