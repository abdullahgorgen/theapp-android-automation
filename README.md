# TheApp Android Automation

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9.16-C71A36?logo=apachemaven&logoColor=white)
![Gauge](https://img.shields.io/badge/Gauge-1.6.38-F5C10E)
![Appium](https://img.shields.io/badge/Appium-3.9.0-662D91?logo=appium&logoColor=white)
![UiAutomator2](https://img.shields.io/badge/UiAutomator2-8.7.3-3DDC84?logo=android&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-4.51.0-43B02A?logo=selenium&logoColor=white)
![AssertJ](https://img.shields.io/badge/AssertJ-3.27.7-25A162)
![Android](https://img.shields.io/badge/Android-17%20%7C%20API%2037-3DDC84?logo=android&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-Page%20Object%20Model-blue)

[TheApp](https://github.com/cloudgrey-io/the-app) Android uygulamasının giriş akışını test eden mobil test otomasyonu projesi. Gauge, Appium ve Java ile yazıldı ve Page Object Model kullanıyor.

## Senaryolar

| Kod   | Senaryo                                    | Etiketler                  |
|-------|--------------------------------------------|----------------------------|
| TC001 | Doğru kullanıcı adı ve şifreyle giriş, ardından çıkış | login, smoke, regression   |
| TC002 | Doğru kullanıcı adı, yanlış şifre          | login, negative, regression |
| TC003 | Yanlış kullanıcı adı, doğru şifre          | login, negative, regression |
| TC004 | Yanlış kullanıcı adı, yanlış şifre         | login, negative, regression |

Başarılı girişte "Secret Area" ekranındaki `You are logged in as alice` mesajı doğrulanır. Ardından Logout'a basılır ve giriş ekranına dönüldüğü kontrol edilir. Başarısız girişlerde `Invalid login credentials, please try again` hata mesajı doğrulanır.

## Teknolojiler

| Araç                        | Sürüm       |
|-----------------------------|-------------|
| Java                        | 17          |
| Maven                       | 3.9.16      |
| Gauge                       | 1.6.38      |
| Gauge Java eklentisi        | 1.0.5       |
| Gauge HTML Report eklentisi | 4.4.8       |
| gauge-maven-plugin          | 1.5.0       |
| Appium                      | 3.9.0       |
| UiAutomator2 sürücüsü       | 8.7.3       |
| Appium java-client          | 11.0.0      |
| Selenium                    | 4.51.0      |
| AssertJ                     | 3.27.7      |
| Gson                        | 2.14.0      |
| Android (emülatör)          | 17 (API 37) |
| TheApp                      | 1.12.0      |

## Proje yapısı

```
├── apps/                          # TheApp.apk buraya konur (git'e eklenmez)
├── specs/
│   ├── Concepts/Login/login.cpt   # Tekrar kullanılan adım grupları
│   └── Scenarios/Login/login.spec # Test senaryoları
└── src/test/
    ├── java/com/vrgsl/theapp/
    │   ├── driver/                # Appium oturumunun açılıp kapanması
    │   ├── helper/                # JSON locator dosyalarının okunması
    │   ├── model/                 # Locator kaydı (ElementInfo)
    │   ├── pages/                 # Page Object sınıfları
    │   └── steps/                 # Gauge adım tanımları ve doğrulamalar
    └── resources/element-infos/   # Ekranlara göre ayrılmış locator dosyaları
```

Katmanlar şu sırayla çağrılır: spec → concept → adım sınıfı → sayfa → `BasePage` → `DriverManager`.

- **Sayfalar** yalnızca ekranla etkileşir ve değer döndürür. Doğrulama yapmaz.
- **Doğrulamalar** adım sınıfında, AssertJ ile yapılır.
- **Locator'lar** koda yazılmaz; `element-infos` altındaki JSON dosyalarında anahtar adıyla tutulur. Desteklenen türler: `accessibilityId`, `id`, `uiAutomator`, `xpath`.

```json
{
  "key": "loginButton",
  "type": "accessibilityId",
  "value": "loginBtn"
}
```

## Kurulum

1. JDK 17, Maven, Gauge ve Android SDK'yı kurun.
2. Gauge eklentilerini kurun:
   ```
   gauge install java
   gauge install html-report
   ```
3. Appium'u ve UiAutomator2 sürücüsünü kurun:
   ```
   npm install -g appium
   appium driver install uiautomator2
   ```
4. TheApp **1.12.0** APK'sını [sürümler sayfasından](https://github.com/cloudgrey-io/the-app/releases) indirin ve `apps/TheApp.apk` olarak kaydedin.

## Çalıştırma

1. Emülatörü başlatın. Proje `emulator-5554` cihazını kullanır; `adb devices` ile kontrol edebilirsiniz.
2. Appium'u başlatın. Varsayılan adres `http://127.0.0.1:4723`:
   ```
   appium
   ```
3. Testleri çalıştırın:
   ```
   mvn test
   ```

Etikete göre çalıştırma:

```
mvn test -Dtags=smoke
mvn test -Dtags=negative
```

Rapor `reports/html-report/index.html` dosyasına yazılır.

Cihaz, Appium adresi ve APK yolu `DriverManager.java` içinde tanımlıdır; farklı bir ortamda çalıştırırken bu değerleri değiştirin.

## Bilinmesi gerekenler

- **Her senaryo yeni bir Appium oturumuyla başlar.** Uygulama giriş yapan kullanıcıyı yeniden başlatmalarda hatırladığı için `noReset=false` kullanılır. Bu yüzden uygulamanın verisi her senaryoda silinir.
- **Hata mesajı Android'in kendi diyaloğunda gösterilir.** Selenium'un alert API'si bu diyaloğu görmez, bu yüzden mesaj `android:id/message` id'siyle normal bir element gibi okunur.
- **Eşleşmeyen adımlar build'i kırmaz.** Spec'teki bir satır hiçbir adıma ya da concept'e eşleşmezse Gauge senaryoyu atlar, ama `mvn test` yine de başarılı biter. Spec değiştirdikten sonra şu komutla kontrol edin:
  ```
  mvn gauge:validate
  ```
- **Yanlış yazılmış etiket hata vermez.** `-Dtags` ile var olmayan bir etiket verilirse Gauge "No specifications found" yazar ve build yine başarılı biter.
