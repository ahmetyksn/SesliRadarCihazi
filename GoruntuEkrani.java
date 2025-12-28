import processing.serial.*;
import java.awt.event.KeyEvent; 
import java.io.IOException;

Serial myPort; 
PFont myFont; 

// Değişkenler
String data = "";
int iAngle = 0;
int iDistance = 0;
int index1 = 0;

// --- AYARLAR ---
final int MAX_DISTANCE = 40; // Radar menzili (cm)
final String PORT_NAME = "COM11"; // Portunu kontrol et

void setup() {
  // Tam ekran modu
  fullScreen(); 
  
  smooth(); // Pürüzsüz çizgiler
  
  // Yazı tipi (Varsa Impact yoksa Arial kullanır)
  myFont = createFont("Arial", 20);
  textFont(myFont);
  
  try {
    myPort = new Serial(this, PORT_NAME, 9600);
    myPort.bufferUntil('.'); 
  } catch (Exception e) {
    println("!!! BAĞLANTI HATASI !!! Port ismini kontrol et.");
  }
}

void draw() {
  // 1. İZ BIRAKMA EFEKTİ
  fill(0, 8); 
  noStroke();
  rect(0, 0, width, height);
  
  // --- RADAR ÇİZİMİ ---
  pushMatrix();
  
  // Radarın merkezini ekranın altından 120 piksel YUKARI taşıyoruz.
  // Böylece alttaki yazılar için yer açılıyor.
  float radarOriginY = height - 120;
  translate(width/2, radarOriginY); 

  drawRadarGrid(radarOriginY); 
  drawSweepLine(radarOriginY);
  drawObject(radarOriginY);
  
  popMatrix();
  
  // --- ARAYÜZ (UI) ÇİZİMİ ---
  // UI'ı popMatrix'ten sonra çiziyoruz ki radarın dönüşünden etkilenmesin.
  drawUI();
}

void serialEvent(Serial myPort) { 
  try {
    data = myPort.readStringUntil('.');
    if (data != null) {
      data = data.trim();
      data = data.substring(0, data.length() - 1);
      
      index1 = data.indexOf(","); 
      if (index1 > 0) {
        String angleStr = data.substring(0, index1); 
        String distanceStr = data.substring(index1 + 1, data.length());
        
        iAngle = Integer.parseInt(angleStr.trim());
        iDistance = Integer.parseInt(distanceStr.trim());
      }
    }
  } catch (Exception e) {}
}

// --- GÖRSEL TASARIM FONKSİYONLARI ---

void drawRadarGrid(float originY) {
  noFill();
  stroke(0, 255, 0, 80); // Neon Yeşil
  strokeWeight(2);
  
  // Halkalar ekran yüksekliğine sığacak şekilde ayarlanır
  float maxRadius = originY - 50; 
  
  for(int i=1; i<=4; i++) {
    stroke(0, 255, 0, 50); 
    strokeWeight(1);
    float r = (maxRadius/4) * i; 
    arc(0, 0, r*2, r*2, PI, TWO_PI);
    
    // Mesafe yazısı
    fill(0, 255, 0, 150);
    textSize(14);
    text(i*10 + "cm", (r) + 10, -5); 
    noFill();
  }
  
  // Açı Çizgileri
  stroke(0, 255, 0, 30); 
  for(int a=30; a<=150; a+=30) {
    float rad = radians(a);
    line(0, 0, (-maxRadius)*cos(rad), (-maxRadius)*sin(rad));
  }
  
  // Taban Çizgisi
  stroke(0, 255, 0);
  strokeWeight(3);
  line(-width/2, 0, width/2, 0);
}

void drawSweepLine(float originY) {
  float maxRadius = originY - 50;
  
  strokeWeight(5); 
  stroke(0, 255, 0); 
  
  line(0, 0, maxRadius*cos(radians(iAngle)), -maxRadius*sin(radians(iAngle))); 
  
  // Uçtaki parlak nokta
  fill(200, 255, 200); 
  noStroke();
  ellipse(maxRadius*cos(radians(iAngle)), -maxRadius*sin(radians(iAngle)), 15, 15);
}

void drawObject(float originY) {
  float maxRadius = originY - 50;
  float pixDistance = iDistance * (maxRadius / MAX_DISTANCE); 
  
  if(iDistance < MAX_DISTANCE && iDistance > 0) {
    float x = pixDistance*cos(radians(iAngle));
    float y = -pixDistance*sin(radians(iAngle));
    
    translate(x, y);
    
    // Kırmızı Engel
    noStroke();
    fill(255, 0, 0); 
    ellipse(0, 0, 30, 30); 
    
    // Şok dalgası
    noFill();
    stroke(255, 0, 0, 150);
    strokeWeight(3);
    ellipse(0, 0, 60, 60); 
    
    stroke(255, 0, 0, 150);
    line(0,0, -x, -y); // Merkeze çizgi
  }
}

void drawUI() { 
  // Burası artık radarın koordinat sisteminden bağımsız.
  // Direkt ekranın piksel koordinatlarına göre çiziyoruz.
  
  // Alt Panel Çizgisi
  stroke(0, 255, 0);
  strokeWeight(4);
  line(0, height - 110, width, height - 110);
  
  // Arka plan paneli (yazıların okunaklı olması için siyah şerit)
  noStroke();
  fill(0);
  rect(0, height - 108, width, 108);
  
  // --- SOL TARAF (DURUM VE MESAFE) ---
  textAlign(LEFT);
  if(iDistance < MAX_DISTANCE && iDistance > 0) {
    fill(255, 50, 50); // Kırmızı
    textSize(20);
    text("DURUM: HEDEF TESPİT EDİLDİ!", 50, height - 70);
    
    textSize(60);
    text(iDistance + " cm", 50, height - 20);
  } else {
    fill(0, 255, 0); // Yeşil
    textSize(20);
    text("DURUM: TARAMA YAPILIYOR", 50, height - 70);
    
    textSize(60);
    fill(0, 255, 0, 100); // Hafif silik
    text("TEMİZ", 50, height - 20);
  }
  
  // --- SAĞ TARAF (AÇI) ---
  textAlign(RIGHT);
  fill(0, 255, 0);
  textSize(20);
  text("RADAR AÇISI", width - 50, height - 70);
  
  textSize(60);
  text(iAngle + "°", width - 50, height - 20);
}
