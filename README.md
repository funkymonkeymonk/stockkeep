# StockKeep

A simple Android barcode scanning app for inventory management with automatic deployment and in-app updates.

## Features

- **Barcode Scanning**: Uses CameraX and ML Kit to scan barcodes in real-time
- **Inventory Management**: Stores scanned items with quantity tracking
- **Local Database**: Room database for offline storage
- **Export**: Export inventory to CSV format with share functionality
- **Edit Items**: Add names, descriptions, and notes to scanned items
- **Auto-Updates**: In-app notifications when new versions are available

## Tech Stack

- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM with ViewModel
- **Database**: Room (SQLite)
- **Barcode Scanning**: ML Kit Barcode Scanning
- **Camera**: CameraX
- **Distribution**: Firebase App Distribution
- **CI/CD**: GitHub Actions
- **Language**: Kotlin

## Project Structure

```
app/src/main/java/com/meepleprofessionals/stockkeep/
├── data/
│   ├── database/        # Room database and DAOs
│   ├── model/           # Data classes (StockItem)
│   └── repository/      # Repository pattern for data access
├── ui/
│   ├── screens/         # Composable screens (Inventory, Scan)
│   ├── theme/           # Material3 theme
│   └── components/      # Reusable UI components
├── update/              # In-app update manager
├── viewmodel/           # StockViewModel
├── MainActivity.kt
└── StockKeepApplication.kt
```

## CI/CD Pipeline

### Pull Requests
- ✅ Runs lint checks
- ✅ Runs unit tests
- ✅ Uploads test results

### Merges to Master
- ✅ Runs all tests
- ✅ Builds signed release APK
- ✅ Uploads to Firebase App Distribution (auto-deploys to your phone!)
- ✅ Available in GitHub Actions artifacts

### Manual Trigger
You can manually trigger builds from the Actions tab in GitHub.

## Setup Instructions

### 1. Firebase Project Setup (Required for Auto-Deploy)

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project (or use existing)
3. Add Android app with package name: `com.meepleprofessionals.stockkeep`
4. Download `google-services.json` and place it in `app/google-services.json`
5. Enable **App Distribution** in Firebase console

### 2. GitHub Secrets Setup

Add these secrets to your GitHub repository (Settings → Secrets → Actions):

**For Signing:**
- `SIGNING_KEY_BASE64` - Base64 encoded keystore file
- `ALIAS` - Keystore alias
- `KEY_STORE_PASSWORD` - Keystore password  
- `KEY_PASSWORD` - Key password

**For Firebase:**
- `FIREBASE_APP_ID` - Your Firebase app ID (found in Firebase console)
- `FIREBASE_SERVICE_ACCOUNT_JSON` - Service account JSON for CI access

### 3. Create Signing Keystore

```bash
# Generate keystore
keytool -genkey -v -keystore stockkeep.keystore -alias stockkeep -keyalg RSA -keysize 2048 -validity 10000

# Encode for GitHub secret
base64 -i stockkeep.keystore | pbcopy  # On macOS
# or
base64 -w 0 stockkeep.keystore  # On Linux
```

### 4. Firebase Service Account

1. In Firebase Console → Project Settings → Service Accounts
2. Click "Generate new private key"
3. Copy the JSON content to `FIREBASE_SERVICE_ACCOUNT_JSON` secret

### 5. Add Yourself as Tester

1. In Firebase Console → App Distribution
2. Add your email as a tester
3. Accept the invitation email on your phone
4. Install the Firebase App Tester app (optional but recommended)

## How It Works

### For Developers

**On Pull Request:**
```bash
git checkout -b feature/new-feature
git add .
git commit -m "Add new feature"
git push origin feature/new-feature
# Create PR on GitHub - tests will run automatically
```

**After Merge:**
- Code merged to master → GitHub Actions triggers
- Builds signed APK → Uploads to Firebase
- You get a notification on your phone within minutes!

### For Users (Your Phone)

1. **First Install**: Accept Firebase invitation email, download initial APK
2. **Updates**: Open the app → If update available, dialog appears → Tap "Update Now"
3. **Auto-download**: New version downloads and installs automatically
4. **Restart**: App restarts with new version

## Building Locally

```bash
# Debug build
./gradlew assembleDebug

# Release build (requires signing config)
./gradlew assembleRelease

# Run tests
./gradlew test

# Run lint
./gradlew lint
```

## Troubleshooting

### Build fails with "AndroidX not enabled"
Make sure `gradle.properties` contains:
```properties
android.useAndroidX=true
```

### Firebase not initializing
- Verify `google-services.json` is in `app/` directory
- Check package name matches Firebase project

### CI/CD fails
- Check all GitHub secrets are set correctly
- Verify Firebase App Distribution is enabled
- Check Actions logs for specific errors

## Permissions

- **Camera**: Required for barcode scanning
- **Storage**: Used for CSV export (Android 9 and below)
- **Internet**: Required for Firebase App Distribution updates

## CSV Export Format

Exported CSV files contain:
- ID
- Barcode
- Name
- Description
- Quantity
- Scanned At (timestamp)
- Notes

## Contributing

1. Create a feature branch
2. Make changes
3. Run tests locally: `./gradlew test`
4. Push and create PR
5. Wait for CI to pass
6. Merge and enjoy automatic deployment!

## License

MIT License - feel free to use and modify!
