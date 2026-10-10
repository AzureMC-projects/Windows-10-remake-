# Windows Remake OS — bootable preview

This directory is the starting point for a Debian Live-based bootable prototype. It is **not yet a complete independent operating system**: Debian supplies the Linux kernel, drivers, session, and base system. The project supplies the lock screen and the existing desktop simulation.

## Intended boot flow

1. Boot the amd64 live image.
2. LightDM automatically opens an Openbox session for the live user.
3. Chromium starts in kiosk mode at `/opt/windows-remake/lockscreen/index.html`.
4. Clicking the Windows-style logo transitions to the existing desktop at `/opt/windows-remake/index.html`.

## Build without a VM on your 4 GB PC

The GitHub Actions workflow `.github/workflows/build-os.yml` builds the ISO on a hosted runner, so image compilation doesn't consume your PC's RAM. Run it from the repository's **Actions → Build bootable OS preview → Run workflow** page, or push a change to one of its watched paths.

The first build may take a while and can fail if Debian packages or upstream build infrastructure change. Check the workflow logs if it does. A successful run uploads an artifact named `windows-remake-os-preview` containing the ISO and SHA-256 checksum. The artifact is temporary (14 days), not a permanent release.

## Test safely

- Use a spare USB drive and a trusted image-writing tool such as balenaEtcher or Rufus.
- Writing an image erases the selected USB drive; verify the drive carefully.
- Boot from USB using your PC's one-time boot menu. Choose a live/try option; do not run an installer or repartition the internal drive.
- Back up important data and keep your Windows recovery options available.
- This preview is intended for compatible 64-bit x86 PCs. Wi-Fi, graphics, Secure Boot, and touch support vary by hardware.

## Known prototype limitations

- The logo is a visual unlock, not secure authentication.
- The current desktop apps are still simulations and store data in browser storage.
- The live session is temporary; changes are not preserved after reboot.
- The image currently targets legacy-compatible hybrid boot media; Secure Boot and diverse hardware require further testing.
- The build workflow and first ISO must be tested in CI before treating the image as bootable/release-ready.
