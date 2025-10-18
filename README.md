# JavaFX Web Browser 🌐

A desktop web browser built with JavaFX, featuring tab management, bookmarks, history tracking, and URL filtering.

![Java](https://img.shields.io/badge/Java-17+-orange?style=flat&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-25-blue?style=flat)
![ControlsFX](https://img.shields.io/badge/ControlsFX-11.2.2-green?style=flat)

## ✨ Features

- 🗂️ Multi-tab browsing support
- 🔖 Bookmark management with autocomplete
- 📜 Browsing history tracking
- 🔍 In-page text search
- 🚫 URL filtering and blocking
- 🔒 HTTPS security verification
- 🛡️ Pop-up blocker
- 🔎 Multiple search engines (Google, Bing, DuckDuckGo, Yandex, Yahoo)
- 🖨️ Page printing
- 📸 Screenshot capture
- 🔍 Zoom in/out
- 🎨 Modern, responsive UI

## 🛠️ Technologies

- Java 17+
- JavaFX 25
- ControlsFX 11.2.2
- WebView & WebEngine
- MVC Architecture
- File-based persistence

## 📸 Screenshots

### Main Browser Window

<img width="1917" height="1027" alt="Ekran görüntüsü 2025-10-19 012440" src="https://github.com/user-attachments/assets/9eb736ea-0350-4ce4-99e6-b79950c3ca9b" />


### URL Filter Manager

<img width="1916" height="1029" alt="Ekran görüntüsü 2025-10-19 012509" src="https://github.com/user-attachments/assets/dd71162e-42cb-4459-85da-f16385986a64" />


### Browsing History

<img width="1914" height="1029" alt="Ekran görüntüsü 2025-10-19 012454" src="https://github.com/user-attachments/assets/d1536e82-0232-44fd-880d-2588a275bb91" />


## 💻 Quick Setup

1. **Clone repository**
```bash
git clone https://github.com/yourusername/javafx-browser.git
```

2. **Install JavaFX SDK**
   - Download JavaFX SDK 25 from [openjfx.io](https://openjfx.io/)
   - Extract to a location (e.g., `javafx-sdk-25/`)

3. **Install ControlsFX**
   - Download ControlsFX 11.2.2 JAR from [controlsfx.org](https://controlsfx.org/)

4. **Configure project paths**
   - Update `.idea/libraries/lib.xml` with your JavaFX SDK path
   - Update `Browser.iml` with ControlsFX JAR path

5. **Run** `src/main/Main.java`

## 📁 Project Structure

```
Browser/
├── src/
│   ├── controller/            # UI Controllers
│   │   ├── BrowserController.java
│   │   ├── TabContentController.java
│   │   ├── HistoryTabController.java
│   │   └── UrlFilterTabController.java
│   ├── utils/                 # Helper Classes
│   │   ├── WebUtil.java
│   │   ├── WebAlerts.java
│   │   └── WebTab.java
│   ├── view/                  # FXML & CSS
│   │   ├── css/
│   │   │   ├── browser.css
│   │   │   ├── urlfilter.css
│   │   │   └── alert.css
│   │   ├── photos/            # Icons & Images
│   │   ├── Browser.fxml
│   │   ├── TabContent.fxml
│   │   ├── HistoryTab.fxml
│   │   └── URLFilter.fxml
│   ├── main/
│   │   └── Main.java
│   ├── bookmarks/
│   │   └── bookmark.txt
│   ├── WebHistory/
│   │   └── history.txt
│   └── BlockedUrls/
│       └── blocked.txt
└── screenshots/               # Auto-generated screenshots
```

## ⚠️ Known Limitations

**Architectural Issues (Learning Project Only):**
- Heavy use of static state across controllers
- Code duplication between BrowserController and TabContentController
- Tab state not properly isolated
- Global variables for bookmarks and blocked URLs

**Code Quality:**
- No unit tests
- No proper error handling
- Manual resource management
- History file accumulates duplicates

**⚠️ NOT for production use**

## 📝 Note

This is my **second software project**, built for learning purposes. Feedback and suggestions are welcome!

---

⭐ If you found this helpful, please star the repo!
