import paho.mqtt.client as mqtt
client = mqtt.Client()
client.connect("backend.ipaas.io", 10383, 60)
client.publish("chat", "Jakob Thun: Hej")