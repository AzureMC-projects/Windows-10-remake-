# Desktop Simulation

An independent browser-based desktop inspired by classic Windows 10 design. It is not a Microsoft product and does not replace an operating system.

## Run locally

Requirements: Windows 10/11 and a modern browser. Node.js 18+ is optional.

- **Quick start:** open `index.html` in Edge, Chrome, Firefox, or another modern browser.
- **Recommended local server:** run `npm start` in this directory, then open the local URL printed by the server. The script uses `npx serve`; its first run may download that development utility. No administrator privileges are required.

## Features

- Desktop shortcuts, selection, right-click menus, refresh and personalisation
- Start menu, app search, Task View, notification panel, clock and date
- Draggable, resizable, minimisable, maximisable and closable application windows
- Taskbar indicators, Alt+Tab switching, edge snapping and show-desktop
- Virtual File Explorer with folders, file creation, rename and delete
- Notepad editing and saving to the virtual file system
- Arithmetic calculator
- Paint canvas with brush colour/size, eraser, clear and PNG export
- Settings for wallpaper and accent colour
- Simulated browser that explicitly does not open external websites
- Recycle Bin restore and permanent deletion
- Simulated Task Manager and safe command prompt
- Calendar and persistent virtual files/preferences

## Safety and persistence

The file system and command prompt are simulations. No real system files are read or modified, no shell commands are executed, and no administrator credentials are requested. Browser local storage holds the virtual files and preferences; clearing site data may erase them. The simulated browser does not access the internet. Paint PNG export is initiated by the user.

## Troubleshooting

- **Blank page:** check the browser developer console and verify `src/app.js` and `styles.css` exist at the expected paths.
- **Files/preferences don't persist:** enable local storage and use the same browser profile. Private browsing may clear data when closed.
- **npm start fails:** open `index.html` directly, or run `npx serve .` from the project directory.
- **Small screen:** maximise the browser window for the intended desktop experience.

## Structure

- `index.html` — desktop shell and taskbar
- `styles.css` — desktop, windows, panels and application styling
- `src/app.js` — window manager, virtual file system, persistence and built-in simulated applications
- `package.json` — local server scripts
