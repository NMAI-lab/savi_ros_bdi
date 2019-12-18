package com.github.rosjava.savi_ros_java.savi_ros_bdi;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Publisher;

public class ActionTalker {

    /**
     * Default constructor - don't use
     */
    private ActionTalker() {}

    /**
     * Constructor
     * @param connectedNode Connection to the ROS node for messages
     */
    public ActionTalker(final ConnectedNode connectedNode) {

        System.out.println("Built the action talker object.");

    }

}
