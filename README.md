# 🎯 Sesli Radar Cihazı (Arduino + Processing)

Bu proje; **Arduino, Servo motor, Ultrasonik sensör, LED’ler, Buzzer ve Processing** kullanılarak geliştirilmiş
**gerçek zamanlı, görsel ve sesli bir radar sistemidir**.

Radar, çevresini tarayarak engellerin **açısını ve mesafesini** tespit eder,
hem **fiziksel uyarılar (LED + Buzzer)** hem de **bilgisayar ekranında radar arayüzü** ile kullanıcıya bildirir.

---

## 🧠 Proje Özeti

* Servo motor ile **15° – 165°** arasında sürekli tarama
* Ultrasonik sensör ile **1 – 40 cm** arası mesafe ölçümü
* Mesafeye göre:

  * LED renk değişimi
  * Buzzer sesli uyarı
* Processing ile:

  * Tam ekran radar animasyonu
  * Gerçek zamanlı açı & mesafe gösterimi
  * Hedef tespiti görselleştirmesi

---

## 🛠️ Kullanılan Teknolojiler

* Arduino UNO
* HC-SR04 Ultrasonik Sensör
* Servo Motor
* LED’ler (Yeşil, Beyaz, Sarı, Mavi, Kırmızı)
* Buzzer
* Processing (Java tabanlı)
* Serial Communication (9600 baud)

---

## 📁 Proje Dosyaları

| Dosya                | Açıklama                                             |
| -------------------- | ---------------------------------------------------- |
| SesliRadarCihazi.ino | Arduino kodu (Servo, sensör, LED ve buzzer kontrolü) |
| GoruntuEkrani.java   | Processing radar arayüzü ve görselleştirme           |

---

## 🔌 Donanım Bağlantıları

### Arduino Pinleri

| Bileşen         | Pin |
| --------------- | --- |
| Servo Motor     | D9  |
| Ultrasonik TRIG | D10 |
| Ultrasonik ECHO | D11 |
| Yeşil LED       | D6  |
| Kırmızı LED     | D7  |
| Beyaz LED       | D8  |
| Sarı LED        | D12 |
| Mavi LED        | D13 |
| Buzzer          | D3  |

---

## 🚦 Mesafe – LED – Buzzer Mantığı

| Mesafe (cm) | LED     | Renk | Buzzer         |
| ----------- | ------- | ---- | -------------- |
| 31 – 40     | Beyaz   | ⚪    | Kapalı         |
| 21 – 30     | Sarı    | 🟡   | Kapalı         |
| 11 – 20     | Mavi    | 🔵   | Açık (1000 Hz) |
| 1 – 10      | Kırmızı | 🔴   | Açık (1200 Hz) |
| Engel Yok   | Yeşil   | 🟢   | Kapalı         |

---

## 📡 Veri İletişimi

Arduino → Processing seri haberleşme formatı:

`
Açı,Mesafe.
`

Örnek:

`
90,18.
`

---

## 🖥️ Processing Radar Özellikleri

* Tam ekran, yarım daire radar tasarımı
* Neon yeşil tarama çizgisi
* Kırmızı hedef işaretleme
* Alt bilgi paneli:

  * Radar açısı
  * Mesafe
  * Hedef durumu

---

## ▶️ Çalıştırma Adımları

1. Arduino devresini kur
2. **SesliRadarCihazi.ino** dosyasını Arduino’ya yükle
3. **GoruntuEkrani.java** dosyasında port ayarını yap:

`
final String PORT_NAME = "COM11";
`

4. Processing üzerinden çalıştır
5. Radar taramasını izle 🚀

---

## 🎯 Projenin Amacı

Bu proje;

* Sensör kullanımı
* Gerçek zamanlı veri işleme
* Görsel arayüz tasarımı
* Fiziksel ve dijital sistem entegrasyonu

konularını uygulamalı olarak göstermek amacıyla geliştirilmiştir.

---

## 👤 Geliştirici

**Ahmet Yakışan**
📌 Bilişim Sistemleri ve Teknolojileri
📌 Arduino & Gömülü Sistemler

---

## ⭐ Not

Projeyi beğendiysen ⭐ vermeyi unutma!
