#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ArduinoJson.h>
#include <ESP8266WebServer.h>
#include<Servo.h>

const char* ssid = "NodeMCU";  
const char* password = "12345678"; 


Servo servo1, servo2,servo3,servo4,servo5;

ESP8266WebServer server(80); // port 80 for web server


void setup() {
  servo1.attach(D1, 544, 2400);
  servo2.attach(D3, 544, 2400);
  servo3.attach(D5, 544, 2400);
  servo4.attach(D7, 544, 2400);
  servo5.attach(D8, 544, 2400);

  servo1.write(544);
  servo2.write(544);
  servo3.write(544);
  servo4.write(544);
  servo5.write(544);
  
  Serial.begin(9600);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.println("Connecting to WiFi..");
  }

  Serial.println("Connected to the WiFi network");

  // Print the IP address
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());  

server.on("/controlRobot", HTTP_POST, []() {    //{IP: http:://192.168.4.2/control }
String requestBody = server.arg("plain");  
StaticJsonDocument<200> doc;
DeserializationError error = deserializeJson(doc, requestBody);

if(error) {
Serial.println(F("deserializeJson() failed"));
return;  
}

String direction = doc["direction"];  
   if(direction == "open") {motor5Control(544);}
else if(direction == "close") {motor5Control(1008);}
else if(direction == "turnOn") {motor4Control(1472);}
else if(direction == "turnOff") {motor4Control(544);}
else if(direction == "motor3Send0") {motor3Control(544);}
else if(direction == "motor3Send45") {motor3Control(1008);}
else if(direction == "motor3Send90") {motor3Control(1472);}
else if(direction == "motor2Send0") {motor2Control(544);}
else if(direction == "motor2Send45") {motor2Control(1008);}
else if(direction == "motor2Send90") {motor2Control(1472);}
else if(direction == "motor1Send0") {motor1Control(544);}
else if(direction == "motor1Send45") {motor1Control(1008);}
else if(direction == "motor1Send90") {motor1Control(1472);}
else if(direction == "motor1Send135") {motor1Control(1936);}
else if(direction == "motor1Send180") {motor1Control(2400);}

server.send(200, "text/plain", "Command received");
});

server.begin();
Serial.println("Server started");
}

void loop() {
    server.handleClient();

}

void motor5Control(int angle) {
    servo5.write(angle);
}

void motor4Control(int angle){
  servo4.write(angle);
}
void motor3Control(int angle) {
  servo3.write(angle);
}
void motor2Control(int angle) {
  servo2.write(angle);
}
void motor1Control(int angle) {
  servo1.write(angle);
}

