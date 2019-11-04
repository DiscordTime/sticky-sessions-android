# StickySessions

[![CircleCI](https://circleci.com/gh/DiscordTime/sticky-sessions-android.svg?style=svg)](https://circleci.com/gh/DiscordTime/sticky-sessions-android)
[![Coverage Status](https://coveralls.io/repos/github/DiscordTime/sticky-sessions-android/badge.svg)](https://coveralls.io/github/DiscordTime/sticky-sessions-android)

StickySessions is an application that helps medium to large teams to share and store their thoughts through digital-like retrospective sessions. This is the repository of the Android app.

## Usage

This Android app is intended to be used along with [Backend][server] and [Web][web] applications. Please check their repositories for more information about how to run them.

Teste

An usual session would follow steps below:
1. User creates a session on app
2. He shares the session link with team
3. Team start adding notes during a predefined time
4. Team discuss notes taken on web app
5. A mediator export notes on web app and send them to team.

## Development

### Server communication

Project has 3 flavors (`Prod`, `Stag` and `Local`) which change backend URL used by app.
- `Prod` is intended for production and should be used only on real meetings
- `Stag` is intended for usual development on Android client's side
- `Local` flavor is intended for local backend development where developer
  connects the device via USB and uses `adb reverse tcp:3000 tcp:3000` command to redirect
  requests to local machine.

### Setup

The app uses Google Sign-in with Firebase authentication as its login method therefore in order to compile it you need to setup an Firebase account, associate app on console, download `google-services.json` file and add it to the project.

[server]: https://github.com/DiscordTime/sticky-sessions-server/tree/dev
[web]:https://github.com/DiscordTime/sticky-sessions-web/tree/dev

### App's Version

The app's version is controlled by a simple algorithm implemented on
[versioning.gradle](app/versioning.gradle) file and uses
`major.minor.build` format according to values defined in
[versio.properties](app/version.properties) file.

The `build` portion is automatically incremented by the number of
commits merged. To increase `major` or `minor` parts of version,
please follow steps below:
- Run the following command to get the current number of commits:
```
git rev-list HEAD --count
```
- Update `VERSION_BUILD_BASE` with that number
- Then increment desired part of app's version
  - If increment is on `VERSION_MAJOR` portion, please clear `VERSION_MINOR`.
