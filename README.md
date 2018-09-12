# StickySessions

[![CircleCI](https://circleci.com/gh/DiscordTime/sticky-sessions-android.svg?style=svg)](https://circleci.com/gh/DiscordTime/sticky-sessions-android)

### Server communication

- Project has two flavors (`Online` and `Local`) which change backend URL used by app.
- `Local` flavor is intended for local backend development where developer could
  connect device via USB and use `adb reverse tcp:3000 tcp:3000` command to redirect
  requests to local machine.
