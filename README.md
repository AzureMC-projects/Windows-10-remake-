# Windows Remake OS

A Windows-inspired desktop project growing from a browser prototype toward a bootable Linux-based OS preview. This is an independent project and is not affiliated with Microsoft.

## Try the desktop prototype on Windows

Requirements: Windows 10/11 and a modern browser. Node.js 18+ is optional.

- **Quick start:** open `index.html` in Edge, Chrome, Firefox, or another modern browser.
- **Local server:** run `npm start` in this directory, then open the local URL printed by the server.

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

Current limitations:
- The lock screen logo is a visual unlock, not authentication.
- Most built-in desktop applications remain simulations.
- The live session does not preserve changes after reboot.
- Hardware compatibility, networking, Secure Boot, and boot behavior need testing.
- Do not use this prototype as your primary OS or on a machine containing data you cannot risk.

## Desktop features

- Start menu, app search, Task View, notification panel, clock and date
- Draggable, resizable, minimisable, maximisable and closable application windows
- Taskbar indicators, Alt+Tab switching, edge snapping and show-desktop
- Virtual File Explorer, Notepad, Calculator, Paint, Settings, simulated Browser, Recycle Bin, Task Manager, safe simulated command prompt, Calendar and Clock
- Local browser storage for simulated files and preferences

## Safety and persistence

The desktop's file system and command prompt are simulations. They do not execute shell commands or modify host system files. Browser local storage holds virtual files and preferences; clearing site data may erase them. Paint PNG export is user-initiated.

## Project structure

- `index.html` — desktop shell and taskbar
- `styles.css` — desktop, windows, panels and app styling
- `src/app.js` — window manager and simulated apps
- `lockscreen/` — custom lock screen and click-to-enter transition
- `os-image/` — Debian Live configuration
- `.github/workflows/build-os.yml` — remote ISO build workflow
- `package.json` — local development server scripts
