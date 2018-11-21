# StickySessions

[![CircleCI](https://circleci.com/gh/DiscordTime/sticky-sessions-android.svg?style=svg)](https://circleci.com/gh/DiscordTime/sticky-sessions-android)

StickySessions is an application that helps medium to large teams to share and store their thoughts through digital-like retrospective sessions. This is the repository of the Android app.

## Usage

This Android app is intended to be used along with [Backend][server] and [Web][web] applications. Please check their repositories for more information about how to run them.

An usual session would follow steps below:
1. User creates a session on app
2. He shares the session link with team
3. Team start adding notes during a predefined time
4. Team discuss notes taken on web app
5. A mediator export notes on web app and send them to team.

## Server communication

- Project has 3 flavors (`Prod`, `Stag` and `Local`) which change backend URL used by app.
- `Prod` is intended for production and should be used only on real meetings
- `Stag` is intended for usual development on Android client's side
- `Local` flavor is intended for local backend development where developer
  connects the device via USB and uses `adb reverse tcp:3000 tcp:3000` command to redirect
  requests to local machine.

[server]: https://github.com/DiscordTime/sticky-sessions-server/tree/dev
[web]:https://github.com/DiscordTime/sticky-sessions-web/tree/dev
