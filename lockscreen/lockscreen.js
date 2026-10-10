(() => {
  const time = document.getElementById("time");
  const date = document.getElementById("date");
  const enter = document.getElementById("enter");
  let opening = false;
  function tick() {
    const now = new Date();
    time.textContent = new Intl.DateTimeFormat(undefined, {hour:"2-digit", minute:"2-digit", hour12:undefined}).format(now);
    date.textContent = new Intl.DateTimeFormat(undefined, {weekday:"long", month:"long", day:"numeric"}).format(now);
  }
  tick();
  setInterval(tick, 15000);
  function unlock() {
    if (opening) return;
    opening = true;
    document.body.classList.add("unlocking");
    enter.disabled = true;
    window.setTimeout(() => {
      // Works from both a local checkout and the installed live image.
      window.location.href = new URL("../index.html", window.location.href).href;
    }, 480);
  }
  enter.addEventListener("click", unlock);
  document.addEventListener("keydown", event => {
    if (event.key === "Enter" || event.key === " ") {
      if (event.target === document.body) { event.preventDefault(); unlock(); }
    }
  });
})();
