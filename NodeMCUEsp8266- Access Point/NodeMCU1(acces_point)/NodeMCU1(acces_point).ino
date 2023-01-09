//access point mode and server

#include <ESP8266WiFi.h>
#include <WiFiClient.h> 
#include <ArduinoJson.h>
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "NodeMCU";  //Name of access point wifi
const char* password = "12345678"; //pasword for ac wifi


ESP8266WebServer server(80); // port 80 for web server



void setup() {

Serial.begin(9600);

WiFi.mode(WIFI_AP); //set ac wifi
WiFi.softAP(ssid, password);

Serial.println();

IPAddress myIP = WiFi.softAPIP();  //IP address 
Serial.print("IP Address for Access Point Connection: ");
Serial.print(myIP);

//Start Web Server
server.on("/controlRobot", HTTP_POST, []() { 
  
String requestBody = server.arg("plain");  
StaticJsonDocument<200> doc;
DeserializationError error = deserializeJson(doc, requestBody);

if(error) {
Serial.println(F("deserializeJson() failed"));
return;  
}

String direction = doc["direction"];  
  if(direction == "open") {send_post_req("open");}
else if(direction == "close") {send_post_req("close");}
else if(direction == "turnOn") {send_post_req("turnOn");}
else if(direction == "turnOff") {send_post_req("turnOff");}
else if(direction == "motor3Send0") {send_post_req("motor3Send0");}
else if(direction == "motor3Send45") {send_post_req("motor3Send45");}
else if(direction == "motor3Send90") {send_post_req("motor3Send90");}
else if(direction == "motor2Send0") {send_post_req("motor2Send0");}
else if(direction == "motor2Send45") {send_post_req("motor2Send45");}
else if(direction == "motor2Send90") {send_post_req("motor2Send90");}
else if(direction == "motor1Send0") {send_post_req("motor1Send0");}
else if(direction == "motor1Send45") {send_post_req("motor1Send45");}
else if(direction == "motor1Send90") {send_post_req("motor1Send90");}
else if(direction == "motor1Send135") {send_post_req("motor1Send135");}
else if(direction == "motor1Send180") {send_post_req("motor1Send180");}

server.send(200, "text/plain", "Command received");
});

server.begin();
Serial.println("Server started");
}

void loop() {
  server.handleClient();
}

void send_post_req(String command) { 
  WiFiClient client;
  HTTPClient http;

  String url = "http://192.168.4.2/controlRobot";
  http.begin(client,url);
  http.addHeader("Content-Type", "application/json");
  String data = "{\"direction\": \"" + command + "\"}";
  int httpCode = http.POST(data);

  if (httpCode > 0) {
    Serial.println(httpCode);
  } else {
    Serial.println("Error on HTTP POST request");
  }
  http.end();
}




