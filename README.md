# StockKeep

A simple Android barcode scanning app for inventory management.

## Features

- **Barcode Scanning**: Uses CameraX and ML Kit to scan barcodes in real-time
- **Inventory Management**: Stores scanned items with quantity tracking
- **Local Database**: Room database for offline storage
- **Export**: Export inventory to CSV format with share functionality
- **Edit Items**: Add names, descriptions, and notes to scanned items

## Tech Stack

- **UI**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM with ViewModel
- **Database**: Room (SQLite)
- **Barcode Scanning**: ML Kit Barcode Scanning
- **Camera**: CameraX
- **Language**: Kotlin

## Project Structure

```
app/src/main/java/com/funkymonkey/stockkeep/
├── data/
│   ├── database/        # Room database and DAOs
│   ├── model/           # Data classes (StockItem)
│   └── repository/      # Repository pattern for data access
├── ui/
│   ├── screens/         # Composable screens (Inventory, Scan)
│   ├── theme/           # Material3 theme
│   └── components/      # Reusable UI components
├── viewmodel/           # StockViewModel
├── MainActivity.kt
└── StockKeepApplication.kt
```

## Getting Started

1. Open the project in Android Studio
2. Sync Gradle files
3. Run on a device or emulator with camera support

## Building

```bash
./gradlew assembleDebug
```

## Permissions

- **Camera**: Required for barcode scanning
- **Storage**: Used for CSV export (Android 9 and below)

## CSV Export Format

Exported CSV files contain:
- ID
- Barcode
- Name
- Description
- Quantity
- Scanned At (timestamp)
- Notes

Files are saved to app storage and can be shared via Android's share sheet.
