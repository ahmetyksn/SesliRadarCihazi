#include <Servo.h>

// -------------------- PINLER --------------------
const int SERVO_PIN = 9;
const int TRIG_PIN  = 10;
const int ECHO_PIN  = 11;

// LED Pinleri
const int LED_GREEN  = 6;    // Engel yok
const int LED_RED    = 7;    // 10 - 1 cm
const int LED_WHITE  = 8;    // 40 - 31 cm
const int LED_YELLOW = 12;   // 30 - 21 cm
const int LED_BLUE   = 13;   // 20 - 11 cm

const int BUZZER_PIN = 3;

// -------------------- AYARLAR --------------------
const int DETECT_CM = 40;

// -------------------- DEĞİŞKENLER --------------------
Servo radarServo;
int angle = 15;
int dir = 1;
long duration, distance;

void setup() {
  Serial.begin(9600);

  pinMode(TRIG_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);

  pinMode(LED_GREEN, OUTPUT);
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_WHITE, OUTPUT);
  pinMode(LED_YELLOW, OUTPUT);
  pinMode(LED_BLUE, OUTPUT);

  pinMode(BUZZER_PIN, OUTPUT);

  radarServo.attach(SERVO_PIN);

  // Başlangıç: Engel yok
  digitalWrite(LED_GREEN, HIGH);
}

void loop() {

  // 1. SERVO HAREKETİ
  radarServo.write(angle);
  delay(30);

  // 2. MESAFE ÖLÇÜMÜ
  digitalWrite(TRIG_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIG_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIG_PIN, LOW);

  duration = pulseIn(ECHO_PIN, HIGH, 30000UL);

  if (duration == 0) {
    distance = 400;
  } else {
    distance = duration * 0.034 / 2;
  }

  // 3. TÜM LEDLERİ SIFIRLA
  digitalWrite(LED_GREEN, LOW);
  digitalWrite(LED_WHITE, LOW);
  digitalWrite(LED_YELLOW, LOW);
  digitalWrite(LED_BLUE, LOW);
  digitalWrite(LED_RED, LOW);

  // ⚠️ BUZZER BURADA SUSTURULMAZ

  // 4. MESAFEYE GÖRE LED + BUZZER
  if (distance >= 31 && distance <= 40) {
    digitalWrite(LED_WHITE, HIGH);
    noTone(BUZZER_PIN);
  }
  else if (distance >= 21 && distance <= 30) {
    digitalWrite(LED_YELLOW, HIGH);
    noTone(BUZZER_PIN);
  }
  else if (distance >= 11 && distance <= 20) {
    digitalWrite(LED_BLUE, HIGH);
    tone(BUZZER_PIN, 1000);   // 🔵 MAVİ → BUZZER VAR
  }
  else if (distance >= 1 && distance <= 10) {
    digitalWrite(LED_RED, HIGH);
    tone(BUZZER_PIN, 1200);   // 🔴 KIRMIZI → BUZZER VAR
  }
  else {
    digitalWrite(LED_GREEN, HIGH);
    noTone(BUZZER_PIN);
  }

  // 5. PROCESSING'E VERİ GÖNDER
  Serial.print(angle);
  Serial.print(",");
  Serial.print(distance);
  Serial.println(".");

  // 6. SERVO YÖN KONTROLÜ
  angle += dir;
  if (angle >= 165) dir = -1;
  if (angle <= 15)  dir = 1;
}
