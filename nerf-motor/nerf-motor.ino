
int rotor = 13;
int fire = 12;
void setup() {
  Serial.begin(9600);
  pinMode(rotor , OUTPUT);
  pinMode(fire , OUTPUT);

}

void loop() {
  char input = Serial.read();
  if (input == 's') {
    digitalWrite(rotor , HIGH);
    delay(2000);
    digitalWrite(fire, HIGH);
    delay(1000);
    digitalWrite(rotor , LOW);
    digitalWrite(fire , LOW);
  }

}
