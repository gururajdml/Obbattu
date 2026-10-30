# Obbattu Android app

Android app for the live Obbattu sweets shop at **https://obbattu.in/**. The app opens the mobile store without a separate app toolbar, keeps Obbattu pages inside the app, and hands external payment/WhatsApp destinations to Android. The website remains the source of truth for products, cart, accounts, checkout, coupons, and orders.

## Build artifacts on GitHub

The GitHub Actions workflow builds an installable debug APK and a release Android App Bundle (AAB) targeting Android 16 / API 36. It keeps the existing website branch untouched when built from the `android-app` branch.

- The debug APK is for device testing only.
- The release AAB is signed only after a private upload keystore is configured. Without the key secrets, the workflow produces an **unsigned AAB** that cannot be uploaded to Google Play.

To enable signed builds, add these repository secrets in GitHub under **Settings → Secrets and variables → Actions**:

- `OBBATTU_UPLOAD_KEYSTORE_BASE64`: base64 text of your private `.jks` upload keystore
- `OBBATTU_UPLOAD_STORE_PASSWORD`: keystore password
- `OBBATTU_UPLOAD_KEY_ALIAS`: key alias
- `OBBATTU_UPLOAD_KEY_PASSWORD`: key password

Keep the keystore and passwords private, and retain a secure backup for future app updates. Never commit the keystore or passwords to this repository.

## Local Android Studio build

Open this folder as an Android Studio project. Install Android SDK Platform 36 and Build Tools 36.0.0 when prompted. Use Java 17 and Gradle 8.13. The app's package name is `in.obbattu.app`; its version is 1.0.1 (version code 2).

## Included behavior

- Obbattu domain and subdomains remain in the app over HTTPS; other destinations open in Android.
- Supports JavaScript and first-party cookies needed by the website, payment-proof file selection, Android back navigation, and a retry page after connection errors.
- Blocks cleartext traffic and mixed content.
- Applies Android 15+ system bar insets so the store content does not overlap status/navigation controls.

Before Play submission, configure the upload key, test the release build on real devices, and complete Play Console's app content, privacy, store listing, testing, and policy requirements. A website wrapper's Play approval is not guaranteed; Google Play expects an app to offer meaningful functionality and a good user experience.
