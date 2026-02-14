# Focus - Distraction-Free Study Helper

Focus is a **modern Android application** designed to help you minimize distractions and boost productivity during study or work sessions. By combining a focus timer with smart app blocking, Focus ensures you stay on track.

## 🚀 Key Features

*   **⏱️ Focus Session Engine**
    *   Set custom focus duration (5 minutes to 2 hours) using an intuitive circular dial.
    *   Visual countdown timer to keep you aware of remaining time.
    *   **Strict enforcement**: Sessions must be explicitly stopped or completed.

*   **🛡️ Smart App Blocking**
    *   Automatically blocks distracting apps (**Instagram**, **TikTok**, **YouTube**, **Facebook**, **Reddit**, **Twitter**) while a session is active.
    *   **"Return to Focus" Overlay**: A calming full-screen overlay gently reminds you to return to your work if you try to open a blocked app.
    *   Powered by Android's **Accessibility Service** for robust detection.

*   **📊 Analytics Dashboard**
    *   Track your progress with local data storage (**Room Database**).
    *   View **Total Focus Minutes** and **Completed Sessions**.
    *   Stay motivated by seeing your productivity history.

*   **✨ Modern UI/UX**
    *   Built with **Jetpack Compose** for a smooth, responsive interface.
    *   Clean, distraction-free design with **Material 3** theming.
    *   Seamless Onboarding flow to guide you through necessary permissions.

## 🛠️ Tech Stack

*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose
*   **Architecture**: MVVM (Model-View-ViewModel)
*   **Database**: Room (Local Storage)
*   **Navigation**: Navigation Compose
*   **Background Tasks**: AccessibilityService for app monitoring.

## 📱 Permissions

To function correctly, Focus requires the following permissions (requested on first launch):
1.  **Display over other apps**: To show the blocking overlay.
2.  **Usage Access**: To detect when other apps are opened.
3.  **Accessibility Service**: To actively monitor and block blacklisted apps during a session.

## 🏁 Getting Started

1.  Clone the repository.
2.  Open the project in **Android Studio** (Ladybug or newer recommended).
3.  Sync Gradle dependencies.
4.  Run on an Android device or emulator (Android 8.0+ recommended).
5.  Follow the in-app onboarding to grant permissions.


![Screenshot_2026-02-14-19-51-41-476-edit_com study focus](https://github.com/user-attachments/assets/41c0eebc-90ed-49b1-b31e-529628d9982e)
![Screenshot_2026-02-14-19-51-41-476-edit_com study focus](https://github.com/user-attachments/assets/5ae5b3c9-49df-4c1b-b59d-87ca3639e37c)

