/*
 * Copyright (C) 2014 pi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.rosjava.savi_ros_java.savi_ros_bdi;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

import jason.asSyntax.*;

public class PerceptionListener implements Runnable {

    // The node for passing messages to
    private ConnectedNode connectedNode;

    private PerceptionListener() { }

    public PerceptionListener(final ConnectedNode connectedNode) {
        // Set the connectedNode parameter
        this.connectedNode = connectedNode;
    }

    public void run() {
        final Log log = connectedNode.getLog();
        Subscriber<std_msgs.String> subscriber = connectedNode.newSubscriber("perceptions", std_msgs.String._TYPE);
        subscriber.addMessageListener(new MessageListener<std_msgs.String>() {
            @Override
            public void onNewMessage(std_msgs.String message) {
                // Interpret the message as a literal
                Literal rxLiteral = Literal.parseLiteral(message.getData());

                // Handle the message
                SyncAgentState agentState = SyncAgentState.getSyncAgentState();
                agentState.setPerceptions(rxLiteral.toString());
            }
        });
    }
}