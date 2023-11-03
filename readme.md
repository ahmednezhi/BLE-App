# Android BLE Background Color Change App

## Introduction
This Android application demonstrates the use of Bluetooth Low Energy (BLE) technology to change the background color of the app's screen. The application acts as a BLE Peripheral device, advertising a custom service that allows connected BLE Central devices to write a value to a characteristic, which in turn changes the screen color.

## Features
- Implements **MVVM architecture** for clean separation of concerns and better testability.
- Uses **Jetpack Compose** for a modern, declarative UI.
- Utilizes **Dagger/Hilt** for dependency injection to provide a scalable and maintainable codebase.
- Includes unit tests with **JUnit** and **MockK** for robustness and reliability.
- Advertises as a BLE Peripheral and changes the app's background color based on characteristic writes.

## BLE Service Details
- Service UUID: `13b402b6-ff2c-4175-a157-7e888b192a45`
- Characteristic UUID: `c0a130aa-7409-484a-935a-ff81c1d4ef96`
- Accepts an 8-bit unsigned integer (UInt8) value.

## Getting Started
To run this application, you will need an Android device with BLE capabilities and Android 9 or higher. Clone the repository, open the project in Android Studio, build the application, and run it on your device.

### Prerequisites
- Android Studio
- A BLE-capable Android device running Android 9+

### Installation
1. Clone the repository to your local machine.
2. Open the project with Android Studio.
3. Connect your Android device to your development machine.
4. Build the project and run the application on your device.

## Testing
You can test the application's BLE functionality using a third-party BLE scanner app like LightBlue. Connect to the “Test AN” device and write values to the characteristic `c0a130aa-7409-484a-935a-ff81c1d4ef96` to change the background color of the app.

## Contributing
Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

## Contact
Ahmed Nezhi - [Email](mailto:ahmed.nezhi@outlook.com)

## Acknowledgements
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android BLE Guide](https://punchthrough.com/android-ble-guide/)
- [LightBlue](https://www.punchthrough.com/lightblue/)
