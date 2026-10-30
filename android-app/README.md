# Obbattu Android app

A simple Android app for the live Obbattu sweets shop at **https://obbattu.in/**. It opens the mobile website in an Android WebView and uses the Obbattu logo as its launcher icon. The website remains the source of truth for products, cart, accounts, checkout, coupons, and order history.

## Build an installable APK without Android Studio

1. Create a GitHub repository and upload the contents of this folder (the `app`, `build.gradle`, `settings.gradle`, and `.github` entries should be at the repository root).
2. Push the files to GitHub, or use **Actions → Build Obbattu Android app → Run workflow**.
3. Open the completed workflow run and download the `obbattu-android-debug-apk` artifact.
4. Extract it and install `app-debug.apk` on an Android phone. Android may ask you to allow installation from that source.

The workflow creates a **debug APK for testing**. It is not a Play Store release. For Play Store distribution, create a signed release Android App Bundle (AAB), keep the signing key private, and complete the Google Play Console app listing, testing, and policy forms.

## Build in Android Studio

Open this folder as a project in Android Studio. Let it install the Android SDK platform 35 and Gradle 8.9 if prompted, then choose **Build → Build Bundle(s) / APK(s) → Build APK(s)**. The test APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

## Included behavior

- Keeps shopping and account pages on `obbattu.in` or its subdomains inside the app.
- Opens external destinations such as WhatsApp and payment apps through Android's normal link handling.
- Supports JavaScript, cookies, saved web storage, Android back navigation, and payment-proof file selection.
- Requires HTTPS and blocks cleartext network traffic.

Before release, verify login, order checkout, payment-proof uploads, WhatsApp links, and Android back navigation on real devices. This wrapper does not add native push notifications or offline shopping.
