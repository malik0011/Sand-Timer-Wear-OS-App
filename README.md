# Sand Timer for Wear OS

A beautiful and minimalist sand timer application designed specifically for Wear OS smartwatches. The app features two distinct timer styles with smooth animations that bring the classic hourglass experience to your wrist.

![Sand Timer App](screenshots/sand_timer_demo.gif)

## Features

### 1. Interactive Timer (Sand Animation Style)
- Set custom timer duration (up to 60 minutes)
- Intuitive controls:
  - "+" button: Add 1 minute
  - "-" button: Subtract 1 minute
  - "Start" button: Begin countdown
- Beautiful sand animation that progresses with time
- Double-tap during countdown to cancel timer
- Clear digital time display

### 2. Classic Animation Style
- Continuous sand animation
- Relaxing visual experience
- No interaction required - perfect for ambient display

## Installation

### Requirements
- Wear OS device running Wear OS 3.0 or later
- Android phone with Android 8.0 or later
- Phone must be paired with your Wear OS device

### Installation Steps

1. **Via Google Play Store (Recommended)**
   - Open Play Store on your Wear OS device
   - Search for "Sand Timer"
   - Tap Install

2. **Via ADB (For Developers)**
   ```bash
   # Enable Developer Options on your watch
   # Enable ADB Debugging
   # Connect watch to computer
   adb install sand-timer.apk
   ```

## How to Use

### Interactive Timer Mode
1. Open the app on your Wear OS device
2. Use "+" and "-" buttons to set desired duration
3. Tap "Start" to begin countdown
4. Watch the sand animation progress
5. Double-tap to cancel timer if needed
6. Timer will vibrate when complete

### Classic Animation Mode
1. Open app settings
2. Select "Classic" style
3. Enjoy the continuous sand animation

## Development

Built with:
- Kotlin
- Jetpack Compose for Wear OS
- Lottie for animations

### Building from Source
1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/sand-timer.git
   ```
2. Open in Android Studio
3. Sync Gradle files
4. Build the project

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Sand animation design by Pedro Lerma
- Built with Google's Wear OS development guidelines
- Special thanks to the Jetpack Compose and Wear OS communities

## Support

For issues, feature requests, or questions:
- Open an issue in this repository
- Contact: your.email@example.com

## Version History

- v1.0.0 - Initial release
  - Two timer styles
  - Custom duration support
  - Smooth animations 