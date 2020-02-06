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

package savi_ros_java.savi_ros_bdi;

import org.apache.commons.logging.Log;
import org.ros.message.MessageListener;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.NodeMain;
import org.ros.node.topic.Subscriber;

/**
 * A simple {@link Subscriber} {@link NodeMain}.
 */
public class SAVI_Main extends AbstractNodeMain {

    private SaviArchitecture agent;

    /**
     * Provide name of this node when requested.
     * @return
     */
    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("rosjava/savi_ros_bdi");
    }


    /**
     * Start method for the node (can think of this as the 'main()' method.
     * @param connectedNode
     */
    @Override
    public void onStart(ConnectedNode connectedNode) {

        // Initialize the agent
        this.agent = new SaviArchitecture(connectedNode);
        this.agent.startAgents();
    }
}
