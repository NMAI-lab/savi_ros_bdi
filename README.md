# savi_ros_bdi

Welcome to the savi_ros_bdi project! This project provides a linkage between agents developed for Belief-Desires-Intentions (BDI) in AgentSpeak (using Jason available at http://jason.sourceforge.net/) a connection to robotic systems using the Robot Operating System (ROS, available at https://www.ros.org/). This package uses the rosJava (http://wiki.ros.org/rosjava) package as a means of connecting the Jason BDI agent to ROS.

## Overview of the project
As mentioned above, this project is aimed at bridging the gap between BDI agents implemented using AgentSpeak with Jason and robots that use ROS. It is recommended that you familiarize yourself with both Jason and ROS. There is also a Jason book which is very helpful for getting started with BDI. The details are on the Jason website.

BDI agents implemented with Jason run in a polling loop, where the agent perceives the environment and then reasons about what it has perceived as well as it's own knowledge, abilities, and objectives before selecting an action to execute. In this project, we have built a ros package for connecting a Jason agent to ros. The agent subscibes to the ```perceptions``` topic and publishes to the ```actions``` topic. The messages are of the form of logical predicates, as is expected by AgnetSpeak. For example, a perception for position in a cartesian coordinate system could be ```position(X,Y,Z)``` where X, Y, and Z are variables representing the position of the robot provided by some sensor connected to it. Actions are similarly formated.

This project builds on previous work done on the SAVI project, available at https://github.com/NMAI-lab/SAVI. The original SAVI project focused on developing a linkage between Jason and a custom simulated environment. In this case, the focus is on making Jason available to any hardware or simulations that are compatible with ros. This implementation is intended to be generic, without any assumptions about the type of environment being used. There are other projects that have sought to link ros and bdi, some examples include (in no particular order): 

- https://github.com/jason-lang/jason-ros
- https://github.com/lsa-pucrs/jason-ros-releases
- https://github.com/smart-pucrs/JROS

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
```
Note that we created a package called savi_ros_bdi using ```catkin_create_rosjava_project``` and then immediately deleted it using ```rm -d -r savi_ros_bdi```. This was followed by cloning the source code from this github repository. This step is important as the ```catkin_create_rosjava_project``` adds the savi_ros_bdi project to the build process that is used by ```catkin_make```. Without doing this, you would have to manually add the package to the build process, otherwise catkin_make will never build the package.

This package uses a configuration file for setting some key parameters, notably the path to the file containing the AgentSpeak source code for the agent. This configuration file is found at ```savi_ros_bdi/src/main/resources/settings.cfg```. This must be set prior building the agent with catkin_make and running it, as the gradle process invoked by catkin_make copies this file as part of the build process.

Lastly, we can build the project.

```
$ cd ~/SAVI_ROS/rosjavaWorkspace
$ catkin_make
$ source devel/setup.bash
```
## Pre Built Project Configuration
Alternatively to building your own project configuration from scratch, which we have found to be somewhat tricky, we have included a prebuilt project directory that can be used for setting up your project worksapce. This configuration is available as a zip file here: https://drive.google.com/file/d/1e260VPV3J92DGzMtxe6uQ5jVbhCNl1YF/view?usp=sharing

There are two empty directories in this configuration. One for savi_ros_demo and the other for savi_ros_bdi. Those directories are the intended location for the repositories.

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
$ rosrun savi_ros_java savi_ros_bdi savi_ros_java.savi_ros_bdi.SAVI_Main
```
In the event that the rosrun method does not work, the project can be launched maunally using the following:
```
$ source devel/setup.bash
$ cd ~/SAVI_ROS/rosjavaWorkspace/src/savi_ros_java/savi_ros_bdi/build/install/savi_ros_bdi/bin
$ ./savi_ros_bdi savi_ros_java.savi_ros_bdi.SAVI_Main
```
The process will subscribe to the perceptions and inbox topics and publish to the actions and outbox topics.

## Example usage
An example of how to use this package is available at https://github.com/NMAI-lab/savi_ros_demo. At that page you will find a sample BDI program written for Jason using AgentSpeak as well as a Python script which generates example perceptions for an agent to perceive. Another Python script listens for actions that the agent wants to execute.

## Compatibility
This application has been successfully run using ROS Kinetic and Melodic. It has also been demonstrated using Raspian on both the Raspberry Pi 3 and 4, as well as on desktop computers running Ubuntu and Windows.
