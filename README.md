# savi_ros_bdi

Welcome to the savi_ros_bdi project! This project provides a linkage between agents developed for Belief-Desires-Intentions (BDI) in AgentSpeak (using Jason available at http://jason.sourceforge.net/) a connection to robotic systems using the Robot Operating System (ROS, available at https://www.ros.org/). This package uses the rosJava (http://wiki.ros.org/rosjava) package as a means of connecting the Jason BDI agent to ROS.

## Overview of the project

## Configuration and installation

This project uses ROS Java. It is recommended that you familiarize yourself with the documentation available at http://wiki.ros.org/rosjava/Tutorials/kinetic and on their github repository at https://github.com/rosjava.

Please note, we had difficulties installing and configuring rosjava using the instructions provided in the tutorials. As a workaround to those challenges, we installed rosjava directly to the directory of your choice from sources using the following proceedure:
```
$ git clone https://github.com/rosjava/rosjava_core.git
$ cd rosjava_core
$ ./gradlew install
$ source devel/setup.bash
```
From here, we then setup a catkin workspace using the following proceedure.
```
$ mkdir SAVI_ROS
$ cd SAVI_ROS
$ mkdir rosjavaWorkspace
$ cd rosjavaWorkspace/
$ mkdir src
```
With the worksapce created, we can then move on to adding the savi_ros_bdi package.
```
$ cd src
$ catkin_create_rosjava_pkg savi_ros_java
$ cd savi_ros_java
$ catkin_create_rosjava_project savi_ros_bdi
$ rm -d -r savi_ros_bdi
$ git clone https://github.com/NMAI-lab/savi_ros_bdi.git
$ cd ~/SAVI_ROS/rosjavaWorkspace
$ catkin_make
$ source devel/setup.bash
```
Note that we created a package called savi_ros_bdi using ```catkin_create_rosjava_project``` and then immediately deleted it using ```rm -d -r savi_ros_bdi```. This was followed by cloning the source code from this github repository. This step is important as the ```catkin_create_rosjava_project``` adds the savi_ros_bdi project to the build process that is used by ```catkin_make```. Without doing this, you would have to manually add the package to the build process, otherwise catkin_make will never build the package.

## Running
To run the savi_ros_bdi process perform the following proceedure.

First, in a seperate terminal, run roscore:
```
$ source devel/setup.bash
$ roscore
```
In a seperate terminal window, run the savi_ros_java.savi_ros_bdi.SAVI_Main process:

```
$ source devel/setup.bash
$ cd ~/SAVI_ROS/rosjavaWorkspace/src/savi_ros_java/savi_ros_bdi/build/install/savi_ros_bdi/bin
- ./savi_ros_bdi savi_ros_java.savi_ros_bdi.SAVI_Main
```
The process will subscribe to the perceptions topic and publish to the actions topic.

## Example useage
An example of how to use this package is available at https://github.com/NMAI-lab/savi_ros_demo. At that page you will find a sample BDI program written for Jason using AgentSpeak as well as a Python script which generates example perceptions for an agent to perceive. Another Python script listens for actions that the agent wants to execute.
