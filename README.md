# RobotArmControl
Distributed automation systems using NodeMCU ESP8266


Specification:
--------------------------------------------------
Equipment:
1. Android app
2. NodeMCU ESP8266 for Access Point
3. NodeMCU ESP8266 for Control Robot Arm

Functionality:
In this project, an android application was used for WiFi control of the robotic arm. The application sends HTTP POST requests to the NodeMCU ESP8266,
which works in access point mode, and then forwards the received request to another NodeMCU ESP8266, which executes the received request.
Sending data is done via json, because it enables machine to machine communication.

Communication: Android app ->(first)NodeMCU ESP8266 ->(second)NodeMCU ESP8266
