# savi_ros_bdi

Sister repository is at https://github.com/NMAI-lab/savi_ros_py

Uses ROS Java. See http://wiki.ros.org/rosjava/Tutorials/kinetic

Java setup:
- mkdir SAVI_ROS
- cd SAVI_ROS
- mkdir rosjavaWorkspace
- source ~/rosjava/devel/setup.bash
- cd rosjavaWorkspace/
- mkdir src
- cd src
- catkin_create_rosjava_pkg savi_ros_java
- cd savi_ros_java
- catkin_create_rosjava_project savi_ros_bdi
-> Contents of savi_ros_bdi folder should be a github repo

Python setup (for the sister repository):
- cd ~/SAVI_ROS/rosjavaWorkspace/src
- catkin_create_pkg savi_ros_py std_msgs rospy roscpp
- cd savi_ros_py 
- mkdir scripts -> this will be the repository on github
- catkin_make -> from the home directory

Running:
- source devel/setup.bash (in each terminal window)
- roscore (in one terminal window)
- catkin_make (from root of workspace)

- ~/SAVI_ROS/rosjavaWorkspace/src/savi_ros_java/savi_ros_bdi/build/install/savi_ros_bdi/bin 
- ./savi_ros_bdi com.github.rosjava.savi_ros_java.savi_ros_bdi.Talker
- ./savi_ros_bdi com.github.rosjava.savi_ros_java.savi_ros_bdi.SAVI_Main


- rosrun savi_ros_py talker.py  
- rosrun savi_ros_py listener.py 
