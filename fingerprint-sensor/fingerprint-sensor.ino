#include <Adafruit_Fingerprint.h>

#define mySerial Serial1

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);
uint8_t id;
volatile int finger_status = -3;

void setup() {
  Serial.begin(9600);
  finger.begin(57600);
  
  if (finger.verifyPassword()) {
    Serial.println("Sensor encontrado");
  } else {
    Serial.println("No se encuentra el sensor");
    while (1);
  }

}

void loop() {
  char selected = Serial.read();
  if(selected == 'e') {
      enroll();
  }else if (selected == 'c') {
    check();
  }

}

void enroll() {
  id = readnumber();
  if (id == 0) {
     return;
  }
  getFingerprintEnroll();
}

void check() {
  do {
    Serial.println("...");
    finger_status = getFingerprintIDez();
    if (finger_status!=-1 and finger_status!=-2){
      Serial.println("Match");
      finger_status = -3;
    } else{
      if (finger_status==-2){
        for (int ii=0;ii<5;ii++){
          Serial.println("Not Match");
        }
      }
    }
    delay(50);
  } while(finger_status != -3);
}

uint8_t readnumber(void) {
  uint8_t num = 0;
  
  while (num == 0) {
    while (! Serial.available());
    num = Serial.parseInt();
  }
  return num;
}

int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  if (p!=2){
    //Serial.println(p);
  }
  if (p != FINGERPRINT_OK)  return -1;
  
  p = finger.image2Tz();
  if (p!=2){
    //Serial.println(p);
  }
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -2;
  
  return finger.fingerID;
}

uint8_t getFingerprintEnroll() {

  int p = -1;
  Serial.println("Pon un dedo en el lector");
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Imagen tomada");
      break;
    case FINGERPRINT_NOFINGER:
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Error de comunicacion");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Huella invalida");
      break;
    default:
      Serial.println("Error desconocido");
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(1);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Imagen convertida");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Imagen sucia");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Error de comunicacion");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Huella invalida");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("HUella invalida");
      return p;
    default:
      Serial.println("Error desconocido");
      return p;
  }
  
  Serial.println("Quita el dedo");
  delay(2000);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
  }
  p = -1;
  Serial.println("Vuelve a poner el mismo dedo");
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Imagen tomada");
      break;
    case FINGERPRINT_NOFINGER:
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Error de comunicacion");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Error en la imagen");
      break;
    default:
      Serial.println("Error desconocido");
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Imagen convertida");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Imangen sucia");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Error de comunicacion");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("No se puede convertir la huella");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Huella invalida");
      return p;
    default:
      Serial.println("Error desconocido");
      return p;
  }
  
  // OK converted!

  p = finger.createModel();
  if (p == FINGERPRINT_OK) {
    Serial.println("Huella matched!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Error de comunicacion");
    return p;
  } else if (p == FINGERPRINT_ENROLLMISMATCH) {
    Serial.println("Huella sin coincidencia");
    return p;
  } else {
    Serial.println("Error desconocido");
    return p;
  }   
  
  p = finger.storeModel(id);
  if (p == FINGERPRINT_OK) {
    Serial.println("Stored!");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Error de comunicacion");
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Error escribiendo en la localizacion");
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error escribiendo en la memoria");
    return p;
  } else {
    Serial.println("Error desconocido");
    return p;
  }   
}
