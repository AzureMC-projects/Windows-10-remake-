# Windows Remake OS

A Windows-inspired desktop project growing from a browser prototype toward a bootable Linux-based OS preview. This is an independent project and is not affiliated with Microsoft.

## Java desktop edition (no HTML required)

The repository now includes a separate native Java Swing desktop prototype in `java-app/`. It runs as a regular desktop application and opens real websites in your default browser instead of trying to embed them in an iframe. It includes a Start menu, desktop shortcuts, windowed apps, a simple calculator, Notepad with open/save, folder shortcuts, Paint drawing, and basic personalisation.

**Build requirements:** Java Development Kit (JDK) 25 and Gradle 9.1 or newer. The JAR is platform-independent, but this prototype uses Windows-specific launching for the Windows Settings shortcut.

### Build a JAR on your PC

1. Install JDK 25.
2. Install Gradle 9.1 or newer.
3. Open Command Prompt in the repository's `java-app` folder.
4. Run:
   ```bat
   gradle clean build
   ```
5. Run the generated JAR:
   ```bat
   java -jar build\libs\Windows-Remake-1.0.0.jar
   ```

No HTML browser page is needed to run this Java edition. The JAR still requires a compatible Java runtime to be installed.

### Build the JAR online instead

If you don't want to install build tools on your PC:

1. Open the repository's **Actions** tab.
2. Select **Build Java desktop JAR** and click **Run workflow** (or wait for it to run after a change to `java-app/`).
3. Open the completed run and download the `Windows-Remake-Java-JAR` artifact.
4. Extract the downloaded ZIP and run the JAR with Java 25 installed.

## Try the original HTML desktop prototype

Open `index.html` in Edge, Chrome, Firefox, or another modern browser. The HTML version remains in the repository; the Java version is a separate alternative.

## Preview the lock screen

Open `lockscreen/index.html` in a modern browser. Click the large Windows-style logo to transition into the existing desktop. This is a visual prototype, not a secure sign-in screen.

## Build the bootable OS preview without a VM

The repository includes a Debian Live build configuration and a GitHub Actions workflow. The workflow builds the ISO on a hosted runner, which is useful on a low-memory PC.

1. Open the repository's **Actions** tab.
2. Choose **Build bootable OS preview**.
3. Select **Run workflow** and run it on `main`.
4. When the workflow completes successfully, open the run and download the `windows-remake-os-preview` artifact.
5. Extract the artifact ZIP to get `Windows-Remake-OS-preview-amd64.iso` and its SHA-256 checksum.

The workflow can also run automatically when lock screen, desktop, or OS image files change. The artifact is temporary and currently retained for 14 days. Check the Actions logs if a build fails; the first ISO has not been certified or tested on every PC.

### Boot the ISO safely

- Use a spare USB drive and a trusted image-writing utility such as Rufus or balenaEtcher.
- **Writing the ISO erases the selected USB drive. Check the selected drive carefully.**
- Back up important files and keep Windows recovery options available.
- Restart and open your PC's one-time boot menu, then boot from the USB in live mode.
- Do not install to or repartition your internal Windows drive. This prototype is intended for live testing first.

## What the bootable preview does

The ISO is a lightweight Debian Live system. It starts a graphical session and launches Chromium in kiosk mode to show the custom lock screen. Clicking the logo opens the existing desktop simulation. Linux supplies the kernel, drivers, and base system; this is not a custom kernel yet.

## Java edition limitations

- This is a desktop simulation, not a replacement operating system or a Windows clone.
- The browser helper opens sites in your default browser; it does not embed a full browser engine inside the Java window.
- File Explorer shortcuts open actual Windows folders in File Explorer. Notepad can open and save files you select.
- Paint supports basic drawing but currently has no export feature.
- The JAR requires Java 25; it does not bundle a Java runtime.

## Safety and persistence

The original browser version's file system and command prompt are simulations. The Java edition's Notepad can write files only when you explicitly choose a save location, and its folder shortcuts open real user folders. Review file paths before saving.

## Project structure

- `index.html`, `styles.css`, `src/app.js` — original HTML desktop prototype
- `java-app/` — Java 25 Swing desktop application and Gradle build
- `lockscreen/` — custom HTML lock screen and click-to-enter transition
- `os-image/` — Debian Live configuration
- `.github/workflows/build-os.yml` — remote ISO build workflow
- `.github/workflows/build-java-app.yml` — remote Java JAR build workflow
